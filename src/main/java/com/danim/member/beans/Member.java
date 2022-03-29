package com.danim.member.beans;

// 멤버 ENTITY
public class Member {
    private String memnum; // 회원 번호
    private String email; // 이메일 (아이디)
    private String pwd; // 비밀번호
    private String name; // 이름
    private String nickname; // 닉네임
    private String zipcode; // 우편번호
    private String addr; // 주소
    private String mobile; // 전화번호
    private String basket; // 장바구니
    private String wishlist; // 위시리스트
    private String role; // 권한

    public String getBasket() {
        return basket;
    }

    public void setBasket(String basket) {
        this.basket = basket;
    }

    public String getWishlist() {
        return wishlist;
    }

    public void setWishlist(String wishlist) {
        this.wishlist = wishlist;
    }

    public String getMemnum() {
        return memnum;
    }

    public void setMemnum(String memnum) {
        this.memnum = memnum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memnum='" + memnum + '\'' +
                ", email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", addr='" + addr + '\'' +
                ", mobile='" + mobile + '\'' +
                ", basket='" + basket + '\'' +
                ", wishlist='" + wishlist + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
