package com.danim.shop.controller;

import com.danim.member.beans.Member;
import com.danim.member.dto.MemberDTO;
import com.danim.member.parser.MemberParser;
import com.danim.member.service.MemberService;
import com.danim.shop.beans.Items;
import com.danim.shop.beans.ItemsDTO;
import com.danim.shop.service.ItemsService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/shop")
public class ShopController {

    private final ItemsService itemsService;
    private final MemberService memberService;
    private final MemberParser memberParser;

    @Autowired
    public ShopController(ItemsService itemsService, MemberService memberService, MemberParser memberParser) {
        this.itemsService = itemsService;
        this.memberService = memberService;
        this.memberParser = memberParser;
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

    // 체크아웃 페이지
    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public ModelAndView checkout(HttpServletRequest httpServletRequest, HttpSession session) {
        ModelAndView mav = new ModelAndView("shop/shop-checkout"); // view
        String memnum = (String)session.getAttribute("user"); // 로그인된 회원번호
        // 로그인이 되어있는 경우
        if (memnum != null) {
            Member member = memberService.selectMember(memnum); // 로그인된 유저정보
            MemberDTO memberDTO = memberParser.parseMember(member); // member to memberDTO
            mav.addObject("userInfo", memberDTO); // Model에 추가
        } else {
            // 로그인이 되어있지 않을 경우
            session.setAttribute("loginCheck", "false"); // 비 로그인 상태 추가
            String currentPage = httpServletRequest.getHeader("Referer"); // 이전 페이지 확인
            mav.setViewName (currentPage != null ? ("redirect:" + currentPage) : "redirect:/");
            // 이전 페이지가 있으면 이전페이지로 이동, 없으면 index 페이지로 이동
        }
        String JSONStringItemList = httpServletRequest.getParameter("items"); // 구매 제품 리스트 JSONString

        List<ItemsDTO> itemList = new ArrayList<>(); // 구매 제품 리스트
        try {
            itemList = itemsService.getCheckoutList(JSONStringItemList); // JSONString to ArrayList
        } catch (Exception e){
            e.printStackTrace();
        }
        if (!itemList.isEmpty()) mav.addObject("itemList", itemList); // Model에 추가
        return mav;
    }
}
