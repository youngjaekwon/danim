package com.danim.orders.controller;

import com.danim.member.service.MemberService;
import com.danim.orders.beans.Orders;
import com.danim.orders.beans.OrdersDTO;
import com.danim.orders.parser.OrdersParser;
import com.danim.orders.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;

@Controller
@RequestMapping("/order")
public class OrdersController {
    private final OrdersService ordersService;
    private final OrdersParser ordersParser;
    private final MemberService memberService;

    @Autowired
    public OrdersController(OrdersService ordersService, OrdersParser ordersParser, MemberService memberService) {
        this.ordersService = ordersService;
        this.ordersParser = ordersParser;
        this.memberService = memberService;
    }

    // 주문 등록
    @RequestMapping(value = "/doRegOrder", method = RequestMethod.POST)
    public String doRegOrder(OrdersDTO ordersDTO, HttpSession session, HttpServletRequest request, RedirectAttributes redirectAttributes){
        String memnum = (String)session.getAttribute("user"); // 로그인된 유저 번호

        /////////////// DB에 등록할 Oder Entity 생성 ///////////////////
        Orders order = ordersParser.parseOrdersDTOtoEntity(ordersDTO); // form에서 전달받은 Order DTO to Order Entity
        order.setMemnum(memnum); // 로그인된 유저 등록
        order.setOrderdate(new Timestamp(System.currentTimeMillis())); // 주문 완료한 시간 등록
        String checkOutState = "101"; // 주문 상태: 결제 확인
        order.setState(checkOutState); // 주문 상태 등록
        String qnaState = "00"; // 1:1문의 상태: 문의 없음
        order.setQna(qnaState); // 문의 상태 등록

        // 주문 등록 성공
        if (ordersService.doRegOrder(order)) {
            // 로그인된 유저 장바구니 비우기
            memberService.clearBasket(memnum);
            redirectAttributes.addFlashAttribute("checkOut", "passed"); // 주문 성공 attribute 추가
            return "redirect:/shop/list"; ///// ***************** 마이페이지 주문내역으로 보내기!!!!!!
        } else {
            redirectAttributes.addFlashAttribute("checkOut", "failed"); // 주문 실패 attribute 추가
            String currentPage = request.getHeader("Referer"); // 이전 페이지
            return "redirect:" + currentPage; // 이전 페이지로 이동
        }
    }
}
