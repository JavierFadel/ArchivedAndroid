package com.example.submission.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.submission.model.Product;
import com.example.submission.util.Constant;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(@Nullable Context context) {
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table.
        String CREATE_TABLE = "CREATE TABLE " + Constant.TABLE_NAME + "(" +
                Constant.KEY_ID + " INTEGER PRIMARY KEY," +
                Constant.KEY_NAME + " TEXT," +
                Constant.KEY_PRICE + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Create and drop table.
        String DROP_TABLE = "DROP TABLE IF EXISTS " + Constant.TABLE_NAME;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void addProduct(Product product) {
        // Get access to write on database.
        SQLiteDatabase db = this.getWritableDatabase();

        // Adding item to database.
        ContentValues values = new ContentValues();
        values.put(Constant.KEY_NAME, product.getProductName());
        values.put(Constant.KEY_PRICE, product.getProductPrice());

        db.insert(Constant.TABLE_NAME, null, values);
    }


}
