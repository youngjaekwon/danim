package com.danim.shop.controller;

import com.danim.shop.beans.Items;
import com.danim.shop.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/shop")
public class ShopController {

    private final ItemsService itemsService;

    @Autowired
    public ShopController(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    // 제품 상세 페이지
    @RequestMapping(value = "/item", method = RequestMethod.GET)
    public ModelAndView item(HttpServletRequest httpServletRequest) {
        String itemnum = httpServletRequest.getParameter("item"); // 제품 번호
        Items item = itemsService.select(itemnum); // item select
        ModelAndView mav = new ModelAndView("shop/shop-item"); // view
        mav.addObject("itemVO", item); // itemVO 추가
        return mav;
    }
}
