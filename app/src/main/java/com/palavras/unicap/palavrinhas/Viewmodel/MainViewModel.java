package com.palavras.unicap.palavrinhas.Viewmodel;

import android.app.Application;

import com.palavras.unicap.palavrinhas.Persistence.AppDatabase;
import com.palavras.unicap.palavrinhas.Persistence.DatabaseCopier;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.room.Room;

public class MainViewModel extends AndroidViewModel {
    private AppDatabase database;

    public MainViewModel(@NonNull Application application) {
        super(application);
    }


    public AppDatabase copyDatabase(){
        DatabaseCopier copier = DatabaseCopier.getInstance(getApplication());
        database = copier.getRoomDatabase();
        return database;
    }

    public AppDatabase getDatabase(){
        database = Room.databaseBuilder(getApplication(), AppDatabase.class, "palavras.db").build();
        return database;
    }
}
