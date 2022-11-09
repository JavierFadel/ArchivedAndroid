package com.example.todo.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.todo.model.Note;
import com.example.todo.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private final Context context;

    // Constructor
    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creating table
        String CREATE_NOTE_TABLE = "CREATE TABLE " + Util.TABLE_NAME +
                "(" + Util.KEY_ID + " INTEGER PRIMARY KEY," +
                Util.KEY_ITEM + " TEXT," +
                Util.KEY_QUANTITY + " INTEGER," +
                Util.KEY_NOTE + " TEXT)";
        db.execSQL(CREATE_NOTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Dropping table
        String DROP_TABLE = "DROP TABLE IF EXISTS " + Util.TABLE_NAME;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    // Adding Note object to database
    public void addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util.KEY_ITEM, note.getItem());
        values.put(Util.KEY_QUANTITY, note.getQuantity());
        values.put(Util.KEY_NOTE, note.getNote());

        db.insert(Util.TABLE_NAME, null, values);

        Log.d("Database", "addNote: item added!");
        db.close();
    }

    // Returning single note object
    public Note getNote(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Setting cursor
        Cursor cursor = db.query(Util.TABLE_NAME,
                new String[]{Util.KEY_ITEM, Util.KEY_QUANTITY, Util.KEY_NOTE},
                Util.KEY_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Note note = new Note();
        assert cursor != null;
        note.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Util.KEY_ID))));
        note.setItem(cursor.getString(cursor.getColumnIndex(Util.KEY_ITEM)));
        note.setQuantity(cursor.getInt(cursor.getColumnIndex(Util.KEY_QUANTITY)));
        note.setNote(cursor.getString(cursor.getColumnIndex(Util.KEY_NOTE)));

        cursor.close();
        return note;
    }

    // Returning all notes available
    public List<Note> getAllNotes() {
        List<Note> noteList =new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        // Loop through data
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(Integer.parseInt(cursor.getString(0)));
                note.setItem(cursor.getString(1));
                note.setQuantity(Integer.parseInt(cursor.getString(2)));
                note.setNote(cursor.getString(3));

                // Adding note object to list
                noteList.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return noteList;
    }

    // Updating note object
    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util.KEY_ITEM, note.getItem());
        values.put(Util.KEY_QUANTITY, note.getQuantity());
        values.put(Util.KEY_NOTE, note.getNote());

        return db.update(Util.TABLE_NAME, values, Util.KEY_ID + "=?",
                new String[]{String.valueOf(note.getId())});
    }

    // Dropping note object
    public void dropNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Util.TABLE_NAME, Util.KEY_ID + "=?",
                new String[]{String.valueOf(note.getId())});
    }

    // Return note count
    public int getCount() {
        String countQuery = "SELECT * FROM " + Util.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.rawQuery(countQuery, null);

        cursor.close();
        return cursor.getCount();
    }
}
