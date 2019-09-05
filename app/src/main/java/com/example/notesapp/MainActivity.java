package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 1;
    private NoteViewModel noteViewModel;
    private RecyclerView rv_notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton bt_addNote = findViewById(R.id.bt_addNote);
        bt_addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

        rv_notes = findViewById(R.id.rv_notes);
        rv_notes.setLayoutManager(new LinearLayoutManager(this));
        rv_notes.setHasFixedSize(true);


        final NoteAdapter adapter = new NoteAdapter();
        rv_notes.setAdapter(adapter);


        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.setNotes(notes);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ADD_NOTE_REQUEST: {
                if (resultCode == RESULT_OK) {
                    String title = data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
                    String description = data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION);
                    int priority = data.getIntExtra(AddNoteActivity.EXTRA_PRIORITY, 1);

                    Note note = new Note(title, description, priority);
                    noteViewModel.insert(note);

                    Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Not not saved", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
