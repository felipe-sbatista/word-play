package com.palavras.unicap.palavrinhas.Repository;

import android.app.Application;

import com.palavras.unicap.palavrinhas.Entity.Palavra;
import com.palavras.unicap.palavrinhas.Persistence.AppDatabase;
import com.palavras.unicap.palavrinhas.Persistence.PalavraDAO;

import java.util.List;

import androidx.lifecycle.LiveData;

public class PalavraRepository {

    private LiveData<List<Palavra>> palavras;

    private AppDatabase database;

    private PalavraDAO dao;

    public PalavraRepository(Application aplApplication) {
        database = AppDatabase.getInstance(aplApplication);
        dao = database.palavraDAO();
        palavras = dao.loadAllPalavras();
    }

    public LiveData<List<Palavra>> getPalavras() {
        return palavras;
    }
}
