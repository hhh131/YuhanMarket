package com.example.yuhanmarket;

public class WriteVO {

    String UserId;
    String Title;
    String Price;
    String Content;


    public WriteVO(String userId, String title, String price, String content) {

        UserId = userId;
        Title = title;
        Price = price;
        Content = content;
    }




    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
