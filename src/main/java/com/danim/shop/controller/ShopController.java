package com.danim.shop.controller;

import com.danim.member.beans.Member;
import com.danim.member.dto.MemberDTO;
import com.danim.member.parser.MemberParser;
import com.danim.member.service.MemberService;
import com.danim.shop.beans.Items;
import com.danim.shop.beans.ItemsDTO;
import com.danim.shop.parser.ItemsParser;
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
import org.springframework.web.bind.annotation.RequestParam;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shop")
public class ShopController {

    private final ItemsService itemsService;
    private final MemberService memberService;
    private final MemberParser memberParser;
    private final ItemsParser itemsParser;
    private final DecimalFormat formatter;

    @Autowired
    public ShopController(ItemsService itemsService, MemberService memberService, MemberParser memberParser, ItemsParser itemsParser, DecimalFormat formatter) {
        this.itemsService = itemsService;
        this.memberService = memberService;
        this.memberParser = memberParser;
        this.itemsParser = itemsParser;
        this.formatter = formatter;
    }

    // 제품 리스트
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("/shop/shop-list"); // view 추가
        String category = httpServletRequest.getParameter("category"); // 요청된 카테고리
        List<ItemsDTO> totalList = itemsService.getList(category); // 카테고리로 리스트 검색
        // 리스트 검색이 안될경우
        if (totalList == null) {
            redirectAttributes.addFlashAttribute("invalidAccess", "true"); // 잘못된 접근 요소 추가
            mav.setViewName("redirect:/"); // index로 redirect
            return mav; // return
        }

        ////////////// 페이지 구성 요소 ///////////////
        int totalRecords = totalList.size(); // 전체 아이템 수
        int numPerPage = 18; // 한 페이지당 출력할 아이템 수
        int pagePerBlock = 6; // 하단 페이지 네비게이션 한 블럭 당 출력할 페이지 수
        int nowPage = 1; // 현재 페이지
        String requestPage = httpServletRequest.getParameter("page"); // 요청된 페이지
        if (requestPage != null) nowPage = Integer.parseInt(requestPage); // 요청된 페이지가 null이 아닐경우 현재페이지 갱신
        int totalPage = (int)Math.ceil((double)totalRecords / numPerPage); // 전체 페이지 (전체 아이템 수 / 페이지당 아이템 수)
        int totalBlock = (int)Math.ceil((double)totalPage / pagePerBlock); // 전체 블럭 (전체 페이지 수 / 블럭당 페이지 수)
        int nowBlock = (int)Math.ceil((double)nowPage / pagePerBlock); // 현재 블럭 (현재 페이지 / 블럭당 페이지 수)
        int pageStart = pagePerBlock * (nowBlock - 1) + 1; // 블럭에 표시될 페이지중 첫 페이지
        int pageEnd   = pageStart + pagePerBlock - 1; // 블럭에 표시될 페이지중 마지막 페이지
        if(pageEnd >= totalPage) pageEnd = totalPage; // 계산된 마지막 페이지가 전체 페이지 수 보다 클 경우, 마지막 페이지 = 전체 페이지 수
        int prevPage = 1; // 이전 블럭으로 이동 (1번 블럭일 경우 1페이지로 이동)
        if (nowBlock > 1) prevPage = pageStart - 1; // 2번 블럭 이상일 경우, 이전 블럭 마지막 페이지로 이동
        int nextPage = totalPage; // 다음 블럭으로 이동 (마지막 블럭일 경우 마지막 페이지로 이동)
        if (totalBlock > nowBlock) nextPage = pageEnd + 1; // 마지막 블럭이 아닐경우, 다음 블럭 첫 페이지로 이동
        int start = (nowPage * numPerPage) - numPerPage + 1; // 현재 페이지에 표시될 첫번째 아이템 번호
        int end = start + numPerPage - 1; // 현재 페이지에 표시될 마지막 아이템 번호
        if (end > totalRecords) end = totalRecords; // 계산된 마지막 아이템 번호가 전체 아이템 수 보다 클 경우, 마지막 아이템 번호 = 전체 아이템 수
        List<ItemsDTO> itemList = totalList.subList(start - 1, end); // 반환할 리스트 (첫 번째 아이템 인덱스 ~ 마지막 아이템 인덱스)
        mav.addObject("itemList", itemList); // 페이지에 표실될 아이템 리스트
        mav.addObject("prevPage", prevPage); // 이전 블럭으로 이동
        mav.addObject("nextPage", nextPage); // 다음 블럭으로 이동
        mav.addObject("pageStart", pageStart); // 블럭에 표시될 첫 번째 페이지
        mav.addObject("pageEnd", pageEnd); // 블럭에 표시될 마지막 페이지

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
            return mav;
        }
        String jsonStringItemList = httpServletRequest.getParameter("items"); // 구매 제품 리스트 JSONString

        List<ItemsDTO> itemList = new ArrayList<>(); // 구매 제품 리스트
        itemList = itemsService.getItemListFromJSONString(jsonStringItemList); // JSONString to ArrayList
        if (!itemList.isEmpty()) {
            mav.addObject("itemList", itemList); // Model에 리스트 추가
            int totalCost = 0; // 구매 제품 리스트 총금액
            // 총 금액 산출
            for (ItemsDTO item : itemList){
                totalCost += item.getPrice();
            }
            mav.addObject("formattedTotalCost", formatter.format(totalCost)); // #,###,### 포멧으로 변경된 총 금액 전달
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
                if (itemsService.addBasketList(member, jsonStringBasketListFromCookie)) { // 멤버 장바구니 업데이트
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
            List<ItemsDTO> itemList = itemsService.getItemListFromJSONString(jsonStringBasketList); // JSONString to ArrayList
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
        } else {
            List<ItemsDTO> itemList = itemsService.getItemListFromJSONString(jsonStringBasketListFromCookie); // JSONString to ArrayList
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

        // 로그인된 경우
        if (memnum != null){
            return itemsService.addBasketList(memnum, addItem) ? "passed" : "failed"; // 추가 요청된 아이템 장바구니에 추가 후 결과 리턴
        } else {
            try {
                // 비로그인의 경우
                String basketListNotLogin = ""; // 리턴할 장바구니 리스트 생성
                // 쿠키에서 장바구니 리스트 불러옴 ////////
                Cookie[] cookies = httpServletRequest.getCookies();
                for (Cookie cookie:cookies){
                    if (cookie.getName().equals("basketList")) basketListNotLogin = URLDecoder.decode(cookie.getValue(), "UTF-8");
                }
                ///////////////////////////////////
                // 비로그인 장바구니에 추가
                basketListNotLogin = itemsService.addBasketListNotLogin(basketListNotLogin, addItem);
                httpServletResponse.addCookie(new Cookie("basketList", URLEncoder.encode(basketListNotLogin, "UTF-8"))); // 쿠키에 추가
            } catch (Exception e){
                e.printStackTrace();
            }
            return "passed";
        }
    }
}
