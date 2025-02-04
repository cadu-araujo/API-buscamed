package com.ifam.edu.BuscaMed.controller;

import com.ifam.edu.BuscaMed.model.Login;
import com.ifam.edu.BuscaMed.model.Usuario;
import com.ifam.edu.BuscaMed.dto.UsuarioInputDTO;
import com.ifam.edu.BuscaMed.dto.UsuarioOutputDTO;
import com.ifam.edu.BuscaMed.repository.LoginRepository;
import com.ifam.edu.BuscaMed.repository.UsuarioRepository;
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
@RequestMapping("/api/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LoginRepository loginRepository;

    @GetMapping
    public ResponseEntity<List<UsuarioOutputDTO>> list() {
        List<Usuario> usuarioList = usuarioRepository.findAll();
        List<UsuarioOutputDTO> usuarioOutputDTOList = new ArrayList<>();

        for (Usuario usuario : usuarioList) {
            usuarioOutputDTOList.add(new UsuarioOutputDTO(usuario));
        }

        if (!usuarioOutputDTOList.isEmpty()) {
            return new ResponseEntity<>(usuarioOutputDTOList, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "A lista de usuários está vazia");
        }
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioOutputDTO> getOne(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        UsuarioOutputDTO usuarioOutputDTO = null;

        if (usuarioOptional.isPresent()) {
            usuarioOutputDTO = new UsuarioOutputDTO(usuarioOptional.get());
        }

        if (usuarioOutputDTO != null) {
            return new ResponseEntity<>(usuarioOutputDTO, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "O usuário de id " + id + " não foi encontrado");
        }
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioOutputDTO> update(@PathVariable Long id, @RequestBody UsuarioInputDTO usuarioInputDTO) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if (!usuarioOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário de id " + id + " não encontrado");
        }

        Usuario usuario = usuarioOptional.get();
        usuario.setNome(usuarioInputDTO.getNome());
        usuario.setTelefone(usuarioInputDTO.getTelefone());
        usuario.setEndereco(usuarioInputDTO.getEndereco());
        usuario.setEmail(usuarioInputDTO.getEmail());

        Optional<Login> login = loginRepository.findById(usuarioInputDTO.getLogin());
        if (login.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Login não encontrado");
        }
        usuario.setLogin(login.get());

        usuarioRepository.save(usuario);

        UsuarioOutputDTO usuarioOutputDTO = new UsuarioOutputDTO(usuario);
        return new ResponseEntity<>(usuarioOutputDTO, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioOutputDTO> create(@RequestBody UsuarioInputDTO usuarioInputDTO) {
        try {
            UsuarioOutputDTO usuarioOutputDTO = new UsuarioOutputDTO(
                    usuarioRepository.save(usuarioInputDTO.build(loginRepository))
            );

            return new ResponseEntity<>(usuarioOutputDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "O usuário não foi criado: " + e.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            usuarioRepository.deleteById(id);
            return new ResponseEntity<>("usuário excluído com sucesso", HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "O usuário não foi excluído: " + e.getMessage());
        }
    }

    @GetMapping(path = "/login/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioOutputDTO> getByLogin(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.findByLogin(id);

        UsuarioOutputDTO usuarioOutputDTO = null;

        if (usuario!= null) {
            usuarioOutputDTO = new UsuarioOutputDTO(usuario);
        }

        if (usuarioOutputDTO != null) {
            return new ResponseEntity<>(usuarioOutputDTO, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "O usuário de id " + id + " não foi encontrado");
        }
    }
}
