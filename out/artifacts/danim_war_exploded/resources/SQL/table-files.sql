-- 결제 번호를 일괄적으로 주기위한 시퀀스 생성
DROP SEQUENCE files_SEQ;
CREATE SEQUENCE files_SEQ
    INCREMENT BY 1
    START WITH 1
    MINVALUE  1
    NOCYCLE;


drop table FILES;
create table FILES (
                        FNUM varchar2(50) NOT NULL , -- 파일 번호
                        ITEMNUM varchar2(50) default '0', -- 상품 번호(상품 이미지 저장시)
                        BOARDNUM varchar2(50) default '0', -- 게시글 번호(게시글 이미지 저장시)
                        QNANUM varchar2(50) default '0', -- 1:1 문의 번호(1 : 1 문의 이미지 저장시)
                        ORIGINALFILENAME varchar2(3000) NOT NULL , -- 파일 이름
                        STOREDFILENAME varchar2(3000) NOT NULL , -- 저장된 파일 이름
                        FTYPE varchar2(1000) NOT NULL , -- 파일의 타입
                        FSIZE varchar2(1000) NOT NULL , -- 파일 사이즈

                        primary key (FNUM)
);
select * from FILES;

ROLLBACK ;
commit ;