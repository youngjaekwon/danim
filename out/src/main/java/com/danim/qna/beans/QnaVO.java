package com.danim.qna.beans;

import com.danim.comments.beans.Comments;
import com.danim.files.beans.FilesEntity;

import java.util.List;

public class QnaVO {
    private String qnanum; // 1 : 1 문의 번호
    private String ordernum; // 문의 대상 주문 번호
    private String memnum; // 문의한 유저 번호
    private String category; // 문의종류
    private String title; // 문의 제목
    private String txt; // 문의 내용
    private String qdate; // 문의 일자 (yyyy-HH-dd 형식)
    private List<FilesEntity> pics; // 문의에 첨부된 사진들
    private List<Comments> comments; // 문의에 추가된 댓글들
    private int commentsNum; // 문의에 추가된 댓글 갯수
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

    public String getQdate() {
        return qdate;
    }

    public void setQdate(String qdate) {
        this.qdate = qdate;
    }

    public List<FilesEntity> getPics() {
        return pics;
    }

    public void setPics(List<FilesEntity> pics) {
        this.pics = pics;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getCommentsNum() {
        return commentsNum;
    }

    public void setCommentsNum(int commentsNum) {
        this.commentsNum = commentsNum;
    }

    @Override
    public String toString() {
        return "QnaVO{" +
                "qnanum='" + qnanum + '\'' +
                ", ordernum='" + ordernum + '\'' +
                ", memnum='" + memnum + '\'' +
                ", category='" + category + '\'' +
                ", title='" + title + '\'' +
                ", txt='" + txt + '\'' +
                ", qdate='" + qdate + '\'' +
                ", pics=" + pics +
                ", comments=" + comments +
                ", commentsNum=" + commentsNum +
                ", state='" + state + '\'' +
                '}';
    }
}
