package com.ifam.edu.BuscaMed.dto;

import com.ifam.edu.BuscaMed.model.Login;
import com.ifam.edu.BuscaMed.repository.LoginRepository;

public class LoginOutputDTO {
    private Long id;
    private String email;
    private String tipo;

    public LoginOutputDTO(Login login) {
        this.id = login.getId();
        this.email = login.getEmail();
        this.tipo = login.getTipo();
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
