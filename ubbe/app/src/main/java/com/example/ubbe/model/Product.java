package com.example.ubbe.model;

public class Product {
    private String item;
    private String version;
    private String price;
    private String desc;
    private int photo;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getPhoto() {
        return photo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
