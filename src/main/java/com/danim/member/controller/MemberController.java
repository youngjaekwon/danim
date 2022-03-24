package com.danim.member.controller;
import com.danim.common.paging.PageMaker;
import com.danim.member.beans.Member;
import com.danim.member.dto.MemberDTO;
import com.danim.member.parser.MemberParser;
import com.danim.member.service.MemberService;
import com.danim.orders.beans.OrdersVO;
import com.danim.orders.service.OrdersService;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class MemberController {

    private final MemberService memberService;
    private final MemberParser memberParser;
    private final OrdersService ordersService;
    private final JSONParser jsonPaeser;

    @Autowired
    public MemberController(MemberService memberService, MemberParser memberParser, OrdersService ordersService, JSONParser jsonPaeser) {
        this.memberService = memberService;
        this.memberParser = memberParser;
        this.ordersService = ordersService;
        this.jsonPaeser = jsonPaeser;
    }

    // 회원가입 페이지
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(HttpServletRequest request, Model model) {
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null){
            HashMap<String, String> naverUser = (HashMap<String, String>) flashMap.get("naverUser");
            model.addAttribute("naverUser", naverUser);
        }
        return "member/member-signup";
    }

    // 회원가입 form submit시 작동
    @RequestMapping(value = "/doSignup", method = RequestMethod.POST)
    public String doSignup(MemberDTO dto, HttpSession session, RedirectAttributes redirectAttributes) {
        // 회원가입 성공시
        if (memberService.signup(dto, session)){
            redirectAttributes.addFlashAttribute("signup", "passed"); // session의 signup attribute에 passed 추가
            return "redirect:/"; // 메인 페이지로 이동
        } else {
            // 실패시
            redirectAttributes.addFlashAttribute("signup", "failed"); // session의 signup attribute에 failed 추가
            return "redirect:/signup"; // 회원가입 페이지로 이동
        }
    }

    // 회원가입시 이메일 중복확인
    @RequestMapping(value = "/isDuplicatedEmail", method = RequestMethod.POST)
    @ResponseBody
    public String isDuplicatedEmail(@RequestParam Map<String, String> param){
        // 이메일: email1@email2
        String email = param.get("email1") + "@" + param.get("email2");
        return (memberService.isDuplicatedEmail(email)) ? "true" : "false"; // 이메일 중복여부 반환
    }

    // 로그인
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest httpServletRequest, HttpSession session, RedirectAttributes redirectAttributes) {
        Member member = new Member(); // 로그인 정보 전달 member 객체 생성
        member.setEmail(httpServletRequest.getParameter("id")); // email
        member.setPwd(httpServletRequest.getParameter("password")); // 비밀번호
        if (memberService.loginCheck(member, session)) { // 로그인 성공여부 확인
            redirectAttributes.addFlashAttribute("loginCheck", "true"); // 로그인 성공 저장
        } else {
            redirectAttributes.addFlashAttribute("loginCheck", "failed"); // 로그인 실패 저장
        }
        return "redirect:" + httpServletRequest.getHeader("Referer"); // 이전 페이지로 이동
    }

    // 회원정보 수정 form submit시 작동
    @RequestMapping(value = "/doModifyMemberInfo", method = RequestMethod.POST)
    public String doModifyMemberInfo(MemberDTO dto, HttpServletRequest httpServletRequest, HttpSession session, RedirectAttributes redirectAttributes) {
        // 로그인된 회원 번호
        String memnum = (String) session.getAttribute("user");
        // 로그인된 유저정보
        Member member = memberService.selectMember(memnum);
        // DB수정 정상인지 확인하는 부분
        int result = 0;
        // 회원정보 갱신 (비밀번호 일치할 시)
        if (dto.getPwd().equals(member.getPwd())){
            // 새 비밀번호 입력시 갱신
            if (!dto.getNewPassword().equals("")) member.setPwd(dto.getNewPassword());
            member.setNickname(dto.getNickname());
            member.setZipcode(dto.getZipcode());
            member.setAddr(dto.getAddr()+ ", " + dto.getAddrDetail());
            member.setMobile(dto.getMobile1()+"-"+dto.getMobile2()+"-"+dto.getMobile3());
            // 새로운 정보 저장된 객체로 update
            result = memberService.modifyMember(member);
        } else {
            // 비밀번호 일치하지 않으면 -1 (오류) 반환
            result = -1;
        }

        // 회원가입 성공시
        if (result > 0){
            redirectAttributes.addFlashAttribute("modify", "passed"); // session의 modify attribute에 passed 추가
            session.setAttribute("userInfo", dto);
        } else if (result == -1){
            redirectAttributes.addFlashAttribute("modify", "pwdMisMatch"); // session의 modify attribute에 failed 추가
        } else {
            // 실패시
            redirectAttributes.addFlashAttribute("modify", "failed"); // session의 modify attribute에 failed 추가
        }
        return "redirect:" + httpServletRequest.getHeader("Referer"); // 이전 페이지로 이동
    }

    // 비밀 번호 찾기
    @RequestMapping(value = "/doFindPwd", method = RequestMethod.POST)
    @ResponseBody
    public String doFindPwd(@RequestParam Map<String, String> param){
        String email = param.get("email"); // 입력된 email값
        String mobile = param.get("mobile"); // 입력된 mobile값
        String memnum;
        return ((memnum = memberService.doFindPwd(email, mobile)) != null) ?  memnum : "failed"; // 비밀번호 찾기 성공시 회원번호 반환
    }

    // 비밀 번호 변경
    @RequestMapping(value = "/doChangePwd", method = RequestMethod.POST)
    public String doChangePwd(HttpServletRequest httpServletRequest, HttpSession session, RedirectAttributes redirectAttributes) {
        // 비밀 번호 수정할 회원번호
        String memnum = httpServletRequest.getParameter("memnum");
        // 수정할 비밀번호
        String pwd = httpServletRequest.getParameter("change-pwd-pwd");
        // 비밀번호 변경 성공시
        if (memberService.modify(memnum, "PWD", pwd)){
            redirectAttributes.addFlashAttribute("changePwd", "passed"); // session의 changePwd attribute에 passed 추가
        } else {
            // 실패시
            redirectAttributes.addFlashAttribute("changePwd", "failed"); // session의 changePwd attribute에 failed 추가
        }
        return "redirect:" + httpServletRequest.getHeader("Referer"); // 이전 페이지로 이동
    }

    // 아이디 찾기
    @RequestMapping(value = "/doFindEmail", method = RequestMethod.POST)
    @ResponseBody
    public String doFindEmail(@RequestParam Map<String, String> param){
        String name = param.get("name"); // 입력된 name값
        String mobile = param.get("mobile"); // 입력된 mobile값
        String email;
        if ((email = memberService.doFindEmail(name, mobile)) != null) return email; // 아이디 찾기 성공, 아이디 반환
        else return "failed"; // 아이디 찾기 실패
    }

    // 로그아웃
    @RequestMapping(value = "/doLogout", method = RequestMethod.GET)
    public String doLogout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/"; // 메인 페이지로 이동
    }

    // 회원탈퇴
    @RequestMapping(value = "/doSignout", method = RequestMethod.POST)
    public String doSignout(HttpServletRequest httpServletRequest, HttpSession session, RedirectAttributes redirectAttributes) {
        // 로그인된 회원번호
        String memnum = (String) session.getAttribute("user");
        // 로그인된 유저
        Member member = memberService.selectMember(memnum);
        // 입력된 비밀번호
        String pwd = httpServletRequest.getParameter("signout-password");
        // 회원탈퇴 결과
        boolean result = false;

        // 입력된 비밀번호 확인
        if (member.getPwd() != null && member.getPwd().equals(pwd)){
            // 회원탈퇴 성공시 세션 제거, 성공여부 추가
            if (memberService.doSignout(memnum)){
                result = true;
                session.removeAttribute("user");
                redirectAttributes.addFlashAttribute("signout", "passed");
            } else {
                redirectAttributes.addFlashAttribute("signout", "failed");
            }
        } else {
            redirectAttributes.addFlashAttribute("signout", "pwdMismatch");
        }

        return result? "redirect:/" : "redirect:" + httpServletRequest.getHeader("Referer"); // 성공: 메인 페이지로 이동/실패: 이전 페이지로 이동
    }

    /////////////////////////////// 마이 페이지 //////////////////////////////////////
    // 회원정보 수정 페이지
    @RequestMapping(value = "/mypage", method = RequestMethod.GET)
    public String mypage(HttpServletRequest httpServletRequest, HttpSession session) {
        String user = (String) session.getAttribute("user");
        // 로그인된 상태일 경우 회원정보 수정 페이지로 이동
        if (user != null){
            Member member = memberService.selectMember(user); // DB에서 로그인된 유저 정보 검색
            MemberDTO dto = memberParser.parseMember(member); // Entity to DTO parsing
            session.setAttribute("userInfo", dto); // 유저 정보 세션에 전달
            return "member/member-mypage";
        } else {
            // 로그인이 되어있지 않을 경우
            session.setAttribute("loginCheck", "false"); // 비 로그인 상태 추가
            String currentPage = httpServletRequest.getHeader("Referer"); // 이전 페이지 확인
            return (currentPage != null) ? ("redirect:" + currentPage) : "redirect:/";
            // 이전 페이지가 있으면 이전페이지로 이동, 없으면 index 페이지로 이동
        }
    }

    // 주문 내역 페이지
    @RequestMapping(value = "/orderList", method = RequestMethod.GET)
    public ModelAndView orderList(HttpSession session, HttpServletRequest request){
        ModelAndView mav = new ModelAndView("member/member-orderlist"); // view 추가

        // paging 요소
        String memnum = (String) session.getAttribute("user"); // 로그인된 회원번호
        String requestPage = request.getParameter("page"); // 요청된 페이지

        // 회원번호로 리스트 검색
        List<OrdersVO> totalList = ordersService.getList(memnum);

        // 리스트 검색이 안될경우
        if (totalList == null) {
            totalList = new ArrayList<>();
        }

        int numPerPage = 10; // 한 페이지당 출력할 아이템 수: 주문리스트의 경우 10개
        int pagePerBlock = 6; // 하단 페이지 네비게이션 한 블럭 당 출력할 페이지 수: 주문 리스트의 경우 6개

        // 검색된 리스트, 요청된 페이지를 이용하여 페이지 생성
        PageMaker.makePage(mav, totalList, requestPage, numPerPage, pagePerBlock);

        return mav;
    }

    @RequestMapping(value = "/naverLogin")
    public String naverLogin(HttpServletRequest request){
        return "common/socialLogin";
//        return "index";
    }

    @RequestMapping(value = "/doNaverLogin", method = RequestMethod.POST)
    public String doNaverLogin(@RequestParam String user, HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttributes)
            throws ParseException {
        JSONObject jsonUser = (JSONObject) jsonPaeser.parse(user);
        String userEmail = jsonUser.get("email").toString(); // 네이버 로그인 시도한 유저 이메일
        Member naverLoginMember = memberService.searchMember("EMAIL", userEmail); // 이메일을 통한 유저 검색

        // 검색결과 null(최초 로그인)의 경우 signup 페이지로 redirect
        if (naverLoginMember == null) {
            redirectAttributes.addFlashAttribute("naverUser", jsonUser);
            return "redirect:/signup";
        }

        // 검색 결과가 존재하는 경우 (로그인 성공한 경우)
        session.setAttribute("user", naverLoginMember.getMemnum()); // 회원 번호 세션에 등록
        redirectAttributes.addFlashAttribute("loginCheck", "true"); // 로그인 성공 저장

        return "redirect:/";
    }
}


