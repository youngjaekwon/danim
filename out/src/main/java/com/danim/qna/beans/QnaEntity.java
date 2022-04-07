package com.danim.qna.beans;

import java.util.Date;

public class QnaEntity {
    private String qnanum; // 1 : 1 문의 번호
    private String ordernum; // 문의 대상 주문 번호
    private String memnum; // 문의한 유저 번호
    private String category; // 문의종류
    private String title; // 문의 제목
    private String txt; // 문의 내용
    private Date qdate; // 문의 일자
    private String pic; // 문의에 첨부된 사진들
    private String comments; // 문의에 추가된 댓글들
    private String state; // 문의 상태

    public String getQnanum() {
        return qnanum;
    }

    public void setQnanum(String qnanum) {
        this.qnanum = qnanum;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public String getMemnum() {
        return memnum;
    }

    public void setMemnum(String memnum) {
        this.memnum = memnum;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getQdate() {
        return qdate;
    }

    public void setQdate(Date qdate) {
        this.qdate = qdate;
    }

    @Override
    public String toString() {
        return "QnaEntity{" +
                "qnanum='" + qnanum + '\'' +
                ", ordernum='" + ordernum + '\'' +
                ", memnum='" + memnum + '\'' +
                ", category='" + category + '\'' +
                ", title='" + title + '\'' +
                ", txt='" + txt + '\'' +
                ", qdate=" + qdate +
                ", pic='" + pic + '\'' +
                ", comments='" + comments + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
