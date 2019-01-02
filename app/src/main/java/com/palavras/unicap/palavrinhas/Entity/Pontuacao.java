package com.palavras.unicap.palavrinhas.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(tableName = "pontuacao", foreignKeys = @ForeignKey(entity = Usuario.class, parentColumns = "Id", childColumns = "usuario"))
public class Pontuacao implements Serializable {

    @PrimaryKey
    @ColumnInfo(name="Id")
    private Long id;

    @ColumnInfo(name = "pontos")
    private int pontos;

    @ColumnInfo(name = "usuario")
    private int usuarioId;

    @ColumnInfo(name = "data")
    private String data;

    @Ignore
    private Usuario usuario;
}
