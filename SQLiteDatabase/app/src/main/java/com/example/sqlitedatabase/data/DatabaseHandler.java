package com.example.sqlitedatabase.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sqlitedatabase.model.Contact;
import com.example.sqlitedatabase.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    // Creating table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + Util.DATABASE_NAME + "(" +
                Util.KEY_ID + " INTEGER PRIMARY KEY," +
                Util.KEY_NAME + " TEXT," +
                Util.KEY_PHONE_NUMBER + " TEXT)";
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Util.SQL_DROP_ENTRIES);
        onCreate(db);
    }

    // Downgrading database
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Util.SQL_DROP_ENTRIES, new String[]{Util.DATABASE_NAME});
    }

    // Adding contact object
    public void addContact(Contact contact) {
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values
        ContentValues values = new ContentValues();
        values.put(Util.KEY_ID, contact.getId());
        values.put(Util.KEY_NAME, contact.getName());
        values.put(Util.KEY_PHONE_NUMBER, contact.getPhoneNUmber());

        // Insert new row
        db.insert(Util.TABLE_NAME, null, values);
        db.close(); // Close db connection
    }

    // Returning contact data from index
    public Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Filter results WHERE "key_id" = id
        String selection = Util.KEY_ID + " =? ";
        String[] selectionArgs = new String[]{String.valueOf(id)};

        // Get data from database
        Cursor cursor = db.query(Util.TABLE_NAME, new String[] {Util.KEY_ID, Util.KEY_NAME, Util.KEY_PHONE_NUMBER},
                selection, selectionArgs, null, null, null);

        // Setting cursor to first the first row
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Contact contact = new Contact();
        assert cursor != null;
        contact.setId(cursor.getInt(0));
        contact.setName(cursor.getString(1));
        contact.setPhoneNUmber(cursor.getString(2));
        cursor.close();

        return contact;
    }

    // Returning all contact data
    public List<Contact> getAllContact() {
        List<Contact> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNUmber(cursor.getString(2));

                contactList.add(contact);
            } while (cursor.moveToNext()); // Each iteration move cursor to the next row
        }
        cursor.close();
        return contactList;
    }
}
