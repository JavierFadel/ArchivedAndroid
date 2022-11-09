package com.example.nodo.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.nodo.model.NoDo;

import java.util.List;

// Creating Data Access Object
@Dao
public interface NoDoDao {

    // Insert
    @Insert
    void insert(NoDo nodo);

    // Read
    @Query("SELECT * FROM nodo_table ORDER BY nodo_column DESC")
    LiveData<List<NoDo>> getAll();

    // Update
    @Query("UPDATE nodo_table SET nodo_column = :text WHERE id = :id")
    int updateNoDo(int id, String text);

    // Delete
    @Query("DELETE FROM nodo_table WHERE id = :id")
    int deleteNoDo(int id);
}
