package org.manuel.c196.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import org.manuel.c196.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddEditTermActivity extends AppCompatActivity {
    public static final String EXTRA_TERM_ID =
            "org.manuel.c196.activities.TERM_ID";
    public static final String EXTRA_TERM_TITLE =
            "org.manuel.c196.activities.TERM_TITLE";
    public static final String EXTRA_TERM_START_DATE =
            "org.manuel.c196.activities.TERM_START_DATE";
    public static final String EXTRA_TERM_END_DATE =
            "org.manuel.c196.activities.TERM_END_DATE";

    public static final String DATE_FORMAT = "MM/dd/yyyy";

    private Calendar calendarStartDate;
    private Calendar calendarEndDate;

    private EditText editTextTitle;
    private EditText editTextStartDate;
    private EditText editTextEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_term);

        editTextTitle = findViewById(R.id.edit_term_title);
        editTextStartDate = findViewById(R.id.edit_term_start_date);
        editTextEndDate = findViewById(R.id.edit_term_end_date);


        this.calendarStartDate = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetStart = (view, year, month, dayOfMonth) -> {
            calendarStartDate.set(Calendar.YEAR, year);
            calendarStartDate.set(Calendar.MONTH, month);
            calendarStartDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(editTextStartDate, calendarStartDate);
        };
        editTextStartDate.setOnClickListener(v -> new DatePickerDialog(AddEditTermActivity.this,
                dateSetStart,
                calendarStartDate.get(Calendar.YEAR),
                calendarStartDate.get(Calendar.MONTH),
                calendarStartDate.get(Calendar.DAY_OF_MONTH)).show());

        this.calendarEndDate = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetEnd = (view, year, month, dayOfMonth) -> {
            calendarEndDate.set(Calendar.YEAR, year);
            calendarEndDate.set(Calendar.MONTH, month);
            calendarEndDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(editTextEndDate, calendarEndDate);
        };
        editTextEndDate.setOnClickListener(v -> new DatePickerDialog(AddEditTermActivity.this,
                dateSetEnd,
                calendarEndDate.get(Calendar.YEAR),
                calendarEndDate.get(Calendar.MONTH),
                calendarEndDate.get(Calendar.DAY_OF_MONTH)).show());

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_TERM_ID)) {
            setTitle("Edit Term");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TERM_TITLE));
            editTextStartDate.setText(intent.getStringExtra(EXTRA_TERM_START_DATE));
            editTextEndDate.setText(intent.getStringExtra(EXTRA_TERM_END_DATE));
        } else {
            setTitle("Add Term");
        }
    }

    private void saveTerm() {
        String termTitle = editTextTitle.getText().toString();
        String startDate = editTextStartDate.getText().toString();
        String endDate = editTextEndDate.getText().toString();

        if (termTitle.trim().isEmpty()
                || startDate.trim().isEmpty()
                || endDate.trim().isEmpty()) {
            Toast.makeText(this, "Empty fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TERM_TITLE, termTitle);
        data.putExtra(EXTRA_TERM_START_DATE, startDate);
        data.putExtra(EXTRA_TERM_END_DATE, endDate);

        int id = getIntent().getIntExtra(EXTRA_TERM_ID, -1);
        if(id != -1) {
            data.putExtra(EXTRA_TERM_ID, id);
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
                saveTerm();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
//        Go back to the correct previous activity page
        finish();
        return true;
    }

    private void updateLabel(EditText editText, Calendar calendar) {
        String myFormat = DATE_FORMAT; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editText.setText(sdf.format(calendar.getTime()));
    }
}
