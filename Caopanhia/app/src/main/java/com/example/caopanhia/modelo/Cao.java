package com.example.caopanhia.modelo;

public class Cao {

    private int id;
    private int anoNascimento;
    private String nome, genero, microship, castrado;
    //private String imagem;


    public Cao(int id, int anoNascimento, String nome, String genero, String microship, String castrado) {
        this.id = id;
        this.anoNascimento = anoNascimento;
        this.nome = nome;
        this.genero = genero;
        this.microship = microship;
        this.castrado = castrado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(int anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getMicroship() {
        return microship;
    }

    public void setMicroship(String microship) {
        this.microship = microship;
    }

    public String getCastrado() {
        return castrado;
    }

    public void setCastrado(String castrado) {
        this.castrado = castrado;
    }
}
