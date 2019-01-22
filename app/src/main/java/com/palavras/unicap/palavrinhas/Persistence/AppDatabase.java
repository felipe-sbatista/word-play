package com.palavras.unicap.palavrinhas.Persistence;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;

import com.palavras.unicap.palavrinhas.Entity.Palavra;
import com.palavras.unicap.palavrinhas.Entity.Pontuacao;
import com.palavras.unicap.palavrinhas.Entity.Usuario;

import java.io.Serializable;

@Database(entities = {Usuario.class, Pontuacao.class, Palavra.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase implements Serializable{
    public abstract UsuarioDAO usuarioDAO();
    public abstract PalavraDAO palavraDAO();
    public abstract PontuacaoDAO pontuacaoDAO();

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
        }
    };


}
