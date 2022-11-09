package com.example.todo.model;

public class Note {
    private int id;
    private String item;
    private int quantity;
    private String note;

    public Note() {
    }

    public Note(String item, int quantity, String note) {
        this.item = item;
        this.quantity = quantity;
        this.note = note;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
