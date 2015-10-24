package br.com.targettrust.exercicio10.model;

import java.io.Serializable;

/**
 * Created by instrutor on 23/10/2015.
 */
public class Filme implements Serializable {
    private static final long serialVersionUID = 1L;

    public String id;
    public String titulo;
    public String imagem;

    public Filme(String id, String titulo, String imagem) {
        this.id = id;
        this.titulo = titulo;
        this.imagem = imagem;
    }
}
