package com.ifam.edu.BuscaMed.dto;

import com.ifam.edu.BuscaMed.controller.UsuarioController;
import com.ifam.edu.BuscaMed.model.Farmacia;
import com.ifam.edu.BuscaMed.model.Login;
import com.ifam.edu.BuscaMed.model.Remedio;
import com.ifam.edu.BuscaMed.model.Usuario;
import com.ifam.edu.BuscaMed.repository.FarmaciaRepository;
import com.ifam.edu.BuscaMed.repository.LoginRepository;
import com.ifam.edu.BuscaMed.repository.UsuarioRepository;

import java.util.Optional;

public class UsuarioInputDTO {
    private String nome;
    private String telefone;
    private String email;
    private String endereco;
    private Long login;


    public UsuarioInputDTO() {
    }

    public Usuario build(LoginRepository loginRepository) {
        Optional<Login> loginObj = loginRepository.findById(login);

        if (loginObj.isPresent()) {
            return new Usuario(nome, telefone, email, endereco, loginObj.get());
        }

        return null;
    }

    public UsuarioInputDTO(String nome, String telefone, String email, String endereco, Long login) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
        this.login = login;
    }

    public UsuarioInputDTO(Usuario usuario) {
        this.nome = usuario.getNome();
        this.telefone = usuario.getTelefone();
        this.email = usuario.getEmail();
        this.endereco = usuario.getEndereco();
        this.login = usuario.getLogin().getId();
    }

    // Getters e setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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

