package com.danim.shop.controller;

import com.danim.common.paging.PageMaker;
import com.danim.items.service.ItemsService;
import com.danim.member.beans.Member;
import com.danim.member.dto.MemberDTO;
import com.danim.member.parser.MemberParser;
import com.danim.member.service.MemberService;
import com.danim.items.beans.Items;
import com.danim.items.beans.ItemsDTO;
import com.danim.items.parser.ItemsParser;
import com.danim.shop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/shop")
public class ShopController {

    private final ShopService shopService;
    private final MemberService memberService;
    private final MemberParser memberParser;
    private final ItemsService itemsService;
    private final ItemsParser itemsParser;
    private final DecimalFormat formatter;

    @Autowired
    public ShopController(ShopService shopService, MemberService memberService, MemberParser memberParser, ItemsService itemsService, ItemsParser itemsParser, DecimalFormat formatter) {
        this.shopService = shopService;
        this.memberService = memberService;
        this.memberParser = memberParser;
        this.itemsService = itemsService;
        this.itemsParser = itemsParser;
        this.formatter = formatter;
    }

    // 제품 리스트
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("/shop/shop-list"); // view 추가

        // paging 요소
        String[] category = httpServletRequest.getParameterValues("category"); // 요청된 카테고리
        String sorting = httpServletRequest.getParameter("sort"); // 정렬 방법
        String keyword = httpServletRequest.getParameter("keyword"); // 검색 키워드
        String requestPage = httpServletRequest.getParameter("page"); // 요청된 페이지

        // Parameter 값 없는 경우 Defalt값 설정
        if (sorting == null) sorting = "ITEMNUM";
        if (keyword == null || keyword.equals("")) keyword = "%%";
        if (requestPage != null && requestPage.equals("")) requestPage = "1";

        // 카테고리로 리스트 검색
        List<ItemsDTO> totalList = shopService.getList(category, sorting, keyword);

        // 리스트 검색이 안될경우
        if (totalList == null) {
            redirectAttributes.addFlashAttribute("invalidAccess", "true"); // 잘못된 접근 요소 추가
            mav.setViewName("redirect:/"); // index로 redirect
            return mav; // return
        }

        int numPerPage = 18; // 한 페이지당 출력할 아이템 수: 제품 리스트의 경우 18개
        int pagePerBlock = 6; // 하단 페이지 네비게이션 한 블럭 당 출력할 페이지 수: 제품 리스트의 경우 6개

        // 검색된 리스트, 요청된 페이지를 이용하여 페이지 생성
        PageMaker.makePage(mav, totalList, requestPage, numPerPage, pagePerBlock);

        List<String> categoryList = new ArrayList<>();
        if (category != null){
            categoryList = Arrays.asList(category);
        }
        // 체크할 filter 값 전달
        mav.addObject("category", categoryList.toString());
        mav.addObject("sorting", sorting);
        if (!keyword.equals("%%"))
            mav.addObject("keyword", keyword);

        return mav;
    }

    // 제품 상세 페이지
    @RequestMapping(value = "/item", method = RequestMethod.GET)
    public ModelAndView item(HttpServletRequest httpServletRequest) {
        String itemnum = httpServletRequest.getParameter("item"); // 제품 번호
        Items item = itemsService.select(itemnum); // item select
        ItemsDTO itemsDTO = itemsParser.parseItems(item);
        ModelAndView mav = new ModelAndView("shop/shop-item"); // view
        mav.addObject("itemVO", itemsDTO); // itemVO 추가
        return mav;
    }

    // 체크아웃 페이지
    @RequestMapping(value = "/checkout", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView checkout(HttpServletRequest request, HttpSession session) {
        ModelAndView mav = new ModelAndView("shop/shop-checkout"); // view
        String memnum = (String)session.getAttribute("user"); // 로그인된 회원번호
        Member member = null; // 로그인된 유저 객체 담을 변수

        member = memberService.selectMember(memnum); // 로그인된 유저정보
        MemberDTO memberDTO = memberParser.parseMember(member); // member to memberDTO
        mav.addObject("userInfo", memberDTO); // Model에 추가

        //  로그인된 유저의 장바구니 리스트 가져옴 JSONString
        String jsonStringItemList = member.getBasket();

        // 단일 아이템 체크아웃의 경우
        String singleItemCheckOut = request.getParameter("item");
        if (singleItemCheckOut != null) jsonStringItemList = singleItemCheckOut; // 구매 제품 리스트 변경

        List<ItemsDTO> itemList = shopService.getItemListFromJSONString(jsonStringItemList); // 구매 제품 리스트 JSONString to ArrayList
        if (itemList != null && !itemList.isEmpty()) {
            mav.addObject("itemList", itemList); // Model에 리스트 추가
            mav.addObject("jsonStringItemList", jsonStringItemList); // Model에 Json String 형태의 리스트 추가
            int totalCost = 0; // 구매 제품 리스트 총금액
            // 총 금액 산출
            for (ItemsDTO item : itemList) {
                totalCost += item.getPrice();
            }
            mav.addObject("totalCost", totalCost); // 총 금액 전달
            mav.addObject("formattedTotalCost", formatter.format(totalCost)); // #,###,### 포멧으로 변경된 총 금액 전달
        } else {
            mav.addObject("emptyBasket", "true");
            mav.setViewName("redirect:/shop/basket");
        }
        return mav;
    }

    // 장바구니
    @RequestMapping(value = "/basket", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView basket(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, HttpSession session) {
        ModelAndView mav = new ModelAndView("shop/shop-basket"); // view
        String memnum = (String)session.getAttribute("user"); // 로그인된 회원번호

        // 쿠키에서 장바구니 리스트 불러옴 ////////
        String jsonStringBasketListFromCookie = "";
        Cookie[] cookies = httpServletRequest.getCookies();
        for (Cookie cookie:cookies){
            try {
                if (cookie.getName().equals("basketList")) jsonStringBasketListFromCookie = URLDecoder.decode(cookie.getValue(), "UTF-8");
            } catch (Exception e){
                e.printStackTrace();
            }

        }
        //////////////////////////////////

        // 로그인 된 경우
        if (memnum != null){
            Member member = memberService.selectMember(memnum); // 로그인된 멤버
            String jsonStringBasketList = member.getBasket(); // 저장된 장바구니 리스트
            if (!jsonStringBasketListFromCookie.isEmpty()) { // 쿠키에 장바구니가 저장된 경우
                if (shopService.addBasketList(member, jsonStringBasketListFromCookie)) { // 멤버 장바구니 업데이트
                    jsonStringBasketList = member.getBasket(); // 저장된 장바구니 리스트 다시 가져옴
                    try {
                        Cookie cookie = new Cookie("basketList", null); // 삭제할 쿠키에 대한 값을 null로 지정
                        cookie.setMaxAge(0); // 유효시간을 0으로 설정해서 바로 만료시킨다.
                        httpServletResponse.addCookie(cookie); // 응답에 추가해서 없어지도록 함
//                        httpServletResponse.addCookie(new Cookie("basketList", URLEncoder.encode(jsonStringBasketList, "UTF-8"))); // 쿠키 업데이트
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            List<ItemsDTO> itemList = shopService.getItemListFromJSONString(jsonStringBasketList); // JSONString to ArrayList
            // 리스트 생성이 안될경우
            if (itemList == null) {
                itemList = new ArrayList<>();
                mav.addObject("itemList", itemList); // 빈 리스트 전달
                return mav; // return
            }
            mav.addObject("itemList", itemList); // 장바구니 리스트 전달
            int totalCost = 0; // 구매 제품 리스트 총금액
            // 총 금액 산출
            for (ItemsDTO item : itemList){
                totalCost += item.getPrice();
            }
            mav.addObject("totalCost", totalCost); // 총 금액 전달
            mav.addObject("formattedTotalCost", formatter.format(totalCost)); // #,###,### 포멧으로 변경된 총 금액 전달
            return mav;
        }
        // 로그인이 안된경우
        else {
            List<ItemsDTO> itemList = shopService.getItemListFromJSONString(jsonStringBasketListFromCookie); // JSONString to ArrayList
            // 리스트 생성이 안될경우 (리스트가 비어있을경우)
            if (itemList == null) {
                itemList = new ArrayList<>();
                mav.addObject("itemList", itemList); // 빈 리스트 전달
                return mav; // return
            }
            mav.addObject("itemList", itemList); // 장바구니 리스트 전달
            int totalCost = 0; // 구매 제품 리스트 총금액
            // 총 금액 산출
            for (ItemsDTO item : itemList){
                totalCost += item.getPrice();
            }
            mav.addObject("totalCost", totalCost); // 총 금액 전달
            mav.addObject("formattedTotalCost", formatter.format(totalCost)); // #,###,### 포멧으로 변경된 총 금액 전달
            return mav;
        }
    }

    // 장바구니에 물품 추가
    @RequestMapping(value = "/addtoBasket", method = RequestMethod.POST)
    @ResponseBody
    public String addtoBasket(HttpSession session, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        String memnum = (String)session.getAttribute("user"); // 로그인된 회원번호
        String addItem = httpServletRequest.getParameter("item"); // 추가 요청된 아이템과 수량
        if (addItem == null) return "failed"; // 추가할 아이템이 없으면 failed 리턴

        /////////////////////////////////////// 로그인된 경우 ///////////////////////////////////////
        if (memnum != null){
            return shopService.addBasketList(memnum, addItem) ? "passed" : "failed"; // 추가 요청된 아이템 장바구니에 추가 후 결과 리턴
        } else {
            /////////////////////////////////////// 비로그인의 경우 ///////////////////////////////////////
            try {
                String basketListNotLogin = ""; // JSONString 장바구니 리스트
                // 쿠키에서 장바구니 리스트 불러옴 ////////
                Cookie[] cookies = httpServletRequest.getCookies();
                for (Cookie cookie:cookies){
                    if (cookie.getName().equals("basketList")) basketListNotLogin = URLDecoder.decode(cookie.getValue(), "UTF-8");
                }
                ///////////////////////////////////
                // 비로그인 장바구니에 추가
                basketListNotLogin = shopService.addBasketListNotLogin(basketListNotLogin, addItem);
                httpServletResponse.addCookie(new Cookie("basketList", URLEncoder.encode(basketListNotLogin, "UTF-8"))); // 쿠키에 추가
            } catch (Exception e){
                e.printStackTrace();
            }
            return "passed";
        }
    }

    // 장바구니에서 물품 삭제
    @RequestMapping(value = "/deleteItemfromBasket", method = RequestMethod.POST)
    @ResponseBody
    public String deleteItemfromBasket(HttpSession session, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        String memnum = (String)session.getAttribute("user"); // 로그인된 회원번호
        String itemnum = httpServletRequest.getParameter("itemnum"); // 삭제 요청된 상품번호
        if (itemnum == null) return "failed"; // 상품 번호가 없으면 false 리턴

        /////////////////////////////////////// 로그인된 경우 ///////////////////////////////////////
        if (memnum != null){
            return shopService.deleteItemfromBasket(memnum, itemnum) ? "passed" : "failed"; // 추가 요청된 아이템 장바구니에 추가 후 결과 리턴
        } else {
            /////////////////////////////////////// 비로그인의 경우 ///////////////////////////////////////
            try {
                String basketListNotLogin = ""; // 리턴할 장바구니 리스트 생성
                // 쿠키에서 장바구니 리스트 불러옴 ////////
                Cookie[] cookies = httpServletRequest.getCookies();
                for (Cookie cookie:cookies){
                    if (cookie.getName().equals("basketList")) basketListNotLogin = URLDecoder.decode(cookie.getValue(), "UTF-8");
                }
                ///////////////////////////////////
                // 비로그인 장바구니에서 삭제
                basketListNotLogin = shopService.deleteItemfromBasketNotLogin(basketListNotLogin, itemnum);

                httpServletResponse.addCookie(new Cookie("basketList", URLEncoder.encode(basketListNotLogin, "UTF-8"))); // 쿠키에 추가
            } catch (Exception e){
                e.printStackTrace();
            }
            return "passed";
        }
    }
}
