package org.manuel.c196.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.manuel.c196.activities.CourseActivity;
import org.manuel.c196.dao.AssessmentDao;
import org.manuel.c196.dao.CourseDao;
import org.manuel.c196.dao.NoteDao;
import org.manuel.c196.dao.TermDao;
import org.manuel.c196.entities.AssessmentEntity;
import org.manuel.c196.entities.CourseEntity;
import org.manuel.c196.entities.NoteEntity;
import org.manuel.c196.entities.TermEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(version = 1,
        entities = {
                TermEntity.class,
                CourseEntity.class,
                NoteEntity.class,
                AssessmentEntity.class
        })
public abstract class C196Database extends RoomDatabase {
    private static volatile C196Database INSTANCE;

    public abstract TermDao termDao();

    public abstract CourseDao courseDao();

    public abstract NoteDao noteDao();

    public abstract AssessmentDao assessmentDao();

    public static synchronized C196Database getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    C196Database.class, "C196_database")
                    .fallbackToDestructiveMigration()
//                    .addCallback(roomCallback)
                    .build();
            databaseWriteExecutor.execute(() -> {
                INSTANCE.populateInitialData();
            });
        }
        return INSTANCE;
    }
    static final ExecutorService databaseWriteExecutor =
            Executors.newSingleThreadExecutor();
    /**
     * Inserts the dummy data into the database if it is currently empty.
     */
    private void populateInitialData() {
        if (termDao().count() == 0) {
            runInTransaction(new Runnable() {
                @Override
                public void run() {
                    // INSERT TERMS
                    termDao().insert(new TermEntity("Term 1", "01/01/2020", "06/06/2020"));
                    termDao().insert(new TermEntity("Term 2", "01/01/2021", "06/06/2022"));
                    termDao().insert(new TermEntity("Term 3", "01/01/2022", "06/06/2023"));
                    // INSERT COURSES
                    courseDao().insert(new CourseEntity(1, "Course 1", "01/01/2020", "06/05/2020",
                            true, CourseActivity.STATUS_IN_PROGRESS, "Bob", "5555555555", "bob@null.com"));
                    courseDao().insert(new CourseEntity(1, "Course 2", "01/01/2020", "06/05/2020",
                            false, CourseActivity.STATUS_COMPLETED, "Bob", "5555555555", "bob@null.com"));
                    courseDao().insert(new CourseEntity(1, "Course 3", "01/01/2020", "06/05/2020",
                            false, CourseActivity.STATUS_IN_PROGRESS, "Bob", "5555555555", "bob@null.com"));
                    // INSERT ASSESSMENTS
                    assessmentDao().insert(new AssessmentEntity(1, "test1", 0, "03/03/2020", true));
                    assessmentDao().insert(new AssessmentEntity(2, "test1", 0, "03/03/2020", true));
                    assessmentDao().insert(new AssessmentEntity(3, "test1", 1, "03/03/2020", true));
                    // INSERT NOTES
                    noteDao().insert(new NoteEntity(1, "Note for course 1", "Content of my note"));
                    noteDao().insert(new NoteEntity(2, "Note for course 2", "Content of my note"));
                    noteDao().insert(new NoteEntity(3, "Note for course 3", "Content of my note"));
                    noteDao().insert(new NoteEntity(3, "Another note for course 3", "Content of my note"));
                }
            });
        }
    }

//    /**
//     * Inserts the dummy data into the database if it is currently empty.
//     */
//    private void populateInitialData() {
//        if (termDao().count() == 0) {
//            runInTransaction(new Runnable() {
//                @Override
//                public void run() {
//                    TermEntity term1 = new TermEntity("Term 1", "01/01/2020", "06/06/2020");
//                    TermEntity term2 = new TermEntity("Term 2", "01/01/2021", "06/06/2022");
//                    TermEntity term3 = new TermEntity("Term 3", "01/01/2022", "06/06/2023");
//                    termDao().insert(term1);
//                    termDao().insert(term2);
//                    termDao().insert(term3);
//
//                    CourseEntity course1 = new CourseEntity(1, "Course 1", "01/01/2020", "06/05/2020",
//                            true, CourseActivity.STATUS_IN_PROGRESS, "Bob", "5555555555", "bob@null.com");
//                    CourseEntity course2 = new CourseEntity(1, "Course 2", "01/01/2020", "06/05/2020",
//                            false, CourseActivity.STATUS_COMPLETED, "Bob", "5555555555", "bob@null.com");
//                    CourseEntity course3 =new CourseEntity(1, "Course 3", "01/01/2020", "06/05/2020",
//                            false, CourseActivity.STATUS_IN_PROGRESS, "Bob", "5555555555", "bob@null.com");
//                    courseDao().insert(course1);
//                    courseDao().insert(course2);
//                    courseDao().insert(course3);
//
//                    AssessmentEntity assessment1 = new AssessmentEntity(1, "test1", 0, "03/03/2020", true);
//                    AssessmentEntity assessment2 = new AssessmentEntity(2, "test1", 0, "03/03/2020", true);
//                    AssessmentEntity assessment3 = new AssessmentEntity(3, "test1", 1, "03/03/2020", true);
//                    assessmentDao().insert(assessment1);
//                    assessmentDao().insert(assessment2);
//                    assessmentDao().insert(assessment3);
//
//                    NoteEntity note1 = new NoteEntity(1, "Note for course 1", "Content of my note");
//                    NoteEntity note2 = new NoteEntity(2, "Note for course 2", "Content of my note");
//                    NoteEntity note3 = new NoteEntity(3, "Note for course 3", "Content of my note");
//                    NoteEntity note4 = new NoteEntity(3, "Another note for course 3", "Content of my note");
//                    noteDao().insert(note1);
//                    noteDao().insert(note2);
//                    noteDao().insert(note3);
//                    noteDao().insert(note4);
//                }
//            });
//        }
//    }


//    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//            new PopulateDbAsyncTask(instance).execute();
//        }
//    };
//
//    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
//        private TermDao termDao;
//        private CourseDao courseDao;
//        private AssessmentDao assessmentDao;
//        private NoteDao noteDao;
//
//        public PopulateDbAsyncTask(C196Database db) {
//            termDao = db.termDao();
//            courseDao = db.courseDao();
//            assessmentDao = db.assessmentDao();
//            noteDao = db.noteDao();
//
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            termDao.insert(new TermEntity("Term 1", "01/01/2020", "06/06/2020"));
//            termDao.insert(new TermEntity("Term 2", "01/01/2021", "06/06/2022"));
//            termDao.insert(new TermEntity("Term 3", "01/01/2022", "06/06/2023"));
//
//            courseDao.insert(new CourseEntity(1, "Course 1", "01/01/2020", "06/05/2020",
//                    true, CourseActivity.STATUS_IN_PROGRESS, "Bob", "5555555555", "bob@null.com"));
//            courseDao.insert(new CourseEntity(1, "Course 2", "01/01/2020", "06/05/2020",
//                    false, CourseActivity.STATUS_COMPLETED, "Bob", "5555555555", "bob@null.com"));
//            courseDao.insert(new CourseEntity(2, "Course 3", "01/01/2020", "06/05/2020",
//                    false, CourseActivity.STATUS_PLANNED, "Bob", "5555555555", "bob@null.com"));
//
//            assessmentDao.insert(new AssessmentEntity(1, "test1", 0, "03/03/2020", true));
//            assessmentDao.insert(new AssessmentEntity(2, "test1", 0, "03/03/2020", true));
//            assessmentDao.insert(new AssessmentEntity(3, "test1", 1, "03/03/2020", true));
//
//            noteDao.insert(new NoteEntity(1, "Note for course 1", "Content of my note"));
//            noteDao.insert(new NoteEntity(2, "Note for course 2", "Content of my note"));
//            noteDao.insert(new NoteEntity(3, "Note for course 3", "Content of my note"));
//            noteDao.insert(new NoteEntity(3, "Notes for course 3", "Content of my note"));
//            return null;
//        }
//    }
}
