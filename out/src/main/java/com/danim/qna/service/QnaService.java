package com.danim.qna.service;

import com.danim.comments.service.CommentsService;
import com.danim.files.beans.FilesEntity;
import com.danim.files.service.FilesService;
import com.danim.files.util.MultipartFileUploadProcessor;
import com.danim.orders.beans.Orders;
import com.danim.orders.beans.OrdersVO;
import com.danim.orders.service.OrdersService;
import com.danim.qna.beans.QnaDTO;
import com.danim.qna.beans.QnaEntity;
import com.danim.qna.beans.QnaVO;
import com.danim.qna.dao.QnaDao;
import com.danim.qna.util.QnaParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class QnaService {
    private final QnaParser qnaParser;
    private final QnaDao qnaDao;
    private final FilesService filesService;
    private final CommentsService commentsService;
    private final OrdersService ordersService;

    @Autowired
    public QnaService(QnaParser qnaParser, QnaDao qnaDao, FilesService filesService, CommentsService commentsService, OrdersService ordersService) {
        this.qnaParser = qnaParser;
        this.qnaDao = qnaDao;
        this.filesService = filesService;
        this.commentsService = commentsService;
        this.ordersService = ordersService;
    }

    public QnaVO getQna(String qnanum){
        QnaEntity qnaEntity = qnaDao.select(qnanum);
        return qnaParser.parse(qnaEntity);
    }

    // 1 : 1 문의 등록
    public boolean regQna(QnaDTO qnaDTO, MultipartHttpServletRequest multipartRequest) throws IOException {
        // DB에 저장할 Entity 생성
        QnaEntity qna = qnaParser.parse(qnaDTO);

        // multipart request로 넘어온 파일 등록 및 파싱
        Map<String, Object> filesMap = MultipartFileUploadProcessor.parsFiles(multipartRequest);

        // DB에 추가할 파일 리스트
        List<FilesEntity> uploadedFiles = null;

        if (filesMap != null) {
            // DB에 추가할 파일 리스트 갱신
            uploadedFiles = (List<FilesEntity>) filesMap.get("filesEntityList");
            // itemsDTO에 저장할 사진 리스트
            String pics = (String) filesMap.get("storedFileNames");

            // itemsDTO에 사진 리스트 저장
            qna.setPic(pics);
        }

        // item 등록 및 등록된 item 번호 가져옴
        String qnanum = qnaDao.insert(qna);
        if (qnanum == null || qnanum.isEmpty()) return false;

        // DB에 파일 추가
        filesService.regFiles("QNANUM", qnanum, uploadedFiles);

        // 주문의 문의상태 변경
        ordersService.update(qna.getOrdernum(), "QNA", "01");

        return true;
    }

    public boolean delQna(String qnanum){
        if(!filesService.delFile("QNANUM", qnanum)) return false;
        if(!commentsService.delComments("QNANUM", qnanum)) return false;
        return qnaDao.delete(qnanum) > 0;
    }

    // 문의 리스트 (관리자)
    public List<QnaVO> getList(String category, String state, String sorting, String keyword){
        // 주어진 필터들을 이용해 DB에서 리스트 검색
        List<QnaEntity> qnaEntityList = qnaDao.searchAllByFilters(category, state, sorting, keyword);

        // 반환할 Qna VO List 생성 및 기존 Entity List 변환하여 저장
        List<QnaVO> qnaVOList = new ArrayList<>();
        if (qnaEntityList != null) {
            qnaEntityList.forEach((qnaEntity -> {
                qnaVOList.add(qnaParser.parse(qnaEntity));
            }));
        }

        return qnaVOList; // Qna List 반환
    }

    // 문의 리스트 (회원)
    public List<QnaVO> getList(String memnum){
        // 회원번호를 이용해 DB에서 리스트 검색
        List<QnaEntity> qnaEntityList = qnaDao.searchAllByAtt("MEMNUM", memnum);

        // 반환할 Qna VO List 생성 및 기존 Entity List 변환하여 저장
        List<QnaVO> qnaVOList = new ArrayList<>();
        if (qnaEntityList != null) {
            qnaEntityList.forEach((qnaEntity -> {
                qnaVOList.add(qnaParser.parse(qnaEntity));
            }));
        }

        return qnaVOList; // Qna List 반환
    }
}
