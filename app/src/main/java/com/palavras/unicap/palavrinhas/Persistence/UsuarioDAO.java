package com.palavras.unicap.palavrinhas.Persistence;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.palavras.unicap.palavrinhas.Entity.Usuario;

import java.util.List;

@Dao
public interface UsuarioDAO {

    @Insert
    long insertUsuario(Usuario usuario);

    @Query("SELECT * FROM usuario")
    List<Usuario> loadAllUsuarios();

    @Query("SELECT DISTINCT * FROM usuario WHERE usuario.Id IN(:ids)")
    List<Usuario> findByIds(int []ids);

}
