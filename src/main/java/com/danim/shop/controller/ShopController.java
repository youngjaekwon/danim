package com.danim.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/shop")
public class ShopController {
    
    @RequestMapping(value = "/item", method = RequestMethod.GET)
    public String signup() {
        return "shop/shop-item";
    }
}
