package com.palavras.unicap.palavrinhas.Persistence;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.palavras.unicap.palavrinhas.Entity.Pontuacao;

import java.util.List;

@Dao
public interface PontuacaoDAO {

    @Query("SELECT * FROM pontuacao ORDER BY pontos DESC LIMIT 10")
    List<Pontuacao> listTop10();

    @Insert
    Long insertPontuacao(Pontuacao pontuacao);
}
