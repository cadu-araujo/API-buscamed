package com.ifam.edu.BuscaMed.dto;

import com.ifam.edu.BuscaMed.model.Remedio;

public class RemedioOutputDTO {
    private Long id;
    private String nome;
    private String marca;
    private Integer quantidade;
    private String descricao;
    private Float valor;
    private String farmacia;

    public RemedioOutputDTO(Remedio remedio) {
        this.id = remedio.getId();
        this.nome = remedio.getNome();
        this.marca = remedio.getMarca();
        this.quantidade = remedio.getQuantidade();
        this.descricao = remedio.getDescricao();
        this.valor = remedio.getValor();
        this.farmacia = remedio.getFarmacia().getNome();
    }

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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public String getFarmacia() {
        return farmacia;
    }

    public void setFarmacia(String farmacia) {
        this.farmacia = farmacia;
    }
}
