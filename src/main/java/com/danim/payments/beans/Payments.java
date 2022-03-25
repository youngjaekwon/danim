package com.danim.payments.beans;

public class Payments {
    private String paymentsnum; // 결제 번호
    private String ordernum; // 주문 번호
    private String impuid; // 고유 아이디
    private String merchantuid; // 상점 거래 id
    private String paidamount; // 결제금액
    private String applynum; // 카드 승인번호
    private String state; // 결제 상태 00: 승인완료 01: 취소

    public String getMerchantuid() {
        return merchantuid;
    }

    public void setMerchantuid(String merchantuid) {
        this.merchantuid = merchantuid;
    }

    public String getPaymentsnum() {
        return paymentsnum;
    }

    public void setPaymentsnum(String paymentsnum) {
        this.paymentsnum = paymentsnum;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public String getImpuid() {
        return impuid;
    }

    public void setImpuid(String impuid) {
        this.impuid = impuid;
    }

    public String getPaidamount() {
        return paidamount;
    }

    public void setPaidamount(String paidamount) {
        this.paidamount = paidamount;
    }

    public String getApplynum() {
        return applynum;
    }

    public void setApplynum(String applynum) {
        this.applynum = applynum;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Payments{" +
                "paymentsnum='" + paymentsnum + '\'' +
                ", ordernum='" + ordernum + '\'' +
                ", impuid='" + impuid + '\'' +
                ", merchantuid='" + merchantuid + '\'' +
                ", paidamount='" + paidamount + '\'' +
                ", applynum='" + applynum + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
