package com.ifam.edu.BuscaMed.controller;

import com.ifam.edu.BuscaMed.dto.LoginInputDTO;
import com.ifam.edu.BuscaMed.dto.LoginOutputDTO;
import com.ifam.edu.BuscaMed.model.Login;
import com.ifam.edu.BuscaMed.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    @Autowired
    private LoginRepository loginRepository;

    @GetMapping
    public ResponseEntity<List<LoginOutputDTO>> list() {
        List<Login> loginList = loginRepository.findAll();
        List<LoginOutputDTO> loginOutputDTOList = new ArrayList<>();

        for (Login login : loginList) {
            loginOutputDTOList.add(new LoginOutputDTO(login));
        }

        if (!loginOutputDTOList.isEmpty()) {
            return new ResponseEntity<>(loginOutputDTOList, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "A lista de logins está vazia");
        }
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginOutputDTO> getOne(@PathVariable Long id) {
        Optional<Login> loginOptional = loginRepository.findById(id);

        LoginOutputDTO loginOutputDTO = null;

        if (loginOptional.isPresent()) {
            loginOutputDTO = new LoginOutputDTO(loginOptional.get());
        }

        if (loginOutputDTO != null) {
            return new ResponseEntity<>(loginOutputDTO, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "O login de id " + id + " não foi encontrado");
        }
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginOutputDTO> update(@PathVariable Long id, @RequestBody LoginInputDTO loginInputDTO) {
        Optional<Login> loginOptional = loginRepository.findById(id);

        if (loginOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "O login de id " + id + " não foi encontrado");
        }
        Login novoLogin = loginOptional.get();
        novoLogin.setEmail(loginInputDTO.getEmail());
        novoLogin.setSenha(loginInputDTO.getSenha());
        novoLogin.setTipo(loginInputDTO.getTipo());

        loginRepository.save(novoLogin);

        LoginOutputDTO loginOutputDTO = new LoginOutputDTO(novoLogin);
        return new ResponseEntity<>(loginOutputDTO, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginOutputDTO> create(@RequestBody LoginInputDTO loginInputDTO) {
        try {
            LoginOutputDTO loginOutputDTO = new LoginOutputDTO(
                    loginRepository.save(loginInputDTO.build())
            );

            return new ResponseEntity<>(loginOutputDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "O login não foi criado: " + e.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if (!loginRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O login de ID " + id + " não foi encontrado");
        }

        loginRepository.deleteById(id);
        return new ResponseEntity<>("Login excluído com sucesso", HttpStatus.OK);
    }

    @PostMapping("/autenticar")
    public ResponseEntity<Login> autenticar(@RequestBody LoginInputDTO loginInputDTO) {
        Login login = loginRepository.findByEmail(loginInputDTO.getEmail());

        if (login == null || !loginInputDTO.getSenha().equals(login.getSenha())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ou senha inválidos");
        }

        return new ResponseEntity<>(login, HttpStatus.OK);
    }
}
