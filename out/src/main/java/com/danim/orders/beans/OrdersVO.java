package com.danim.orders.beans;

import com.danim.items.beans.ItemsDTO;

import java.util.List;

public class OrdersVO {
    private String orderNum; // 주문 번호
    private String name; // 주문자 이름
    private String titleItem; // 대표 상품명
    private int others; // 대표 상품 외 상품 갯수 (ex. 스메나 SMENA 35 외 3개)
    private String thumbnail; // 썸네일 (대표상품의 대표이미지)
    private String price; // 포멧 처리된 가격
    private String shortDate; // 주문 일자 (yyyy-HH-dd 형식)
    private String payment; // 결제 방법
    private String state; // 주문 상태
    private String qna; // 1:1 문의 상태
    private String qnanum; // 1:1 문의 번호
    private String date; // 주문 일자 (yyyy-HH-dd hh.mm.ss 형식)
    private String zipcode; // 우편 번호
    private String addr; // 주소
    private String mobile; // 전화번호
    private String waybillNum; // 운송장 번호
    private String request; // 요청 사항
    private List<ItemsDTO> itemsList; // 상품 리스트

    public String getQnanum() {
        return qnanum;
    }

    public void setQnanum(String qnanum) {
        this.qnanum = qnanum;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitleItem() {
        return titleItem;
    }

    public void setTitleItem(String titleItem) {
        this.titleItem = titleItem;
    }

    public int getOthers() {
        return others;
    }

    public void setOthers(int others) {
        this.others = others;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getShortDate() {
        return shortDate;
    }

    public void setShortDate(String shortDate) {
        this.shortDate = shortDate;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getQna() {
        return qna;
    }

    public void setQna(String qna) {
        this.qna = qna;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getWaybillNum() {
        return waybillNum;
    }

    public void setWaybillNum(String waybillNum) {
        this.waybillNum = waybillNum;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public List<ItemsDTO> getItemsList() {
        return itemsList;
    }

    public void setItemsList(List<ItemsDTO> itemsList) {
        this.itemsList = itemsList;
    }

    @Override
    public String toString() {
        return "OrdersVO{" +
                "orderNum='" + orderNum + '\'' +
                ", name='" + name + '\'' +
                ", titleItem='" + titleItem + '\'' +
                ", others=" + others +
                ", thumbnail='" + thumbnail + '\'' +
                ", price='" + price + '\'' +
                ", shortDate='" + shortDate + '\'' +
                ", payment='" + payment + '\'' +
                ", state='" + state + '\'' +
                ", qna='" + qna + '\'' +
                ", qnanum='" + qnanum + '\'' +
                ", date='" + date + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", addr='" + addr + '\'' +
                ", mobile='" + mobile + '\'' +
                ", waybillNum='" + waybillNum + '\'' +
                ", request='" + request + '\'' +
                ", itemsList=" + itemsList +
                '}';
    }
}
