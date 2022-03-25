package com.danim.orders.service;

import com.danim.orders.beans.Orders;
import com.danim.orders.beans.OrdersVO;
import com.danim.orders.dao.OrdersDao;
import com.danim.orders.parser.OrdersParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersService {
    private final OrdersDao ordersDao;
    private final OrdersParser ordersParser;

    @Autowired
    public OrdersService(OrdersDao ordersDao, OrdersParser ordersParser) {
        this.ordersDao = ordersDao;
        this.ordersParser = ordersParser;
    }

    // 주문 등록
    public boolean doRegOrder(Orders oder){
        return ordersDao.insert(oder) > 0;
    }

    // 주문 리스트 (관리자)
    public List<OrdersVO> getList(String state, String qna, String sorting, String keyword){
        // keyword 에 SQL 와일드카드 추가
        if (keyword != null) keyword = "%" + keyword + "%";

        // 주어진 필터들을 이용해 DB에서 리스트 검색
        List<Orders> ordersList = ordersDao.searchAllByFilters(state, qna, sorting, keyword);

        // 반환할 Orders VO List 생성 및 기존 Entity List 변환하여 저장
        List<OrdersVO> ordersVOList = ordersParser.ordersListEntitytoVO(ordersList);

        return ordersVOList; // Orders List 반환
    }

    // 주문 리스트 (회원)
    public List<OrdersVO> getList(String memnum){
        // 회원번호를 이용해 DB에서 리스트 검색
        List<Orders> ordersList = ordersDao.selectAllByAtt("MEMNUM", memnum);

        // 반환할 Orders VO List 생성 및 기존 Entity List 변환하여 저장
        List<OrdersVO> ordersVOList = ordersParser.ordersListEntitytoVO(ordersList);

        return ordersVOList; // Orders List 반환
    }

    // 등록된 가장 최근 주문번호 가저오는 메소드
    public String getCurrOrderNum(String memnum){
        // 회원번호를 이용해 DB에서 리스트 검색
        List<Orders> ordersList = ordersDao.selectAllByAtt("MEMNUM", memnum);

        // 반환할 주문번호
        String currOrderNum = ordersList.get(0).getOrdernum();

        return currOrderNum; // 주문번호 반환
    }
}
