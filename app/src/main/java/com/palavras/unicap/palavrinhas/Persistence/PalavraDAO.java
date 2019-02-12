package com.palavras.unicap.palavrinhas.Persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

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
