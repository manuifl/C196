package org.manuel.c196.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.manuel.c196.database.C196Repository;
import org.manuel.c196.entities.NoteEntity;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private C196Repository repo;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repo = new C196Repository(application);
    }

    public void insert(NoteEntity noteEntity) {
        repo.insert(noteEntity);
    }
    public void update(NoteEntity noteEntity) {
        repo.update(noteEntity);
    }
    public void delete(NoteEntity noteEntity) {
        repo.delete(noteEntity);
    }

    public LiveData<List<NoteEntity>> getCourseNotes(int courseID) {
        return repo.getCourseNotes(courseID);
    }

}
