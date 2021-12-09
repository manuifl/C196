package org.manuel.c196.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.manuel.c196.entities.AssessmentEntity;
import org.manuel.c196.generics.GenericDao;

import java.util.List;

@Dao
public interface AssessmentDao extends GenericDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AssessmentEntity assessmentEntity);

    @Update
    void update(AssessmentEntity assessmentEntity);

    @Delete
    void delete(AssessmentEntity assessmentEntity);

    @Query("DELETE FROM assessments")
    void deleteAllAssessments();

    @Query("SELECT * FROM assessments WHERE courseID= :courseID ORDER BY id ASC")
    LiveData<List<AssessmentEntity>> getCourseAssessments(int courseID);
}
