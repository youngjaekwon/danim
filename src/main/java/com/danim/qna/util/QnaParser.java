package com.danim.qna.util;

import com.danim.files.service.FilesService;
import com.danim.member.beans.Member;
import com.danim.member.service.MemberService;
import com.danim.qna.beans.QnaDTO;
import com.danim.qna.beans.QnaEntity;
import com.danim.qna.beans.QnaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@Service
public class QnaParser {

    private final SimpleDateFormat simpleDateFormat;
    private final FilesService filesService;
    private final MemberService memberService;

    @Autowired
    public QnaParser(SimpleDateFormat simpleDateFormat, FilesService filesService, MemberService memberService) {
        this.simpleDateFormat = simpleDateFormat;
        this.filesService = filesService;
        this.memberService = memberService;
    }

    public QnaEntity parse(QnaDTO qnaDTO){
        // 리턴할 qnaEntity
        QnaEntity qnaEntity = new QnaEntity();

        /////////////// Entity 객체에 등록될 정보 ////////////////////////////
        // 문의 번호
        qnaEntity.setQnanum(qnaDTO.getQnanum());
        // 주문 번호
        qnaEntity.setOrdernum(qnaDTO.getOrdernum());
        // 문의한 회원 번호
        qnaEntity.setMemnum(qnaDTO.getMemnum());
        // 문의 종류
        qnaEntity.setCategory(qnaDTO.getCategory());
        // 문의 제목
        qnaEntity.setTitle(qnaDTO.getTitle());
        // 문의 내용
        qnaEntity.setTxt(qnaDTO.getTxt());
        ///////////////////////////////////////////////////////////////

        // QNA 생성한 시간 등록
        qnaEntity.setQdate(new Timestamp(System.currentTimeMillis()));
        // 답변 없음 문의 상태 등록
        qnaEntity.setState("00");

        return qnaEntity;
    }

    public QnaVO parse(QnaEntity qnaEntity){

        // 리턴할 VO
        QnaVO qnaVO = new QnaVO();

        /////////////// VO 객체에 등록될 정보 ////////////////////////////
        // 문의 번호
        qnaVO.setQnanum(qnaEntity.getQnanum());
        // 주문 번호
        qnaVO.setOrdernum(qnaEntity.getOrdernum());
        // 문의한 회원 번호
        String memnum = qnaEntity.getMemnum();
        qnaVO.setMemnum(memnum);

        // 회원 번호로 회원 검색
        Member member = memberService.selectMember(memnum);

        // 유저 아이디
        qnaVO.setUserId(member.getEmail());

        // 유저 닉네임
        qnaVO.setUserName(member.getName());

        // 문의 종류
        String category = qnaEntity.getCategory();
        Map<String, String> categoryMap = new HashMap<>(){
            {
                put("shipping", "배송");
                put("payment", "결제");
                put("item", "상품");
            }
        };
        qnaVO.setCategory(categoryMap.get(category));
        // 문의 제목
        qnaVO.setTitle(qnaEntity.getTitle());
        // 문의 내용
        qnaVO.setTxt(qnaEntity.getTxt());
        // 문의 일자 (yyyy-HH-dd 형식)
        String date = simpleDateFormat.format(qnaEntity.getQdate());
        qnaVO.setQdate(date);
        // 문의에 첨부된 사진들
        String qnanum = qnaEntity.getQnanum();
        qnaVO.setPics(filesService.getList("QNANUM", qnanum));
        // 문의에 추가된 댓글들

        // 문의에 추가된 댓글 갯수
        qnaVO.setCommentsNum(0);
        // 문의 상태
        String state = qnaEntity.getState();
        Map<String, String> stateMap = new HashMap<>(){
            {
                put("00", "답변대기");
                put("01", "답변완료");
            }
        };
        qnaVO.setState(stateMap.get(state));
        ///////////////////////////////////////////////////////////////

        return qnaVO;
    }
}
