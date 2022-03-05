package com.palavras.unicap.palavrinhas.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UsuarioDB {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "nome")
    public String nome;

    @ColumnInfo(name = "pontuacao")
    public int pontuacao;
}
