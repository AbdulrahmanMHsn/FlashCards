package com.amhsn.flashcards.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.amhsn.flashcards.database.converter.DateConverter;
import com.amhsn.flashcards.database.dao.CardDao;
import com.amhsn.flashcards.database.dao.SubjectDao;
import com.amhsn.flashcards.database.entity.CardEntity;
import com.amhsn.flashcards.database.entity.SubjectEntity;


@Database(entities = {SubjectEntity.class, CardEntity.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "3";
    private static AppDatabase mInstance;

    public static AppDatabase getInstance(Context context) {
        if (mInstance == null) {
            synchronized (LOCK) {
                Log.d(TAG, "getInstance: Creating a new database instance");
                mInstance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase.class,
                        AppDatabase.DATABASE_NAME)
                        .addCallback(sRoomDatabaseCallback)
                        .build();
            }
        }
        Log.d(TAG, "getInstance: Getting the database instance, no need to recreated it.");
        return mInstance;
    }

    public abstract SubjectDao subjectDao();

    public abstract CardDao cardDao();

    /**
     * Populate Database Section
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsync(mInstance).execute();
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final SubjectDao mSubjectDao;
        private final CardDao mCardDao;

        PopulateDbAsync(AppDatabase db) {
            mSubjectDao = db.subjectDao();
            mCardDao = db.cardDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            SubjectEntity subjectEntity01 = new SubjectEntity("Math");
            mSubjectDao.insertSubject(subjectEntity01);
            return null;
        }
    }
}
