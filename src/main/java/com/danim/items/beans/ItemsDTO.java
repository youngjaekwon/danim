package com.danim.items.beans;

// Item DTO
public class ItemsDTO {
    private String itemnum; // item 번호
    private String category; // 카테고리
    private String mfr; // 제조사
    private String name; // 제품명
    private String info; // 제품정보
    private int price; // 가격
    private String formattedPrice; // #,###,### 포맷으로 변경된 가격
    private String pic; // 사진
    private String thumbnail; // 썸네일
    private int stock; // 재고수량
    private int quantity; // 주문수량

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getFormattedPrice() {
        return formattedPrice;
    }

    public void setFormattedPrice(String formattedPrice) {
        this.formattedPrice = formattedPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getItemnum() {
        return itemnum;
    }

    public void setItemnum(String itemnum) {
        this.itemnum = itemnum;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMfr() {
        return mfr;
    }

    public void setMfr(String mfr) {
        this.mfr = mfr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public String toString() {
        return "ItemsDTO{" +
                "itemnum='" + itemnum + '\'' +
                ", category='" + category + '\'' +
                ", mfr='" + mfr + '\'' +
                ", name='" + name + '\'' +
                ", info='" + info + '\'' +
                ", price=" + price +
                ", formattedPrice='" + formattedPrice + '\'' +
                ", pic='" + pic + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", stock='" + stock + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }
}
