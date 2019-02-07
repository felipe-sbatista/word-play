package com.palavras.unicap.palavrinhas.Viewmodel;

import android.app.Application;

import com.palavras.unicap.palavrinhas.Entity.Palavra;
import com.palavras.unicap.palavrinhas.Repository.PalavraRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class JogoViewModel extends AndroidViewModel {
    private PalavraRepository repository;

    private LiveData<List<Palavra>> palavras;

    public JogoViewModel(@NonNull Application application) {
        super(application);
        repository = new PalavraRepository(application);
        palavras = repository.getPalavras();
    }

    public LiveData<List<Palavra>> getPalavras() {
        return palavras;
    }
}
