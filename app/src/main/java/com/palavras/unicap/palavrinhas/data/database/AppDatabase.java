package com.palavras.unicap.palavrinhas.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;


import com.palavras.unicap.palavrinhas.data.dao.UsuarioDAO;
import com.palavras.unicap.palavrinhas.entity.UsuarioDB;

@Database(entities = {UsuarioDB.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UsuarioDAO userDao();
}
