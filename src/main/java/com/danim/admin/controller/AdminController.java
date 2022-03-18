package com.danim.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping(value = "/orders")
    public String orders(){
        return "/admin/admin-orders";
    }
}
