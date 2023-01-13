package com.example.caopanhia.modelo;

public class MarcacaoVeterinaria {

    private int id;
    private String data, hora, vet, cao;

    public MarcacaoVeterinaria(int id, String data, String hora, String vet, String cao) {
        this.id = id;
        this.data = data;
        this.hora = hora;
        this.vet = vet;
        this.cao = cao;
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

    public String getVet() {
        return vet;
    }

    public void setVet(String vet) {
        this.vet = vet;
    }

    public String getCao() {
        return cao;
    }

    public void setCao(String cao) {
        this.cao = cao;
    }
}
