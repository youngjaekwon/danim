package com.danim.orders.beans;

import com.danim.shop.beans.ItemsDTO;

import java.util.List;

public class OrdersDTO {
    private String shippingName; // 배송 받는 사람 이름
    private String shippingZipcode; // 배송지 우편번호
    private String shippingAddr; // 배송지 주소
    private String shippingAddrDetail; // 배송지 상세 주소
    private String shippingMobile1; // 전화번호
    private String shippingMobile2; // 전화번호
    private String shippingMobile3; // 전화번호
    private String request; // 요청사항
    private String totalCost; // 전체 금액
    private String payment; // 결제 정보
    private String itemlist; // 아이템 리스트

    public String getItemlist() {
        return itemlist;
    }

    public void setItemlist(String itemlist) {
        this.itemlist = itemlist;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public String getShippingZipcode() {
        return shippingZipcode;
    }

    public void setShippingZipcode(String shippingZipcode) {
        this.shippingZipcode = shippingZipcode;
    }

    public String getShippingAddr() {
        return shippingAddr;
    }

    public void setShippingAddr(String shippingAddr) {
        this.shippingAddr = shippingAddr;
    }

    public String getShippingAddrDetail() {
        return shippingAddrDetail;
    }

    public void setShippingAddrDetail(String shippingAddrDetail) {
        this.shippingAddrDetail = shippingAddrDetail;
    }

    public String getShippingMobile1() {
        return shippingMobile1;
    }

    public void setShippingMobile1(String shippingMobile1) {
        this.shippingMobile1 = shippingMobile1;
    }

    public String getShippingMobile2() {
        return shippingMobile2;
    }

    public void setShippingMobile2(String shippingMobile2) {
        this.shippingMobile2 = shippingMobile2;
    }

    public String getShippingMobile3() {
        return shippingMobile3;
    }

    public void setShippingMobile3(String shippingMobile3) {
        this.shippingMobile3 = shippingMobile3;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "OrdersDTO{" +
                "shippingName='" + shippingName + '\'' +
                ", shippingZipcode='" + shippingZipcode + '\'' +
                ", shippingAddr='" + shippingAddr + '\'' +
                ", shippingAddrDetail='" + shippingAddrDetail + '\'' +
                ", shippingMobile1='" + shippingMobile1 + '\'' +
                ", shippingMobile2='" + shippingMobile2 + '\'' +
                ", shippingMobile3='" + shippingMobile3 + '\'' +
                ", request='" + request + '\'' +
                ", totalCost='" + totalCost + '\'' +
                ", payment='" + payment + '\'' +
                ", itemlist='" + itemlist + '\'' +
                '}';
    }
}
