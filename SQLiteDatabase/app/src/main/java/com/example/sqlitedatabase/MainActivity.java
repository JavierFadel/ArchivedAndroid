package com.example.sqlitedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.sqlitedatabase.data.DatabaseHandler;
import com.example.sqlitedatabase.model.Contact;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler db = new DatabaseHandler(MainActivity.this);

        Contact peter = new Contact();
        peter.setName("Peter");
        peter.setPhoneNUmber("2304981");

        db.addContact(peter);

//        List<Contact> contactList = db.getAllContact();
//        for (Contact contact: contactList) {
//            Log.d("MainActivity", "onCreate: " + contact.getName());
//        }
    }
}
