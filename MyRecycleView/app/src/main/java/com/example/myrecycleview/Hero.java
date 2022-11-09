package com.example.myrecycleview;

public class Hero {
    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getDetail() {
        return detail;
    }

    void setDetail(String detail) {
        this.detail = detail;
    }

    int getPhoto() {
        return photo;
    }

    void setPhoto(int photo) {
        this.photo = photo;
    }

    private String name;
    private String detail;
    private int photo;
}
