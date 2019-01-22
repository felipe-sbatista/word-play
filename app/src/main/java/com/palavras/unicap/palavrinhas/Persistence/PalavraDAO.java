package com.palavras.unicap.palavrinhas.Persistence;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.palavras.unicap.palavrinhas.Entity.Palavra;

import java.util.List;

@Dao
public interface PalavraDAO {

    @Query("SELECT * FROM Palavra")
    List<Palavra> loadAllPalavras();



    @Insert
    Long insert(Palavra p);

    @Delete
    void delete(Palavra p);


}
