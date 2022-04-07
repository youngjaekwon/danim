package com.danim.comments.beans;

public class CommentsVO {
    String cnum; // 댓글 번호
    String memnum; // 댓글 작성자 회원번호
    String name; // 댓글 작성자 이름
    String nickName; // 댓글 작성자 닉네임
    String txt; // 댓글 내용
    String date; // 댓글 작성일자 (ex: 5분전)

    @Override
    public String toString() {
        return "CommentsVO{" +
                "cnum='" + cnum + '\'' +
                ", memnum='" + memnum + '\'' +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                ", txt='" + txt + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public String getMemnum() {
        return memnum;
    }

    public void setMemnum(String memnum) {
        this.memnum = memnum;
    }

    public String getCnum() {
        return cnum;
    }

    public void setCnum(String cnum) {
        this.cnum = cnum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
