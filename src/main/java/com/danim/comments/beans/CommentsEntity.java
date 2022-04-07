package com.danim.comments.beans;

import java.util.Date;

public class CommentsEntity {
    String cnum, memnum, boardnum, qnanum, txt;
    Date cdate;

    public String getCnum() {
        return cnum;
    }

    public void setCnum(String cnum) {
        this.cnum = cnum;
    }

    public String getMemnum() {
        return memnum;
    }

    public void setMemnum(String memnum) {
        this.memnum = memnum;
    }

    public String getBoardnum() {
        return boardnum;
    }

    public void setBoardnum(String boardnum) {
        this.boardnum = boardnum;
    }

    public String getQnanum() {
        return qnanum;
    }

    public void setQnanum(String qnanum) {
        this.qnanum = qnanum;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }

    @Override
    public String toString() {
        return "CommentsEntity{" +
                "cnum='" + cnum + '\'' +
                ", memnum='" + memnum + '\'' +
                ", boardnum='" + boardnum + '\'' +
                ", qnanum='" + qnanum + '\'' +
                ", txt='" + txt + '\'' +
                ", cdate=" + cdate +
                '}';
    }
}
