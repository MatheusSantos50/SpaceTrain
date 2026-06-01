package com.siteoob.spacetrain.model.cliente;

public class Cliente {

    private int id;

    private String cpf, nome;

     
    public Cliente(){}
    
    public Cliente(String nome, String cpf){
        this.nome = nome;
        this.cpf = cpf;
    }

    
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
