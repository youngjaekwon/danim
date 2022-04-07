package com.danim.comments.controller;

import com.danim.comments.beans.CommentsDTO;
import com.danim.comments.beans.CommentsVO;
import com.danim.comments.service.CommentsService;
import com.danim.qna.service.QnaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/comments")
@Controller
public class CommentsController {
    private final CommentsService commentsService;
    private final JSONParser jsonParser;
    private final ObjectMapper objectMapper;
    private final QnaService qnaService;

    @Autowired
    public CommentsController(CommentsService commentsService, JSONParser jsonParser, ObjectMapper objectMapper, QnaService qnaService) {
        this.commentsService = commentsService;
        this.jsonParser = jsonParser;
        this.objectMapper = objectMapper;
        this.qnaService = qnaService;
    }

    @RequestMapping(value = "/doReg", consumes = "application/json;charset=UTF-8", produces ="application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public String doReg(@RequestBody CommentsDTO commentsDTO) throws JsonProcessingException {

        // 댓글 등록 및 등록된 댓글의 VO 반환
        CommentsVO comment = commentsService.doReg(commentsDTO);

        // DTO role 확인
        String role = commentsDTO.getRole();

        // QNA 상태 변경
        if (commentsDTO.getQnanum() != null && role.equals("ROLE_ADMIN")){
            qnaService.updateState(commentsDTO.getQnanum());
        }


        String jsonComment = objectMapper.writeValueAsString(comment);

        return jsonComment;
    }

    @RequestMapping(value = "/doDel", method = RequestMethod.GET)
    @ResponseBody
    public boolean doDel(@RequestParam String cnum){
        boolean result = commentsService.doDel(cnum);
        return result;
    }
}
