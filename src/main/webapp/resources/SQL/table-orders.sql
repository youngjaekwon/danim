-- 회원의 번호를 일괄적으로 주기위한 시퀀스 생성
DROP SEQUENCE ORDERS_SEQ;
CREATE SEQUENCE ORDERS_SEQ
    INCREMENT BY 1
    START WITH 1
    MINVALUE  1
    MAXVALUE 9999
    NOCYCLE;

drop table ORDERS;
create table ORDERS (
                        ORDERNUM varchar2(20) NOT NULL , -- 주문번호 (ORDERS_SEQ를 이용)
                        MEMNUM varchar2(50) NOT NULL , -- 주문한 유저
                        ORDERDATE DATE NOT NULL , -- 주문일자
                        NAME varchar2(50) NOT NULL , -- 이름
                        ZIPCODE varchar2(50), -- 우편번호
                        ADDR     varchar2(200), -- 주소
                        MOBILE   varchar2(100), -- 전화번호
                        STATE    varchar2(50), -- 주문상태
                        /*
                        * 주문 상태
                        * 10X: 진행중인 주문
                        * 20X: 종료된 주문
                        *
                        * 101: 결제 확인
                        * 102: 상품 준비중
                        * 103: 배송중
                        * 201: 배송완료
                        * 202: 취소
                        * */
                        WAYBILLNUM varchar2(50), -- 운송장번호
                        PRICE  varchar2(50), -- 결제 금액
                        PAYMENT varchar2(50), -- 결제 수단
                        REQUEST varchar2(1000), -- 요청사항
                        ITEMSLIST varchar2(500), -- 아이템 리스트 (JSONArray의 String 형태로 저장)
                        QNA varchar2(20), -- 1 : 1 문의 여부 00: 없음, 01: 미답변, 02: 답변완료

                        primary key (ORDERNUM)
);
select * from ORDERS;

SELECT * FROM ORDERS WHERE STATE LIKE '101' AND QNA LIKE '0%' ORDER BY ORDERNUM DESC;
SELECT * FROM ORDERS WHERE (ORDERNUM LIKE '%%' OR NAME LIKE '%%' OR ADDR LIKE '%%') AND (STATE LIKE '%%' AND QNA LIKE '%%') ORDER BY ORDERNUM DESC;

ROLLBACK ;
commit ;