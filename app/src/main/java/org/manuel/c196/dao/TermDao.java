package org.manuel.c196.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.manuel.c196.entities.TermEntity;
import org.manuel.c196.generics.GenericDao;

import java.util.List;

@Dao
public interface TermDao extends GenericDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TermEntity termEntity);

    @Update
    void update(TermEntity termEntity);

    @Delete
    void delete(TermEntity termEntity);

    @Query("DELETE FROM terms")
    void deleteAllTerms();

    @Query("SELECT * FROM terms ORDER BY id ASC")
    LiveData<List<TermEntity>> getAllTerms();

    @Query("SELECT COUNT(id) FROM terms")
    int count();
}
