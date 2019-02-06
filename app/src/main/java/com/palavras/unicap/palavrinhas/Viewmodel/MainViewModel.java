package com.palavras.unicap.palavrinhas.Viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModel;
import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;

import com.palavras.unicap.palavrinhas.Persistence.AppDatabase;
import com.palavras.unicap.palavrinhas.Persistence.DatabaseCopier;

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
