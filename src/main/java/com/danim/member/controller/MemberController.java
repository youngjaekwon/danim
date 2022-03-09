package com.danim.member.controller;
import com.danim.member.beans.Member;
import com.danim.member.dao.MemberDao;
import com.danim.member.dto.MemberDTO;
import com.danim.member.parser.MemberParser;
import com.danim.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 회원가입 페이지
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup() {
        return "member/member-signup";
    }

    // 회원정보 수정 페이지
    @RequestMapping(value = "/mypage", method = RequestMethod.GET)
    public String mypage(HttpServletRequest httpServletRequest, HttpSession session) {
        String user = (String) session.getAttribute("user");
        // 로그인된 상태일 경우 회원정보 수정 페이지로 이동
        if (user != null){
            Member member = memberService.selectMember(user); // DB에서 로그인된 유저 정보 검색
            MemberDTO dto = MemberParser.parseMemberEntitytoDTO(member); // Entity to DTO parsing
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

    // 회원가입 form submit시 작동
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
}


