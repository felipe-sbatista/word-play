package com.palavras.unicap.palavrinhas.Entity;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Base64;

public class Palavra {

    private Long id;

    private String texto;

    private String imagemBase64;

    private String somBase64;

    private byte[] imagem = null;

    private byte[] som = null;


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

    public String getImagemBase64() {
        return imagemBase64;
    }

    public void setImagemBase64(String imagemBase64) {
        this.imagemBase64 = imagemBase64;
    }

    public String getSomBase64() {
        return somBase64;
    }

    public void setSomBase64(String somBase64) {
        this.somBase64 = somBase64;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public byte[] getImagem(){
        if(this.imagem == null){
            this.imagem = Base64.getEncoder().encode(this.imagemBase64.getBytes());
        }
        return this.imagem;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public byte[] getSom(){
        if(this.som == null){
            this.som = Base64.getEncoder().encode(this.somBase64.getBytes());
        }
        return this.som;
    }


}
