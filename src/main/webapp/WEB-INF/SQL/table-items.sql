-- 회원의 번호를 일괄적으로 주기위한 시퀀스 생성
DROP SEQUENCE ITEMS_SEQ;
CREATE SEQUENCE ITEMS_SEQ
    INCREMENT BY 1
    START WITH 1
    MINVALUE  1
    MAXVALUE 9999
    NOCYCLE;


drop table ITEMS;
create table ITEMS (
                        ITEMNUM varchar2(50), -- 제품 번호 (ITEMS_SEQ를 이용)
                        CATEGORY   varchar2(50), -- 카테고리
                        MFR     varchar2(100), -- 제조사
                        NAME     varchar2(100), -- 제품명
                        INFO   varchar2(100), -- 제품정보
                        PRICE    varchar2(50), -- 가격
                        PIC varchar2(500), -- 사진
                        STOCK  varchar2(50), -- 재고 수량

                        primary key (ITEMNUM)
);
select * from ITEMS;

DECLARE
    NUM1 NUMBER :=1;

BEGIN
    WHILE(NUM1<72) --NUM이 10보다 작을때까지 LOOP실행
        LOOP
            INSERT INTO ITEMS VALUES (ITEMS_SEQ.nextval, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
            NUM1 := NUM1+1; --NUM = NUM +1
        END LOOP;
END;

SELECT * FROM ITEMS WHERE CATEGORY LIKE '%%' AND (CASE WHEN TO_NUMBER(STOCK) IS NULL THEN 0 ELSE TO_NUMBER(STOCK) END) > -1 AND (ITEMNUM LIKE '%%' OR MFR LIKE '%%' OR NAME LIKE '%%' or INFO LIKE '%%') ORDER BY ITEMNUM DESC;

ROLLBACK ;
commit ;