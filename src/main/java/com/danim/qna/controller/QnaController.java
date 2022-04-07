package com.danim.qna.controller;

import com.danim.qna.beans.QnaDTO;
import com.danim.qna.beans.QnaEntity;
import com.danim.qna.dao.QnaDao;
import com.danim.qna.service.QnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping(value = "/qna")
public class QnaController {

    private final QnaService qnaService;

    @Autowired
    public QnaController(QnaService qnaService) {
        this.qnaService = qnaService;
    }

    @RequestMapping(value = "/doReg", method = RequestMethod.POST)
    public String doReg(QnaDTO qnaDTO, MultipartHttpServletRequest multipartRequest, RedirectAttributes attributes) throws IOException {
        System.out.println(qnaDTO);
        boolean result = qnaService.regQna(qnaDTO, multipartRequest);

        if (result){
            attributes.addFlashAttribute("regQna", "passed");
            return "redirect:/member/qnaList";
        } else {
            attributes.addFlashAttribute("regQna", "failed");
            return "redirect:/member/qna_reg?ordernum="+qnaDTO.getOrdernum();
        }
    }
}
