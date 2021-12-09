package org.manuel.c196.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.manuel.c196.R;
import org.manuel.c196.database.C196Repository;
import org.manuel.c196.entities.AssessmentEntity;
import org.manuel.c196.entities.CourseEntity;
import org.manuel.c196.entities.NoteEntity;
import org.manuel.c196.entities.TermEntity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_list);

    }



}
