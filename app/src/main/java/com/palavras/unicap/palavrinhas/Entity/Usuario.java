package com.palavras.unicap.palavrinhas.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(tableName = "usuario")
public class Usuario {

    @PrimaryKey
    @ColumnInfo(name = "Id")
    private Long id;

    @ColumnInfo(name = "Nome")
    private String nome;

    @ColumnInfo(name = "data_nascimento")
    private String dataNascimento;

    @Ignore
    private List<Integer> pontos = new ArrayList<>();

}
