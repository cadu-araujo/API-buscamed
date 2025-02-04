package com.ifam.edu.BuscaMed.dto;

import com.ifam.edu.BuscaMed.model.Login;

public class LoginInputDTO {
    private Long id;
    private String email;
    private String senha;
    private String tipo;

    public LoginInputDTO(Long id, String email, String senha, String tipo) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
    }

    public Login build() {
        Login login = new Login();
        login.setEmail(email);
        login.setSenha(senha);
        login.setTipo(tipo);
        return login;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
