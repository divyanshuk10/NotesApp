package com.example.notesapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Created by Divyanshu Kumar on 2019-09-04.
 * divyanshuk10@gmail.com
 */

/*
* This is the DAO(Data Access Object) to query data in the SQL Lite
* */
@Dao
public interface NoteDao {
    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM note_table")
    void deleteAll();

    @Query("SELECT * FROM note_table ORDER BY priority_column DESC")
    LiveData<List<Note>> getAllNotes();
}
