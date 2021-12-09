package org.manuel.c196.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.manuel.c196.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddEditAssessmentActivity extends AppCompatActivity {
    public static final String EXTRA_ASSESSMENT_ID =
            "org.manuel.c196.activities.COURSE_ASSESSMENT_ID";
    public static final String EXTRA_COURSE_ID =
            "org.manuel.c196.activities.COURSE__ID";
    public static final String EXTRA_COURSE_ASSESSMENT_TITLE =
            "org.manuel.c196.activities.COURSE_ASSESSMENT_TITLE";
    public static final String EXTRA_COURSE_ASSESSMENT_GOAL_DATE =
            "org.manuel.c196.activities.COURSE_ASSESSMENT_GOAL_DATE";
    public static final String EXTRA_COURSE_ASSESSMENT_ALERT =
            "org.manuel.c196.activities.COURSE_ASSESSMENT_ALERT";
    public static final String EXTRA_ASSESSMENT_TYPE =
            "org.manuel.c196.activities.ASSESSMENT_TYPE";

    public static final int EDIT_ASSESSMENT_REQUEST = 2;

    public static final String DATE_FORMAT = "MM/dd/yyyy";

    private Calendar calendarGoalDate;

    private int assessmentID;
    private EditText editTextTitle;
    private RadioGroup editRadioGroupType;
    private EditText editTextGoalDate;
    private CheckBox editCheckboxAlarmEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_assessment);

        editTextTitle = findViewById(R.id.edit_assessment_name);
        editRadioGroupType = findViewById(R.id.edit_assessment_radio_type);
        editTextGoalDate = findViewById(R.id.edit_assessment_goal_date);
        editCheckboxAlarmEnabled = findViewById(R.id.edit_assessment_alarm);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);

        this.calendarGoalDate = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetGoal = (view, year, month, dayOfMonth) -> {
            calendarGoalDate.set(Calendar.YEAR, year);
            calendarGoalDate.set(Calendar.MONTH, month);
            calendarGoalDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };
        editTextGoalDate.setOnClickListener(v -> new DatePickerDialog(AddEditAssessmentActivity.this,
            dateSetGoal,
            calendarGoalDate.get(Calendar.YEAR),
            calendarGoalDate.get(Calendar.MONTH),
            calendarGoalDate.get(Calendar.DAY_OF_MONTH)).show());

        Intent parentIntent = getIntent();
        if(parentIntent.hasExtra(EXTRA_ASSESSMENT_ID)) {
            setTitle("Edit Assessment");
            editTextTitle.setText(parentIntent.getStringExtra(EXTRA_COURSE_ASSESSMENT_TITLE));
            editTextGoalDate.setText(parentIntent.getStringExtra(EXTRA_COURSE_ASSESSMENT_GOAL_DATE));
            editRadioGroupType.check(getTypeBtnID(parentIntent.getIntExtra(EXTRA_ASSESSMENT_TYPE, -1)));
            if(parentIntent.getBooleanExtra(EXTRA_COURSE_ASSESSMENT_ALERT, false))
                editCheckboxAlarmEnabled.performClick();
        } else {
            setTitle("Add Assessment");
        }

    }

    private void saveAssessment() {
        String assessmentTitle = editTextTitle.getText().toString();
        String assessmentGoalDate = editTextGoalDate.getText().toString();
        boolean alarmEnabled = editCheckboxAlarmEnabled.isChecked();

        if(assessmentTitle.trim().isEmpty()
            || assessmentGoalDate.trim().isEmpty()
            || getTypeBtnID(editRadioGroupType.getCheckedRadioButtonId()) != -1) {
            Toast.makeText(this, "Assessment not saved. Empty fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_COURSE_ASSESSMENT_TITLE, assessmentTitle);
        data.putExtra(EXTRA_ASSESSMENT_TYPE, getRadioType(editRadioGroupType.getCheckedRadioButtonId()));
        data.putExtra(EXTRA_COURSE_ASSESSMENT_GOAL_DATE, assessmentGoalDate);
        data.putExtra(EXTRA_COURSE_ASSESSMENT_ALERT, alarmEnabled);
        assessmentID = getIntent().getIntExtra(EXTRA_ASSESSMENT_ID, -1);
        if(assessmentID != -1)
            data.putExtra(EXTRA_ASSESSMENT_ID, assessmentID);

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
                saveAssessment();
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

    private int getTypeBtnID(int id) {
        int btn_id;
        switch (id) {
            case AssessmentActivity.TYPE_PA:
                btn_id = R.id.radio_assessment_type_pa;
                break;
            case AssessmentActivity.TYPE_OA:
                btn_id = R.id.radio_assessment_type_oa;
                break;
            default:
                btn_id = -1;
        }
        return btn_id;
    }

    private int getRadioType(int btnID) {
        int typeID;
        switch (btnID) {
            case R.id.radio_assessment_type_pa:
                typeID = AssessmentActivity.TYPE_PA;
                break;
            case R.id.radio_assessment_type_oa:
                typeID = AssessmentActivity.TYPE_OA;
                break;
            default:
                typeID = -1;
        }
        return typeID;
    }

    private void updateLabel() {
        String myFormat = DATE_FORMAT; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editTextGoalDate.setText(sdf.format(calendarGoalDate.getTime()));
    }

}
