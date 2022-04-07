package com.danim.qna.beans;

public class QnaDTO {
    private String qnanum; // 1 : 1 문의 번호
    private String ordernum; // 문의 대상 주문 번호
    private String memnum; // 문의한 유저 번호
    private String category; // 문의종류
    private String title; // 문의 제목
    private String txt; // 문의 내용

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

    @Override
    public String toString() {
        return "QnaDTO{" +
                "qnanum='" + qnanum + '\'' +
                ", ordernum='" + ordernum + '\'' +
                ", memnum='" + memnum + '\'' +
                ", category='" + category + '\'' +
                ", title='" + title + '\'' +
                ", txt='" + txt + '\'' +
                '}';
    }
}
