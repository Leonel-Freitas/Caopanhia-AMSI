package com.example.caopanhia.modelo;

public class Cao {

    private int id;
    private int anoNascimento;
    private int idUserProfile;
    private String nome, genero, microship, castrado, adotado;


    public Cao(int id, int anoNascimento, int idUserProfile, String nome, String genero, String microship, String castrado, String adotado) {
        this.id = id;
        this.anoNascimento = anoNascimento;
        this.idUserProfile = idUserProfile;
        this.nome = nome;
        this.genero = genero;
        this.microship = microship;
        this.castrado = castrado;
        this.adotado = adotado;
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

    public int getIdUserProfile() {
        return idUserProfile;
    }

    public void setIdUserProfile(int idUserProfile) {
        this.idUserProfile = idUserProfile;
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

    public String getAdotado() {
        return adotado;
    }

    public void setAdotado(String adotado) {
        this.adotado = adotado;
    }
}
