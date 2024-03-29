package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.example.notesapp.AddNoteActivity.EXTRA_DESCRIPTION;
import static com.example.notesapp.AddNoteActivity.EXTRA_PRIORITY;
import static com.example.notesapp.AddNoteActivity.EXTRA_TITLE;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    private NoteViewModel noteViewModel;
    private RecyclerView rv_notes;
    public static final String EXTRA_ID = "com.example.notesapp.MainActivity.EXTRA_ID";

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
        adapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                intent.putExtra(EXTRA_ID, note.getId());
                intent.putExtra(EXTRA_TITLE, note.getTitle());
                intent.putExtra(EXTRA_DESCRIPTION, note.getDescription());
                intent.putExtra(EXTRA_PRIORITY, note.getPriority());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });
        rv_notes.setAdapter(adapter);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.setNotes(notes);

            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getApplicationContext(), "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(rv_notes);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.delete_all:
                noteViewModel.deleteAll();
                Toast.makeText(getApplicationContext(), "All Deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ADD_NOTE_REQUEST: {
                if (resultCode == RESULT_OK) {
                    String title = data.getStringExtra(EXTRA_TITLE);
                    String description = data.getStringExtra(EXTRA_DESCRIPTION);
                    int priority = data.getIntExtra(EXTRA_PRIORITY, 1);

                    Note note = new Note(title, description, priority);
                    noteViewModel.insert(note);

                    Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case EDIT_NOTE_REQUEST: {
                if (resultCode == RESULT_OK) {
                    String title = data.getStringExtra(EXTRA_TITLE);
                    String description = data.getStringExtra(EXTRA_DESCRIPTION);
                    int priority = data.getIntExtra(EXTRA_PRIORITY, 1);
                    int id = data.getIntExtra(EXTRA_ID, 0);
                    Note note = new Note(title, description, priority);
                    note.setId(id);
                    noteViewModel.update(note);

                    Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Not not saved", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            default:
        }
    }
}

