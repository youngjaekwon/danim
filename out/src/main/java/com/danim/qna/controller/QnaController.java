package com.danim.qna.controller;

import com.danim.orders.service.OrdersService;
import com.danim.qna.beans.QnaDTO;
import com.danim.qna.beans.QnaEntity;
import com.danim.qna.dao.QnaDao;
import com.danim.qna.service.QnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping(value = "/qna")
public class QnaController {

    private final QnaService qnaService;
    private final OrdersService ordersService;

    @Autowired
    public QnaController(QnaService qnaService, OrdersService ordersService) {
        this.qnaService = qnaService;
        this.ordersService = ordersService;
    }

    @RequestMapping(value = "/doReg", method = RequestMethod.POST)
    public String doReg(QnaDTO qnaDTO, MultipartHttpServletRequest multipartRequest, RedirectAttributes attributes) throws IOException {
        boolean result = qnaService.regQna(qnaDTO, multipartRequest);

        if (result){
            attributes.addFlashAttribute("regQna", "passed");
            return "redirect:/member/qnaList";
        } else {
            attributes.addFlashAttribute("regQna", "failed");
            return "redirect:/member/qna_reg?ordernum="+qnaDTO.getOrdernum();
        }
    }

    @RequestMapping(value = "/doDel", method = RequestMethod.POST)
    public String doDel(@RequestParam String qnanum, RedirectAttributes attributes) throws IOException {
        // qnanum을 전달해 DB에서 QNA 삭제 후 QNA에 해당하는 ordernum을 가져옴
        String ordernum = qnaService.delQna(qnanum);

        if (ordernum != null){
            // 해당 주문의 문의 상태를 "00"(없음)으로 변경
            ordersService.update(ordernum, "QNA", "00");
            attributes.addFlashAttribute("delQna", "passed");
            return "redirect:/member/qnaList";
        } else {
            attributes.addFlashAttribute("delQna", "failed");
            return "redirect:/member/qna?qnanum="+qnanum;
        }
    }
}
