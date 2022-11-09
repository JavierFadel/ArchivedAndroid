package com.example.nodo.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Create new table
@Entity(tableName = "nodo_table")
public class NoDo {

    // Create new Primary Key
    @PrimaryKey(autoGenerate = true)
    private int id;

    // Create new column called 'nodo_column'
    @NonNull
    @ColumnInfo(name = "nodo_column")
    private String noDo;

    public NoDo(@NonNull String noDo) {
        this.noDo = noDo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoDo() {
        return noDo;
    }

    public void setNoDo(String noDo) {
        this.noDo = noDo;
    }
}
