package com.palavras.unicap.palavrinhas.entity;

import java.io.Serializable;

public class Palavra  implements Serializable {

    private String texto;

    private String hashImagem;

    private String nomeArquivo;


    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getHashImagem() {
        return hashImagem;
    }

    public void setHashImagem(String hashImagem) {
        this.hashImagem = hashImagem;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }
}
