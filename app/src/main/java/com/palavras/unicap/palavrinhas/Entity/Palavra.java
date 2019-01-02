package com.palavras.unicap.palavrinhas.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(tableName = "Palavra")
public class Palavra {

    @PrimaryKey
    private Long id;

    @ColumnInfo(name = "texto")
    private String texto;

    @ColumnInfo(name = "imagem")
    private byte[] imagem;

    @ColumnInfo(name = "som")
    private byte[] som;

}
