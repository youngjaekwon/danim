package com.danim.comments.service;

import com.danim.comments.beans.CommentsDTO;
import com.danim.comments.beans.CommentsEntity;
import com.danim.comments.beans.CommentsVO;
import com.danim.comments.dao.CommentsDao;
import com.danim.comments.util.CommentsParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentsService {
    private final CommentsDao commentsDao;
    private final CommentsParser commentsParser;

    @Autowired
    public CommentsService(CommentsDao commentsDao, CommentsParser commentsParser) {
        this.commentsDao = commentsDao;
        this.commentsParser = commentsParser;
    }

    public CommentsVO doReg(CommentsDTO comment){
        // 등록할 댓글
        CommentsEntity commentsEntity = commentsParser.parse(comment);

        // 등록 후 등록된 댓글 번호 반환
        String cnum = commentsDao.insert(commentsEntity);


        CommentsEntity selectedComment = commentsDao.select(cnum);

        // 반환할 VO 생성
        CommentsVO commentsVO = commentsParser.parse(selectedComment);

        return commentsVO;
    }

    public boolean doDel(String cnum){
        return commentsDao.delete(cnum) > 0;
    }

    public boolean delComments(String from, String num){
        return commentsDao.delete(from, num) > 0;
    }

    public List<CommentsVO> getList(String from, String num){
        // Comments Entity List 검색
        List<CommentsEntity> commentsEntityList = commentsDao.selectComments(from, num);

        // entity to VO
        List<CommentsVO> commentsVOList = new ArrayList<>();
        if (commentsEntityList != null){
            commentsEntityList.forEach(commentsEntity -> commentsVOList.add(commentsParser.parse(commentsEntity)));
        }

        return commentsVOList;
    }
}
