package org.manuel.c196.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.manuel.c196.R;
import org.manuel.c196.database.C196Repository;
import org.manuel.c196.entities.AssessmentEntity;
import org.manuel.c196.entities.CourseEntity;
import org.manuel.c196.entities.NoteEntity;
import org.manuel.c196.entities.TermEntity;

import static org.manuel.c196.alarms.AssessmentAlarmReceiver.ASSESSMENT_CHANNEL_ID_ALARMS;
import static org.manuel.c196.alarms.CourseAlarmReceiver.COURSE_CHANNEL_ID_ALARMS;

public class NavigationPanelActivity extends AppCompatActivity {
    private Button viewTermListBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_panel);
//        C196Repository repository = new C196Repository(getApplication());
//        repository.deleteAll();
//        TermEntity term = new TermEntity("Term 1", "01/01/2020", "06/06/2020");
//        repository.insert(term);
//        term = new TermEntity("Term 2", "01/01/2021", "06/06/2022");
//        repository.insert(term);
//        term = new TermEntity("Term 3", "01/01/2022", "06/06/2023");
//        repository.insert(term);
//
//        CourseEntity course = new CourseEntity(1, "Course 1", "01/01/2020", "06/05/2020",
//                true, CourseActivity.STATUS_IN_PROGRESS, "Bob", "5555555555", "bob@null.com");
//        repository.insert(course);
//        course = new CourseEntity(1, "Course 2", "01/01/2020", "06/05/2020",
//                false, CourseActivity.STATUS_COMPLETED, "Bob", "5555555555", "bob@null.com");
//        repository.insert(course);
//        course = new CourseEntity(1, "Course 3", "01/01/2020", "06/05/2020",
//                false, CourseActivity.STATUS_COMPLETED, "Bob", "5555555555", "bob@null.com");
//        repository.insert(course);
//
//        AssessmentEntity assessment = new AssessmentEntity(1, "test1", 0, "03/03/2020", true);
//        repository.insert(assessment);
//        assessment = new AssessmentEntity(2, "test1", 0, "03/03/2020", true);
//        repository.insert(assessment);
//        assessment = new AssessmentEntity(3, "test1", 1, "03/03/2020", true);
//        repository.insert(assessment);
//
//        NoteEntity note = new NoteEntity(1, "Note for course 1", "Content of my note");
//        repository.insert(note);
//        note = new NoteEntity(2, "Note for course 2", "Content of my note");
//        repository.insert(note);
//        note = new NoteEntity(3, "Note for course 3", "Content of my note");
//        repository.insert(note);
//        note = new NoteEntity(3, "Another note for course 3", "Content of my note");
//        repository.insert(note);

//        Android 8+ requires notification channels
        createNotificationChannels();

        viewTermListBtn = findViewById(R.id.btn_view_term_list);
        viewTermListBtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), TermListActivity.class);
            v.getContext().startActivity(intent);
        });

    }


    private void createNotificationChannels() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Course Alarms";
            String description = "When the course starts and ends";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel courseNotificationChannel = new NotificationChannel(COURSE_CHANNEL_ID_ALARMS, name, importance);
            courseNotificationChannel.setDescription(description);
            name = "Assessment Alarms";
            description = "When assessments start and end";
            NotificationChannel assessmentNotificationChannel = new NotificationChannel(ASSESSMENT_CHANNEL_ID_ALARMS, name, importance);
            assessmentNotificationChannel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(courseNotificationChannel);
            notificationManager.createNotificationChannel(assessmentNotificationChannel);
        }
    }

        public void termScreen(View view) {
        Intent intent=new Intent(NavigationPanelActivity.this, TermListActivity.class);
        startActivity(intent);
    }

}
