package com.amhsn.flashcards.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.amhsn.flashcards.database.AppDatabase;
import com.amhsn.flashcards.database.dao.SubjectDao;
import com.amhsn.flashcards.database.entity.SubjectEntity;
import com.amhsn.flashcards.utilities.AppExecutors;

import java.util.List;

public class SubjectRepository {
    private static final Object LOCK = new Object();
    private static SubjectRepository mInstance;
    private final SubjectDao subjectDao;
    private LiveData<List<SubjectEntity>> listSubjects;

    private SubjectRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        subjectDao = database.subjectDao();
        listSubjects = subjectDao.getAllSubjects();
    }

    public static SubjectRepository getInstance(Application application) {

        if (mInstance == null) {
            synchronized (LOCK) {
                mInstance = new SubjectRepository(application);
            }
        }

        return mInstance;
    }

    public LiveData<List<SubjectEntity>> getAllSubjects() {
        return listSubjects;
    }

    public LiveData<SubjectEntity> getSubjectById(final int subjectId) {
        return subjectDao.getSubjectById(subjectId);
    }

    public void insert(final SubjectEntity subjectEntity) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                subjectDao.insertSubject(subjectEntity);
            }
        });
    }

    public void update(final SubjectEntity subjectEntity) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                subjectDao.updateSubject(subjectEntity);
            }
        });
    }

    public void delete(final SubjectEntity subjectEntity) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                subjectDao.deleteSubject(subjectEntity);
            }
        });
    }

    public void deleteAll() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                subjectDao.deleteAll();
            }
        });
    }


}
