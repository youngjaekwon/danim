package com.danim.orders.beans;

import java.util.Date;

public class Orders {
    private String ordernum; // 주문번호
    private String memnum; // 주문한 유저
    private Date orderdate; // 주문 일자
    private String name; // 이름
    private String zipcode; // 우편번호
    private String addr; // 주소
    private String mobile; // 전화번호
    private String state; // 주문 상태
    /*
    * 주문 상태
    * 10X: 진행중인 주문
    * 20X: 종료된 주문
    *
    * 101: 결제 확인
    * 102: 상품 준비중
    * 103: 배송중
    * 201: 배송완료
    * 202: 취소
    * */
    private String waybillnum; // 운송장번호
    private String price; // 결제 금액
    private String payment; // 결제 수단
    private String request; // 요청 사항
    private String itemlist; // 아이템 리스트
    private String qna; // 1 : 1 문의 여부 00: 없음, 01: 미답변, 02: 답변완료

    public String getQna() {
        return qna;
    }

    public void setQna(String qna) {
        this.qna = qna;
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

    public Date getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Date orderdate) {
        this.orderdate = orderdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getWaybillnum() {
        return waybillnum;
    }

    public void setWaybillnum(String waybillnum) {
        this.waybillnum = waybillnum;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getItemlist() {
        return itemlist;
    }

    public void setItemlist(String itemlist) {
        this.itemlist = itemlist;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "ordernum=" + ordernum +
                ", memnum='" + memnum + '\'' +
                ", orderdate=" + orderdate +
                ", name='" + name + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", addr='" + addr + '\'' +
                ", mobile='" + mobile + '\'' +
                ", state='" + state + '\'' +
                ", waybillnum='" + waybillnum + '\'' +
                ", price='" + price + '\'' +
                ", payment='" + payment + '\'' +
                ", request='" + request + '\'' +
                ", itemlist='" + itemlist + '\'' +
                ", qna='" + qna + '\'' +
                '}';
    }
}
