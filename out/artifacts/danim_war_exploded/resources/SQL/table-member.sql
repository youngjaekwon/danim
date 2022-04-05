-- 회원의 번호를 일괄적으로 주기위한 시퀀스 생성
DROP SEQUENCE MEMBER_SEQ;
CREATE SEQUENCE MEMBER_SEQ
    INCREMENT BY 1
    START WITH 1
    MINVALUE  1
    MAXVALUE 9999
    NOCYCLE;


drop table MEMBER;
create table MEMBER (
     MEMNUM varchar2(50), -- 회원 번호 (MEMBER_SEQ를 이용)
     EMAIL   varchar2(50), -- 이메일 (아이디)
     PWD     varchar2(100), -- 비밀번호
     NAME     varchar2(50), -- 이름
     NICKNAME   varchar2(50), -- 닉네임
     ZIPCODE    varchar2(50), -- 우편번호
     ADDR varchar2(200), -- 주소
     MOBILE  varchar2(50), -- 전화번호
     BASKET varchar2(500), -- 장바구니
     WISHLIST varchar2(500), -- 위시리스트
     ROLE varchar2(50), -- 권한

     primary key (MEMNUM)
);
select * from MEMBER;
INSERT INTO MEMBER VALUES (LPAD(MEMBER_SEQ.nextval, 6, 0), 'asdf@gmail.com', 'asdf', 'admin', null, null, null, null, null, null, 1); --관리자 아이디 생성
UPDATE MEMBER SET BASKET = '' WHERE MEMNUM = 3;
UPDATE MEMBER SET ROLE = 'ROLE_MEMBER' WHERE MEMNUM =000001;
DELETE FROM MEMBER WHERE MEMNUM=000003;
DELETE FROM MEMBER WHERE MEMNUM = 000003;

commit ;