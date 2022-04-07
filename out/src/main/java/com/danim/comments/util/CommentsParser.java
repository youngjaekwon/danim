package com.danim.comments.util;

import com.danim.comments.beans.CommentsDTO;
import com.danim.comments.beans.CommentsEntity;
import com.danim.comments.beans.CommentsVO;
import com.danim.member.beans.Member;
import com.danim.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class CommentsParser {

    private final MemberService memberService;

    @Autowired
    public CommentsParser(MemberService memberService) {
        this.memberService = memberService;
    }

    public CommentsEntity parse(CommentsDTO commentsDTO){
        // 리턴할 Comments Entity
        CommentsEntity commentsEntity = new CommentsEntity();

        /////////////// Entity 객체에 등록될 정보 ////////////////////////////
        // 댓글 번호
        commentsEntity.setCnum(commentsDTO.getCnum());
        // 댓글 작성 회원 번호
        commentsEntity.setMemnum(commentsDTO.getMemnum());
        // 게시글 번호
        commentsEntity.setBoardnum(commentsDTO.getBoardnum());
        // 1:1 문의 번호
        commentsEntity.setQnanum(commentsDTO.getQnanum());
        // 내용
        commentsEntity.setTxt(commentsDTO.getTxt());
        ///////////////////////////////////////////////////////////////

        // 댓글 생성한 시간 등록
        commentsEntity.setCdate(new Timestamp(System.currentTimeMillis()));

        return commentsEntity;
    }

    public CommentsVO parse(CommentsEntity commentsEntity){
        // 리턴할 Comments VO
        CommentsVO commentsVO = new CommentsVO();

        /////////////// VO 객체에 등록될 정보 ////////////////////////////
        // 댓글 번호
        commentsVO.setCnum(commentsEntity.getCnum());

        // 작성자 검색
        Member member = memberService.selectMember(commentsEntity.getMemnum());
        // 댓글 작성자 이름
        commentsVO.setName(member.getName());
        // 댓글 작성자 닉네임
        commentsVO.setNickName(member.getNickname());
        // 댓글 내용
        commentsVO.setTxt(commentsEntity.getTxt());

        // 댓글 작성일
        Date cDate = commentsEntity.getCdate();
        // 현재 시간
        Date now = new Timestamp(System.currentTimeMillis());
        // 경과 시간
        long difference = now.getTime() - cDate.getTime();

        String date = "";
        // 5분 미만
        if (difference < 1000 * 60 * 5) {
            date = "방금";
        }
        // 1시간 미만
        else if (difference < 1000 * 60 * 60) {
            int differenceInt = now.getMinutes() - cDate.getMinutes();
            date = differenceInt + "분 전";
        }
        // 1일 미만
        else if (difference < 1000 * 60 * 60 * 24){
            int differenceInt = now.getHours() - cDate.getHours();
            date = differenceInt + "시간 전";
        }
        // 1달 미만
        else if (difference < 1000L * 60 * 60 * 24 * 28){
            int differenceInt = now.getDay() - cDate.getDay();
            if (differenceInt == 0){
                differenceInt = now.getHours() - cDate.getHours();
                date = differenceInt + "시간 전";
            }
            date = differenceInt + "일 전";
        }
        // 1년 미만
        else if (difference < 1000L * 60 * 60 * 24 * 28 * 12) {
            int differenceInt = now.getMonth() - cDate.getMonth();
            if (differenceInt == 0){
                differenceInt = now.getDay() - cDate.getDay();
                date = differenceInt + "일 전";
            }
            date = differenceInt + "달 전";
        }
        // 1년 이상
        else {
            int differenceInt = now.getYear() - cDate.getYear();
            if (differenceInt == 0){
                differenceInt = now.getMonth() - cDate.getMonth();
                date = differenceInt + "달 전";
            }
            date = differenceInt + "년 전";
        }

        // VO에 경과시간 저장
        commentsVO.setDate(date);

        return commentsVO;
    }
}
