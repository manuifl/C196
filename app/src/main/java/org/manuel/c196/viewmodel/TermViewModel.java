package org.manuel.c196.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.manuel.c196.database.C196Repository;
import org.manuel.c196.entities.TermEntity;

import java.util.List;

public class TermViewModel extends AndroidViewModel {
    private C196Repository repo;
    private LiveData<List<TermEntity>> allTerms;

    public TermViewModel(@NonNull Application application) {
        super(application);
        repo = new C196Repository(application);
        allTerms = repo.getAllTerms();
    }

    public void insert(TermEntity termEntity) {
        repo.insert(termEntity);
    }
    public void update(TermEntity termEntity) {
        repo.update(termEntity);
    }
    public void delete(TermEntity termEntity) {
        repo.delete(termEntity);
    }

    public LiveData<List<TermEntity>> getAllTerms() {
        return allTerms;
    }
}
