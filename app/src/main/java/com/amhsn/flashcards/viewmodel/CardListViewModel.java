package com.amhsn.flashcards.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;

import com.amhsn.flashcards.database.entity.CardEntity;
import com.amhsn.flashcards.repository.CardRepository;


public class CardListViewModel extends AndroidViewModel {

    private CardRepository mRepository;
    private LiveData<PagedList<CardEntity>> mCardsByParentId;

    private CardListViewModel(@NonNull Application application, final int cardParentId) {
        super(application);
        mRepository = CardRepository.getInstance(application);
        mCardsByParentId = mRepository.getAllCardsById(cardParentId);
    }

    public LiveData<PagedList<CardEntity>> getCardsByParentId() {
        return mCardsByParentId;
    }

    public void insert(CardEntity cardEntity) {
        mRepository.insert(cardEntity);
    }

    public void update(CardEntity cardEntity) {
        mRepository.update(cardEntity);
    }

    public void delete(CardEntity cardEntity) {
        mRepository.delete(cardEntity);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }


    /**
     * Factory to pass parameters to the ViewModel (inject dependencies into ViewModels).
     * DON NOT PASS CONTEXT, CALLBACKS, VIEWS!!!
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;
        private final int mCardParentId;

        public Factory(@NonNull Application application, int cardParentId) {
            mApplication = application;
            mCardParentId = cardParentId;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new CardListViewModel(mApplication, mCardParentId);
        }
    }
}