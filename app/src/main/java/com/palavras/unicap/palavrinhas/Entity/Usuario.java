package com.palavras.unicap.palavrinhas.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;


@Entity(tableName = "usuario")
public class Usuario {

    @PrimaryKey
    @ColumnInfo(name = "Id")
    private Long id;

    @ColumnInfo(name = "nome")
    private String nome;

    @ColumnInfo(name = "data_nascimento")
    private String dataNascimento;

    @Ignore
    private List<Integer> pontos = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public List<Integer> getPontos() {
        return pontos;
    }

    public void setPontos(List<Integer> pontos) {
        this.pontos = pontos;
    }
}
