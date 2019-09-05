package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Divyanshu Kumar on 2019-09-05.
 * divyanshuk10@gmail.com
 */
public class AddNoteActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "com.example.notesapp.AddNoteActivity.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.example.notesapp.AddNoteActivity.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY = "com.example.notesapp.AddNoteActivity.EXTRA_PRIORITY";

    private EditText et_title, et_description;
    private NumberPicker np_priority;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        et_description = findViewById(R.id.et_description);
        et_title = findViewById(R.id.et_title);
        np_priority = findViewById(R.id.np_priority);
        np_priority.setMinValue(1);
        np_priority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Note");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_item:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        String title = et_title.getText().toString();
        String description = et_description.getText().toString();
        int priority = np_priority.getValue();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter a title and a description", Toast.LENGTH_LONG).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_PRIORITY, priority);

        setResult(RESULT_OK, data);
        finish();

    }
}
