-- 회원의 번호를 일괄적으로 주기위한 시퀀스 생성
DROP SEQUENCE COMMENTS_SEQ;
CREATE SEQUENCE COMMENTS_SEQ
    INCREMENT BY 1
    START WITH 1
    MINVALUE  1
    NOCYCLE;


drop table COMMENTS;
create table COMMENTS (
                        CNUM varchar2(50), -- 댓글번호 번호 (COMMENTS_SEQ를 이용)
                        MEMNUM     varchar2(50), -- 댓글 작성자
                        BOARDNUM     varchar2(50), -- 게시글 번호 (게시글에 달린 댓글일 때)
                        QNANUM   varchar2(50), -- 1 : 1 문의 번호 (1 : 1 문의에 달린 댓글일 때)
                        TXT    varchar2(1500), -- 댓글 내용
                        CDATE DATE NOT NULL , -- 댓글 작성 일자

                        primary key (CNUM)
);
select * from COMMENTS;

ROLLBACK ;
commit ;