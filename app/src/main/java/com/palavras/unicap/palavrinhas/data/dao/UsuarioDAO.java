package com.palavras.unicap.palavrinhas.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.palavras.unicap.palavrinhas.entity.UsuarioDB;

import java.util.List;

@Dao
public interface UsuarioDAO {

    @Insert
    void Salvar(UsuarioDB user);

    @Query("SELECT * FROM UsuarioDB")
    List<UsuarioDB> getAll();

}
