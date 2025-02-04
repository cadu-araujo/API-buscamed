package com.ifam.edu.BuscaMed.dto;

import com.ifam.edu.BuscaMed.model.Farmacia;
import com.ifam.edu.BuscaMed.model.Login;
import com.ifam.edu.BuscaMed.model.Usuario;
import com.ifam.edu.BuscaMed.repository.LoginRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FarmaciaInputDTO {
    private String nome;
    private String cnpj;
    private String email;
    private String endereco;
    private Long login;

    public FarmaciaInputDTO(Farmacia farmacia) {
        this.nome = farmacia.getNome();
        this.cnpj = farmacia.getCnpj();
        this.email = farmacia.getEmail();
        this.endereco = farmacia.getEndereco();
        this.login = farmacia.getLogin().getId();
    }

    public Farmacia build(LoginRepository loginRepository) {
        Login loginObj = loginRepository.findByEmail(email);
        if (loginObj == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Login não encontrado para o email: " + email);
        }
        return new Farmacia(nome, cnpj, endereco, email, loginObj);
    }


    public FarmaciaInputDTO() {
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
