package org.manuel.c196.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.manuel.c196.entities.NoteEntity;
import org.manuel.c196.generics.GenericDao;

import java.util.List;

@Dao
public interface NoteDao extends GenericDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NoteEntity noteEntity);

    @Update
    void update(NoteEntity noteEntity);

    @Delete
    void delete(NoteEntity noteEntity);

    @Query("DELETE FROM notes")
    void deleteAllNotes();

    @Query("SELECT * FROM notes WHERE courseID= :courseID ORDER BY id ASC")
    LiveData<List<NoteEntity>> getCourseNotes(int courseID);


}
