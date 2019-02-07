package com.palavras.unicap.palavrinhas.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public byte[] getSom() {
        return som;
    }

    public void setSom(byte[] som) {
        this.som = som;
    }
}
