-- 결제 번호를 일괄적으로 주기위한 시퀀스 생성
DROP SEQUENCE PAYMENTS_SEQ;
CREATE SEQUENCE PAYMENTS_SEQ
    INCREMENT BY 1
    START WITH 1
    MINVALUE  1
    MAXVALUE 9999
    NOCYCLE;


drop table PAYMENTS;
create table PAYMENTS (
                        PAYMENTSNUM varchar2(20) NOT NULL , -- 결제 번호 (PAYMENTS_SEQ를 이용)
                        ORDERNUM varchar2(50) NOT NULL , -- 주문 번호 (ORDER TABLE FOREIGN KEY)
                        IMPUID varchar2(50) NOT NULL , -- 고유 아이디
                        MERCHANTUID varchar2(50) NOT NULL , -- 상점 거래 ID
                        PAIDAMOUNT varchar2(50), -- 결제금액
                        APPLYNUM varchar2(50), -- 카드 승인번호
                        STATE varchar2(50), -- 결제 상태 00: 승인완료 01: 취소

                        primary key (PAYMENTSNUM)
);
select * from PAYMENTS;

ROLLBACK ;
commit ;