package com.danim.admin.controller;

import com.danim.common.paging.PageMaker;
import com.danim.member.service.MemberService;
import com.danim.orders.beans.Orders;
import com.danim.orders.beans.OrdersVO;
import com.danim.orders.service.OrdersService;
import com.danim.shop.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final OrdersService ordersService;
    private final ItemsService itemsService;
    private final MemberService memberService;

    @Autowired
    public AdminController(OrdersService ordersService, ItemsService itemsService, MemberService memberService) {
        this.ordersService = ordersService;
        this.itemsService = itemsService;
        this.memberService = memberService;
    }

    @RequestMapping(value = "/orders", method = {RequestMethod.GET})
    public ModelAndView orders(HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes){
        ModelAndView mav = new ModelAndView("/admin/admin-orders"); // view 추가

        // paging 요소
        String state = httpServletRequest.getParameter("state"); // 주문 상태
        String qna = httpServletRequest.getParameter("qna"); // 1:1 문의 상태
        String sorting = httpServletRequest.getParameter("sort"); // 정렬 방법
        String keyword = httpServletRequest.getParameter("keyword"); // 검색 키워드
        String requestPage = httpServletRequest.getParameter("page"); // 요청된 페이지

        // Parameter 값 없는 경우 Defalt값 설정
        if (state == null) state = "%%";
        if (qna == null) qna = "%%";
        if (sorting == null) sorting = "ORDERNUM DESC";
        if (keyword == null || keyword.equals("")) keyword = "%%";
        if (requestPage != null && requestPage.equals("")) requestPage = "1";

        // paging 요소로 리스트 검색
        List<OrdersVO> totalList = ordersService.getList(state, qna, sorting, keyword);

        // 리스트 검색이 안될경우
        if (totalList == null) {
//            redirectAttributes.addFlashAttribute("invalidAccess", "true"); // 잘못된 접근 요소 추가
//            mav.setViewName("redirect:/"); // index로 redirect
//            return mav; // return
            totalList = new ArrayList<>();
        }

        int numPerPage = 10; // 한 페이지당 출력할 아이템 수: 주문리스트의 경우 10개
        int pagePerBlock = 6; // 하단 페이지 네비게이션 한 블럭 당 출력할 페이지 수: 주문 리스트의 경우 6개

        // 검색된 리스트, 요청된 페이지를 이용하여 페이지 생성
        PageMaker.makePage(mav, totalList, requestPage, numPerPage, pagePerBlock);

        // 체크할 filter 값 전달
        mav.addObject("state", state);
        mav.addObject("qna", qna);
        mav.addObject("sorting", sorting);
        if (!keyword.equals("%%"))
            mav.addObject("keyword", keyword);

        return mav;
    }
}
