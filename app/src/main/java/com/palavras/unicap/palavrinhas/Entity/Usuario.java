package com.palavras.unicap.palavrinhas.Entity;

import java.util.ArrayList;
import java.util.List;


public class Usuario {

    private String nome;

    private int segundos;

    private String dataNascimento;

    private List<Integer> pontos = new ArrayList<>();


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

    public int getSegundos() {
        return segundos;
    }

    public void setSegundos(int segundos) {
        this.segundos = segundos;
    }
}
