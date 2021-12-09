package org.manuel.c196.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import org.manuel.c196.dao.AssessmentDao;
import org.manuel.c196.dao.CourseDao;
import org.manuel.c196.dao.NoteDao;
import org.manuel.c196.dao.TermDao;
import org.manuel.c196.entities.AssessmentEntity;
import org.manuel.c196.entities.CourseEntity;
import org.manuel.c196.entities.NoteEntity;
import org.manuel.c196.entities.TermEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class C196Repository {
    private final TermDao termDao;
    private LiveData<List<TermEntity>> mAllTerms;

    private final CourseDao courseDao;
    private List<CourseEntity> mTermCourses;

    private final NoteDao noteDao;
    private LiveData<List<NoteEntity>> mCourseNotes;

    private final AssessmentDao assessmentDao;
    private LiveData<List<AssessmentEntity>> mCourseAssessments;

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newSingleThreadExecutor();


    public C196Repository(Application application) {
        C196Database database = C196Database.getInstance(application);
        termDao = database.termDao();
        mAllTerms = termDao.getAllTerms();

        courseDao = database.courseDao();
        assessmentDao = database.assessmentDao();
        noteDao = database.noteDao();
    }

    public void insert(TermEntity termEntity) {
        databaseWriteExecutor.submit(() -> {
            courseDao.insert(termEntity);
        });
    }
    public void insert(CourseEntity courseEntity) {
        databaseWriteExecutor.submit(() -> {
            courseDao.insert(courseEntity);
        });
    }
    public void insert(NoteEntity noteEntity) {
        databaseWriteExecutor.submit(() -> {
            courseDao.insert(noteEntity);
        });
    }
    public void insert(AssessmentEntity assessmentEntity) {
        databaseWriteExecutor.submit(() -> {
            courseDao.insert(assessmentEntity);
        });
    }

    public void update(TermEntity termEntity) {
        databaseWriteExecutor.submit(() -> {
            termDao.update(termEntity);
        });
    }
    public void update(CourseEntity courseEntity) {
        databaseWriteExecutor.submit(() -> {
            courseDao.update(courseEntity);
        });
    }
    public void update(NoteEntity noteEntity) {
        databaseWriteExecutor.submit(() -> {
            noteDao.update(noteEntity);
        });
    }
    public void update(AssessmentEntity assessmentEntity) {
        databaseWriteExecutor.submit(() -> {
            assessmentDao.update(assessmentEntity);
        });
    }

    public void delete(TermEntity termEntity) {
        databaseWriteExecutor.submit(() -> {
            termDao.delete(termEntity);
        });
    }
    public void delete(CourseEntity courseEntity) {
        databaseWriteExecutor.submit(() -> {
            courseDao.delete(courseEntity);
        });
    }
    public void delete(NoteEntity noteEntity) {
        databaseWriteExecutor.execute(() -> {
            noteDao.delete(noteEntity);
        });
    }
    public void delete(AssessmentEntity assessmentEntity) {
        databaseWriteExecutor.submit(() -> {
            assessmentDao.delete(assessmentEntity);
        });
    }

    public void deleteAll() {
        databaseWriteExecutor.submit(noteDao::deleteAllNotes);
        databaseWriteExecutor.submit(assessmentDao::deleteAllAssessments);
        databaseWriteExecutor.submit(courseDao::deleteAllCourses);
        databaseWriteExecutor.submit(termDao::deleteAllTerms);
    }


    public LiveData<List<TermEntity>> getAllTerms() {
        return mAllTerms;
    }

    public LiveData<List<CourseEntity>> getLiveTermCourses(int termID) {
        return courseDao.getLiveTermCourses(termID);
    }

    public LiveData<List<AssessmentEntity>> getCourseAssessments(int courseID) {
        return assessmentDao.getCourseAssessments(courseID);
    }
    public LiveData<List<NoteEntity>> getCourseNotes(int courseID) {
        return noteDao.getCourseNotes(courseID);
    }
    public LiveData<List<NoteEntity>> getCourseNotes2(int courseID) {
        databaseWriteExecutor.submit(() -> {
            mCourseNotes = noteDao.getCourseNotes(courseID);
        });
        return mCourseNotes;
    }



    public List<CourseEntity> getTermCourses(int termID) throws ExecutionException, InterruptedException {
        databaseWriteExecutor.execute(() -> {
            mTermCourses = courseDao.getTermCourses(termID);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mTermCourses;
    }

}
