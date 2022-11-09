package com.example.firestore;

public class User {
    private String name;
    private String fullname;

    // Crucial.
    public User() {
    }

    public User(String name, String fullname) {
        this.name = name;
        this.fullname = fullname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
