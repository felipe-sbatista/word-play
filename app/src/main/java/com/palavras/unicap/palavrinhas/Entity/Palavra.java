package com.palavras.unicap.palavrinhas.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Palavra {
    private String texto;
    private byte[] imagem;
    private Long id;
    private byte[] som;
    private String nomeImagem;
    private String nomeSom;
}
