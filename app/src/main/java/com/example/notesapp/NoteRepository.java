package com.example.notesapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Created by Divyanshu Kumar on 2019-09-04.
 * divyanshuk10@gmail.com
 */
public class NoteRepository {
    // reference of this note dao is used by the InsertAsyncTask class
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;
    private NoteDatabase noteDatabase;

    public NoteRepository(Application application) {
        // creating a new object of NoteDatabase to use it for transaction
        NoteDatabase mNoteDatabase = NoteDatabase.getInstance(application);
        // reference of note dao coming from Note Database class
        noteDao = mNoteDatabase.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    void insert(Note note) {
        new InsertNoteAsyncTask(noteDao).execute(note);
    }

    void update(Note note) {
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }

    void delete(Note note) {
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    void deleteAll() {
        new DeleteAllNoteAsyncTask(noteDao).execute();
    }

    LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private InsertNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private UpdateNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private DeleteNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNoteAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;

        private DeleteAllNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAll();
            return null;
        }
    }

}
