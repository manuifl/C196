package org.manuel.c196.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import org.manuel.c196.R;

public class AddEditNoteActivity extends AppCompatActivity {
    public static final String EXTRA_NOTE_ID =
            "org.manuel.c196.activities.COURSE_NOTE_ID";
    public static final String EXTRA_COURSE_ID =
            "org.manuel.c196.activities.COURSE_ID";
    public static final String EXTRA_COURSE_NOTE_TITLE =
            "org.manuel.c196.activities.COURSE_NOTE_TITLE";
    public static final String EXTRA_COURSE_NOTE_CONTENT =
            "org.manuel.c196.activities.COURSE_NOTE_CONTENT";

    private EditText editTextTitle;
    private EditText editTextContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);

        editTextTitle = findViewById(R.id.edit_note_name);
        editTextContent = findViewById(R.id.edit_note_content);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);

        Intent parentIntent = getIntent();
        if(parentIntent.hasExtra(EXTRA_NOTE_ID)) {
            setTitle("Edit Note");
            editTextTitle.setText(parentIntent.getStringExtra(EXTRA_COURSE_NOTE_TITLE));
            editTextContent.setText(parentIntent.getStringExtra(EXTRA_COURSE_NOTE_CONTENT));
        } else {
            setTitle("Add Note");
        }
    }

    private void saveNote() {
        String noteTitle = editTextTitle.getText().toString();
        String noteContent = editTextContent.getText().toString();

        if(noteTitle.trim().isEmpty()
            || noteContent.trim().isEmpty()) {
            Toast.makeText(this, "Note not saved. Empty fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_COURSE_NOTE_TITLE, noteTitle);
        data.putExtra(EXTRA_COURSE_NOTE_CONTENT, noteContent);
        int noteID = getIntent().getIntExtra(EXTRA_NOTE_ID, -1);
        if(noteID != -1) {
            data.putExtra(EXTRA_NOTE_ID, noteID);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_edit, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_edit_save:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
