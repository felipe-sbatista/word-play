package com.palavras.unicap.palavrinhas.Persistence;

import android.content.Context;

import com.palavras.unicap.palavrinhas.Entity.Palavra;
import com.palavras.unicap.palavrinhas.Entity.Pontuacao;
import com.palavras.unicap.palavrinhas.Entity.Usuario;

import java.io.Serializable;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Usuario.class, Pontuacao.class, Palavra.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase implements Serializable{
    public abstract UsuarioDAO usuarioDAO();
    public abstract PalavraDAO palavraDAO();
    public abstract PontuacaoDAO pontuacaoDAO();

    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class, "palavras.db")
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }


}
