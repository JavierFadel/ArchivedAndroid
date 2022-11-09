package com.example.nodo.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.nodo.model.NoDo;

@Database(entities = {NoDo.class}, version = 1)
public abstract class NoDoRoomDatabase extends RoomDatabase {

    private static volatile NoDoRoomDatabase INSTANCE;
    public abstract NoDoDao noDoDao();

    static NoDoRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NoDoRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NoDoRoomDatabase.class, "nodo_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
