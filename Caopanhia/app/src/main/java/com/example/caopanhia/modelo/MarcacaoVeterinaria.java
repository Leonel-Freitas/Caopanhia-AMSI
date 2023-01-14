package com.example.caopanhia.modelo;

public class MarcacaoVeterinaria {

    private int id;
    private String data, hora, nomeClient, nomeVet, nomeCao;

    public MarcacaoVeterinaria(int id, String data, String hora, String nomeClient, String nomeVet, String nomeCao) {
        this.id = id;
        this.data = data;
        this.hora = hora;
        this.nomeClient = nomeClient;
        this.nomeVet = nomeVet;
        this.nomeCao = nomeCao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getNomeClient() {
        return nomeClient;
    }

    public void setNomeClient(String nomeClient) {
        this.nomeClient = nomeClient;
    }

    public String getNomeVet() {
        return nomeVet;
    }

    public void setNOmeVet(String nomeVet) {
        this.nomeVet = nomeVet;
    }

    public String getNomeCao() {
        return nomeCao;
    }

    public void setNomeCao(String nomeCao) {
        this.nomeCao = nomeCao;
    }
}
