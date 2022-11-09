package com.example.todo;

import android.content.Intent;
import android.os.Bundle;

import com.example.todo.data.DatabaseHandler;
import com.example.todo.model.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private EditText itemName, quantityAmount, sideNote;
    private Button addButton;
    private ArrayList<String> noteArrayList;
    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Instantiate database object
        databaseHandler = new DatabaseHandler(MainActivity.this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopupDialog();
            }
        });

        List<Note> noteList = databaseHandler.getAllNotes();

        for (Note note : noteList) {
            Log.d("databaseHandler", "onCreate: " + note.getItem());
        }
    }

    private void createPopupDialog() {
        builder = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.popup, null);
        itemName = view.findViewById(R.id.enter_name);
        quantityAmount = view.findViewById(R.id.enter_quantity);
        sideNote = view.findViewById(R.id.enter_note);

        addButton = view.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!itemName.getText().toString().isEmpty()
                && !quantityAmount.getText().toString().isEmpty()) {
                    saveItem(v);
                } else {
                    Snackbar.make(v, "Field cannot be empty", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void saveItem(View view) {
        // Get data from user input
        String newItem = itemName.getText().toString().trim();
        int newQuantity = Integer.parseInt(quantityAmount.getText().toString().trim());
        String newNote = sideNote.getText().toString().trim();

        // Creating new object for database
        Note note = new Note();
        note.setItem(newItem);
        note.setQuantity(newQuantity);
        note.setNote(newNote);

        // Adding object to database
        databaseHandler.addNote(note);

        // Popup message
        Snackbar.make(view, "Item added", Snackbar.LENGTH_SHORT).show();

        // Close and start new activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.dismiss();
                startActivity(new Intent(MainActivity.this, ItemList.class));
            }
        }, 1200); // Delay duration in ms
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
