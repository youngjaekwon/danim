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
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    @Autowired
    @Lazy
    public MemberController(MemberService memberService, MemberParser memberParser, OrdersService ordersService, JSONParser jsonPaeser, PasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.memberParser = memberParser;
        this.ordersService = ordersService;
        this.jsonPaeser = jsonPaeser;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입 페이지
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(HttpServletRequest request, Model model) {
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request); // RequestAttribute Map

        // 소셜 로그인 유저정보 존재하면 model에 저장
        if (flashMap != null){
            HashMap<String, String> naverUser = (HashMap<String, String>) flashMap.get("naverUser");
            model.addAttribute("naverUser", naverUser);
            HashMap<String, String> googleUser = (HashMap<String, String>) flashMap.get("googleUser");
            model.addAttribute("googleUser", googleUser);
            HashMap<String, String> kakaoUser = (HashMap<String, String>) flashMap.get("kakaoUser");
            model.addAttribute("kakaoUser", kakaoUser);
        }

        return "member/member-signup";
    }

    // 회원가입 form submit시 작동
    @RequestMapping(value = "/doSignup", method = RequestMethod.POST)
    public String doSignup(MemberDTO dto, HttpSession session, RedirectAttributes redirectAttributes) {

        // 비밀번호 인코딩
        dto.setPwd(passwordEncoder.encode(dto.getPwd()));

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

    // Spring Security 이용으로 사용하지 않음
//    // 로그인
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public String login(HttpServletRequest httpServletRequest, HttpSession session, RedirectAttributes redirectAttributes) {
//        Member member = new Member(); // 로그인 정보 전달 member 객체 생성
//        member.setEmail(httpServletRequest.getParameter("id")); // email
//        member.setPwd(httpServletRequest.getParameter("password")); // 비밀번호
//        if (memberService.loginCheck(member, session)) { // 로그인 성공여부 확인
//            redirectAttributes.addFlashAttribute("loginCheck", "true"); // 로그인 성공 저장
//        } else {
//            redirectAttributes.addFlashAttribute("loginCheck", "failed"); // 로그인 실패 저장
//        }
//        return "redirect:" + httpServletRequest.getHeader("Referer"); // 이전 페이지로 이동
//    }


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

    /////////////////////////////// 소셜 로그인 //////////////////////////////////////
    // 네이버 로그인 CallBack
    @RequestMapping(value = "/naverLogin")
    public String naverLogin(HttpServletRequest request){
        return "common/socialLogin";
    }

    // 네이버 로그인
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

    // 구글 로그인
    @RequestMapping(value = "/doGoogleLogin", method = RequestMethod.POST)
    public String doGoogleLogin(@RequestParam String user, HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttributes)
            throws ParseException {
        JSONObject jsonUser = (JSONObject) jsonPaeser.parse(user); // 클라이언트에서 넘어온 유저정보 파싱
        String userEmail = jsonUser.get("zv").toString(); // 구글 로그인 시도한 유저 이메일
        Member googleLoginMember = memberService.searchMember("EMAIL", userEmail); // 이메일을 통한 유저 검색

        // 검색결과 null(최초 로그인)의 경우 signup 페이지로 redirect
        if (googleLoginMember == null) {
            redirectAttributes.addFlashAttribute("googleUser", jsonUser);
            return "redirect:/signup";
        }

        // 검색 결과가 존재하는 경우 (로그인 성공한 경우)
        session.setAttribute("user", googleLoginMember.getMemnum()); // 회원 번호 세션에 등록
        redirectAttributes.addFlashAttribute("loginCheck", "true"); // 로그인 성공 저장

        return "redirect:" + request.getHeader("Referer"); // 이전 페이지로 이동
    }

    // 카카오 로그인
    @RequestMapping(value = "/doKakaoLogin", method = RequestMethod.POST)
    public String doKakaoLogin(@RequestParam String user, HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttributes)
            throws ParseException {
        JSONObject jsonUser = (JSONObject) jsonPaeser.parse(user); // 클라이언트에서 넘어온 유저정보 파싱
        String userEmail = jsonUser.get("email").toString(); // 카카오 로그인 시도한 유저 이메일
        String userName = ((HashMap<String, String>) jsonUser.get("profile")).get("nickname"); // 카카오 로그인 시도한 유저 이름
        jsonUser.put("name", userName); // 유저 이름 등록
        Member googleLoginMember = memberService.searchMember("EMAIL", userEmail); // 이메일을 통한 유저 검색

        // 검색결과 null(최초 로그인)의 경우 signup 페이지로 redirect
        if (googleLoginMember == null) {
            redirectAttributes.addFlashAttribute("kakaoUser", jsonUser);
            return "redirect:/signup";
        }

        // 검색 결과가 존재하는 경우 (로그인 성공한 경우)
        session.setAttribute("user", googleLoginMember.getMemnum()); // 회원 번호 세션에 등록
        redirectAttributes.addFlashAttribute("loginCheck", "true"); // 로그인 성공 저장

        return "redirect:" + request.getHeader("Referer"); // 이전 페이지로 이동
    }
}


