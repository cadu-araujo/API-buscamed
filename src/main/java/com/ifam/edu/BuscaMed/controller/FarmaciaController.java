package com.ifam.edu.BuscaMed.controller;

import com.ifam.edu.BuscaMed.dto.RemedioInputDTO;
import com.ifam.edu.BuscaMed.dto.RemedioOutputDTO;
import com.ifam.edu.BuscaMed.model.Farmacia;
import com.ifam.edu.BuscaMed.dto.FarmaciaInputDTO;
import com.ifam.edu.BuscaMed.dto.FarmaciaOutputDTO;
import com.ifam.edu.BuscaMed.model.Login;
import com.ifam.edu.BuscaMed.model.Remedio;
import com.ifam.edu.BuscaMed.repository.FarmaciaRepository;
import com.ifam.edu.BuscaMed.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/farmacia")
public class FarmaciaController {
    @Autowired
    private FarmaciaRepository farmaciaRepository;

    @Autowired
    private LoginRepository loginRepository;

    @GetMapping
    public ResponseEntity<List<FarmaciaOutputDTO>> list() {
        List<Farmacia> farmaciaList = farmaciaRepository.findAll();
        List<FarmaciaOutputDTO> farmaciaOutputDTOList = new ArrayList<>();

        for (Farmacia farmacia : farmaciaList) {
            farmaciaOutputDTOList.add(new FarmaciaOutputDTO(farmacia));
        }

        if (!farmaciaOutputDTOList.isEmpty()) {
            return new ResponseEntity<>(farmaciaOutputDTOList, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "A lista de farmácias está vazia");
        }
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FarmaciaOutputDTO> getOne(@PathVariable Long id) {
        Optional<Farmacia> farmaciaOptional = farmaciaRepository.findById(id);

        FarmaciaOutputDTO farmaciaOutputDTO = null;

        if (farmaciaOptional.isPresent()) {
            farmaciaOutputDTO = new FarmaciaOutputDTO(farmaciaOptional.get());
        }

        if (farmaciaOutputDTO != null) {
            return new ResponseEntity<>(farmaciaOutputDTO, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "O farmácia de id " + id + " não foi encontrado");
        }
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FarmaciaOutputDTO> update(@PathVariable Long id, @RequestBody FarmaciaInputDTO farmaciaInputDTO) {
        Optional<Farmacia> farmaciaOptional = farmaciaRepository.findById(id);

        if (farmaciaOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A farmácia de id " + id + " não foi encontrada");
        }

        Farmacia novaFarmacia = farmaciaOptional.get();
        novaFarmacia.setNome(farmaciaInputDTO.getNome());
        novaFarmacia.setCnpj(farmaciaInputDTO.getCnpj());
        novaFarmacia.setEndereco(farmaciaInputDTO.getEndereco());

        farmaciaRepository.save(novaFarmacia);

        FarmaciaOutputDTO farmaciaOutputDTO = new FarmaciaOutputDTO(novaFarmacia);
        return new ResponseEntity<>(farmaciaOutputDTO, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FarmaciaOutputDTO> create(@RequestBody FarmaciaInputDTO farmaciaInputDTO) {
        try {
            Optional<Login> login = loginRepository.findById(farmaciaInputDTO.getLogin());

            if (login.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Login não encontrado para o email: " + farmaciaInputDTO.getLogin());
            }

            Farmacia farmacia = new Farmacia(
                    farmaciaInputDTO.getNome(),
                    farmaciaInputDTO.getCnpj(),
                    farmaciaInputDTO.getEndereco(),
                    farmaciaInputDTO.getEmail(),
                    login.get()
            );

            FarmaciaOutputDTO farmaciaOutputDTO = new FarmaciaOutputDTO(farmaciaRepository.save(farmacia));

            return new ResponseEntity<>(farmaciaOutputDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "A farmácia não foi criada: " + e.getMessage());
        }
    }


    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            if (!farmaciaRepository.existsById(id)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A farmácia de id " + id + " não foi encontrada");
            }
            farmaciaRepository.deleteById(id);
            return new ResponseEntity<>("Farmácia excluída com sucesso", HttpStatus.OK);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "A farmácia não foi excluída: " + e.getMessage());
        }
    }

    @GetMapping(path = "/login/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FarmaciaOutputDTO> getLogin(@PathVariable Long id) {
        Farmacia farmacia = farmaciaRepository.findByLogin(id);

        if (new FarmaciaOutputDTO(farmacia) != null) {
            return new ResponseEntity<>(new FarmaciaOutputDTO(farmacia), HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "O farmácia de id " + id + " não foi encontrado");
        }
    }

    @GetMapping(path = "/name/{nome}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FarmaciaOutputDTO>> getNome(@PathVariable String nome) throws UnsupportedEncodingException {
        List<Farmacia> farmacias = farmaciaRepository.findByNome(nome);

        List<FarmaciaOutputDTO> farmaciaOutputDTOList = new ArrayList<>();

        for (Farmacia farmacia:farmacias) {
            farmaciaOutputDTOList.add(new FarmaciaOutputDTO(farmacia));
        }

        if (farmaciaOutputDTOList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "A farmácia de nome " + nome + " não foi encontrado");
        } else {
            return new ResponseEntity<>(farmaciaOutputDTOList, HttpStatus.OK);
        }
    }
}
