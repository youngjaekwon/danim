package com.danim.comments.beans;

public class CommentsDTO {
    String cnum, memnum, boardnum, qnanum, txt, role;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "CommentsDTO{" +
                "cnum='" + cnum + '\'' +
                ", memnum='" + memnum + '\'' +
                ", boardnum='" + boardnum + '\'' +
                ", qnanum='" + qnanum + '\'' +
                ", txt='" + txt + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
