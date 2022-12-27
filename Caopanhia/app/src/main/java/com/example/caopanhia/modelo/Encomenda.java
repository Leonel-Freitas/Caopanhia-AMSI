package com.example.caopanhia.modelo;

public class Encomenda {

    private int id;
    private float valorTotal;
    private String data, estado;

    public Encomenda(int id, float valorTotal, String data, String estado) {
        this.id = id;
        this.valorTotal = valorTotal;
        this.data = data;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
