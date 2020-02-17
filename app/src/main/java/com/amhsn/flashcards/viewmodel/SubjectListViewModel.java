package com.amhsn.flashcards.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.amhsn.flashcards.database.entity.SubjectEntity;
import com.amhsn.flashcards.repository.SubjectRepository;

import java.util.List;

public class SubjectListViewModel extends AndroidViewModel {

    private SubjectRepository mRepository;
    private LiveData<List<SubjectEntity>> mAllSubjects;

    public SubjectListViewModel(@NonNull Application context) {
        super(context);
        mRepository = SubjectRepository.getInstance(context);
        mAllSubjects = mRepository.getAllSubjects();
    }

    public LiveData<List<SubjectEntity>> getAllSubjects() {
        return mAllSubjects;
    }

    public void getSubjectById(int subjectId) {
        mRepository.getSubjectById(subjectId);
    }

    public void insert(SubjectEntity subjectEntity) {
        mRepository.insert(subjectEntity);
    }

    public void update(SubjectEntity subjectEntity) {
        mRepository.update(subjectEntity);
    }

    public void delete(SubjectEntity subjectEntity) {
        mRepository.delete(subjectEntity);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }
}
