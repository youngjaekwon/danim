package com.danim.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ErrorHandleController {

    @RequestMapping(value = "/accessDenied")
    public String accessDenied(HttpServletRequest request, HttpSession session, RedirectAttributes attributes){
        String memnum = (String) session.getAttribute("user"); // 로그인된 회원

        // 로그인 되어 있을경우 (Admin 권한이 없는 경우)
        if (memnum != null) {
            attributes.addFlashAttribute("isAdmin", "false"); // admin 아님
        }
        // 로그인이 되어있지 않은 경우 (Member 권한이 없는 경우)
        else {
            attributes.addFlashAttribute("loginCheck", "false"); // 비 로그인 상태 추가
        }

        String currentPage = request.getHeader("Referer"); // 이전 페이지 확인
        return (currentPage != null) ? ("redirect:" + currentPage) : "redirect:/";
        // 이전 페이지가 있으면 이전페이지로 이동, 없으면 index 페이지로 이동
    }


}
