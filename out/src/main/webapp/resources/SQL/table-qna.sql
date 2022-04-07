-- 회원의 번호를 일괄적으로 주기위한 시퀀스 생성
DROP SEQUENCE QNA_SEQ;
CREATE SEQUENCE QNA_SEQ
    INCREMENT BY 1
    START WITH 1
    MINVALUE  1
    NOCYCLE;


drop table QNA;
create table QNA (
                        QNANUM varchar2(50), -- 1 : 1 문의 번호 (QNA_SEQ를 이용)
                        ORDERNUM   varchar2(50), -- 문의 대상 주문 번호
                        MEMNUM     varchar2(50), -- 문의한 유저 번호
                        CATEGORY     varchar2(100), -- 문의종류
                        TITLE   varchar2(100), -- 문의 제목
                        TXT    varchar2(1500), -- 문의 내용
                        QDATE DATE NOT NULL , -- 문의 일자
                        PIC varchar2(2000), -- 문의에 첨부된 사진들
                        COMMENTS  varchar2(500), -- 문의에 추가된 댓글들
                        STATE  varchar2(50), -- 문의 상태
                        /*
                            00 : 답변 없음
                            01 : 답변 완료
                        */

                        primary key (QNANUM)
);
select * from QNA;

ROLLBACK ;
commit ;