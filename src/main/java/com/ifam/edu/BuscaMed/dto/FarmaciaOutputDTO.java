package com.ifam.edu.BuscaMed.dto;

import com.ifam.edu.BuscaMed.model.Farmacia;

public class FarmaciaOutputDTO {
    private Long id;
    private String nome;
    private String cnpj;
    private String endereco;
    private String email;
    private Long login;

    public FarmaciaOutputDTO(Farmacia farmacia) {
        this.id = farmacia.getId();
        this.nome = farmacia.getNome();
        this.cnpj = farmacia.getCnpj();
        this.endereco = farmacia.getEndereco();
        this.email = farmacia.getEmail();
        this.login = farmacia.getLogin() != null ? farmacia.getLogin().getId() : null;
    }

    // Getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getLogin() {
        return login;
    }

    public void setLogin(Long login) {
        this.login = login;
    }
}
