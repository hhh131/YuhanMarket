package com.example.yuhanmarket.PostPack;

public class PostVO {

    String userId;
    String title;
    String price;
    String content;
    Boolean salesStatus;
    String uploadDate;

    public PostVO(String userId, String title, String price, String content,Boolean salesStatus,String uploadDate) {

        this.userId = userId;
        this.title = title;
        this.price = price;
        this.content = content;
        this.salesStatus = salesStatus;
        this.uploadDate = uploadDate;
    }


    public PostVO() {

    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getSalesStatus() {
        return salesStatus;
    }

    public void setSalesStatus(Boolean salesStatus) {
        this.salesStatus = salesStatus;
    }
}
