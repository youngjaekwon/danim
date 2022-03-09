package com.danim.member.dto;

// 멤버 DTO
public class MemberDTO {
    private String email; // 이메일 전체
    private String email1; // 이메일 앞자리
    private String email2; // 이메일 뒷자리
    private String pwd; // 비밀번호
    private String newPassword; // 변경할 비밀번호
    private String name; // 이름
    private String nickname; // 닉네임
    private String zipcode; // 우편번호
    private String addr; // 주소
    private String addrDetail; // 주소
    private String mobile1; // 전화번호 앞자리
    private String mobile2; // 전화번호 중간자리
    private String mobile3; // 전화번호 뒷자리

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getAddrDetail() {
        return addrDetail;
    }

    public void setAddrDetail(String addrDetail) {
        this.addrDetail = addrDetail;
    }

    public String getMobile1() {
        return mobile1;
    }

    public void setMobile1(String mobile1) {
        this.mobile1 = mobile1;
    }

    public String getMobile2() {
        return mobile2;
    }

    public void setMobile2(String mobile2) {
        this.mobile2 = mobile2;
    }

    public String getMobile3() {
        return mobile3;
    }

    public void setMobile3(String mobile3) {
        this.mobile3 = mobile3;
    }

    @Override
    public String toString() {
        return "MemberDTO{" +
                "email1='" + email1 + '\'' +
                ", email2='" + email2 + '\'' +
                ", pwd='" + pwd + '\'' +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", addr='" + addr + '\'' +
                ", addrDetail='" + addrDetail + '\'' +
                ", mobile1='" + mobile1 + '\'' +
                ", mobile2='" + mobile2 + '\'' +
                ", mobile3='" + mobile3 + '\'' +
                '}';
    }
}
