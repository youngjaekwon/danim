package com.danim.member.controller;

import com.danim.common.paging.PageMaker;
import com.danim.member.beans.Member;
import com.danim.member.dto.MemberDTO;
import com.danim.member.parser.MemberParser;
import com.danim.member.service.MemberService;
import com.danim.orders.beans.OrdersVO;
import com.danim.orders.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


/*
* 멤버 권한이 필요한 페이지를 담당하는 컨트롤러
* ROLE_MEMBER 필요
* */
@Controller
@RequestMapping("/member")
public class AuthMemberController {

    private final MemberService memberService;
    private final MemberParser memberParser;
    private final OrdersService ordersService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    @Lazy
    public AuthMemberController(MemberService memberService, MemberParser memberParser, OrdersService ordersService, PasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.memberParser = memberParser;
        this.ordersService = ordersService;
        this.passwordEncoder = passwordEncoder;
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
        if (passwordEncoder.matches(dto.getPwd(), member.getPwd())){
            // 새 비밀번호 입력시 갱신
            String newPassword = dto.getNewPassword();
            if (!newPassword.equals("")){
                member.setPwd(passwordEncoder.encode(newPassword));
            }
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

        // 회원 정보 수정 성공시
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
        if (member.getPwd() != null && passwordEncoder.matches(pwd, member.getPwd())){
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

}
