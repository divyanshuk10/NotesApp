package com.example.notesapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Created by Divyanshu Kumar on 2019-09-04.
 * divyanshuk10@gmail.com
 */
@Database(entities = Note.class, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    public static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
