package com.palavras.unicap.palavrinhas.Persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.palavras.unicap.palavrinhas.Entity.Palavra;

import java.util.List;

@Dao
public interface PalavraDAO {

    @Query("SELECT * FROM Palavra")
     List<Palavra> loadAllPalavras();
}
