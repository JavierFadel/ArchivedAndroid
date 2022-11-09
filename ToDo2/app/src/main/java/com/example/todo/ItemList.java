package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.todo.adapter.RecyclerAdapter;
import com.example.todo.data.DatabaseHandler;
import com.example.todo.model.Note;

import java.util.ArrayList;
import java.util.List;

public class ItemList extends AppCompatActivity {
    private Context context;
    private RecyclerView recyclerView;
    private ArrayAdapter<Note> arrayAdapter;
    private ArrayList<String> arrayList;
//    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        // Access database
        DatabaseHandler db = new DatabaseHandler(this);
        List<Note> noteList = db.getAllNotes();

        // Setting up layoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(context.getApplicationContext()));
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);

//        arrayAdapter = new ArrayAdapter<>(this,
//                R.layout.item_list_recycler,
//                noteList);
//        recyclerView.setAdapter(arrayAdapter);
    }
}
