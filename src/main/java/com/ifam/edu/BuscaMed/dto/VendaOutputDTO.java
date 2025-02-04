package com.ifam.edu.BuscaMed.dto;

import com.ifam.edu.BuscaMed.model.Venda;

public class VendaOutputDTO {
    private Long id;
    private String usuario;
    private String remedio;
    private String farmacia;
    private String concluida;

    public VendaOutputDTO(Venda venda) {
        this.id = venda.getId();
        this.usuario = venda.getUsuario() != null ? venda.getUsuario().getNome() : null;
        this.remedio = venda.getRemedio() != null ? venda.getRemedio().getNome() : null;
        this.farmacia = venda.getRemedio() != null && venda.getRemedio().getFarmacia() != null ? venda.getRemedio().getFarmacia().getNome() : null;
        this.concluida = venda.getConcluida();
    }

    // Getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getRemedio() {
        return remedio;
    }

    public void setRemedio(String remedio) {
        this.remedio = remedio;
    }

    public String getFarmacia() {
        return farmacia;
    }

    public void setFarmacia(String farmacia) {
        this.farmacia = farmacia;
    }

    public String getConcluida() {
        return concluida;
    }

    public void setConcluida(String concluida) {
        this.concluida = concluida;
    }
}
