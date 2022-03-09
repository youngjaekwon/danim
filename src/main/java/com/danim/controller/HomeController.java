package com.danim.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homeGET() {
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String homePOST(HttpServletRequest request) {
        System.out.println(request.getParameter("signup"));
        return "index";
    }
}
