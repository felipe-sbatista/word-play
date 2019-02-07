package com.palavras.unicap.palavrinhas.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
