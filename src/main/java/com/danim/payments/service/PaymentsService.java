package com.danim.payments.service;

import com.danim.orders.service.OrdersService;
import com.danim.payments.beans.Payments;
import com.danim.payments.dao.PaymentsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentsService {
    private final PaymentsDao paymentsDao;
    private final OrdersService ordersService;

    @Autowired
    public PaymentsService(PaymentsDao paymentsDao, OrdersService ordersService) {
        this.paymentsDao = paymentsDao;
        this.ordersService = ordersService;
    }

    // 결제 내역 등록메소드
    public boolean doRegPayment(Payments payments, String memnum){
        /* 주문번호 등록
        * 가장 최근 등록된 주문번호를 가져와 등록
        * */
        String currOrderNum = ordersService.getCurrOrderNum(memnum);
        payments.setOrdernum(currOrderNum); // 결제 내역에 주문번호 등록

        // 결제완료 상태 추가
        String paymentFinished = "00";
        payments.setState(paymentFinished);

        return paymentsDao.insert(payments) > 0;
    }
}
