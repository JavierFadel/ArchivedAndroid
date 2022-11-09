package com.example.sqlitedatabase.model;

public class Contact {
    private int id;
    private String name;
    private String phoneNUmber;

    public Contact() {
    }

    public Contact(int id, String name, String phoneNUmber) {
        this.id = id;
        this.name = name;
        this.phoneNUmber = phoneNUmber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNUmber() {
        return phoneNUmber;
    }

    public void setPhoneNUmber(String phoneNUmber) {
        this.phoneNUmber = phoneNUmber;
    }
}
