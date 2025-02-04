package com.ifam.edu.BuscaMed.dto;

import com.ifam.edu.BuscaMed.model.Farmacia;
import com.ifam.edu.BuscaMed.model.Remedio;
import com.ifam.edu.BuscaMed.repository.FarmaciaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class RemedioInputDTO {

    private String nome;
    private String marca;
    private Integer quantidade;
    private String descricao;
    private Float valor;
    private String farmacia;

    public Remedio build(FarmaciaRepository farmaciaRepository) {
        Farmacia farmaciaObj = farmaciaRepository.findByNome_(farmacia);
        return new Remedio(nome, marca, quantidade, descricao, valor, farmaciaObj);
    }

    public RemedioInputDTO() {
    }

    public RemedioInputDTO(String nome, String marca, Integer quantidade, String descricao, Float valor, String farmacia) {
        this.nome = nome;
        this.marca = marca;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.valor = valor;
        this.farmacia = farmacia;
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

