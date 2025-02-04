package com.ifam.edu.BuscaMed.controller;

import com.ifam.edu.BuscaMed.model.Farmacia;
import com.ifam.edu.BuscaMed.model.Remedio;
import com.ifam.edu.BuscaMed.dto.RemedioInputDTO;
import com.ifam.edu.BuscaMed.dto.RemedioOutputDTO;
import com.ifam.edu.BuscaMed.repository.FarmaciaRepository;
import com.ifam.edu.BuscaMed.repository.RemedioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/remedio")
public class RemedioController {
    @Autowired
    private RemedioRepository remedioRepository;

    @Autowired
    private FarmaciaRepository farmaciaRepository;

    @GetMapping
    public ResponseEntity<List<RemedioOutputDTO>> list() {
        List<Remedio> remedioList = remedioRepository.findAll();
        List<RemedioOutputDTO> remedioOutputDTOList = new ArrayList<>();

        for (Remedio remedio : remedioList) {
            remedioOutputDTOList.add(new RemedioOutputDTO(remedio));
        }

        if (!remedioOutputDTOList.isEmpty()) {
            return new ResponseEntity<>(remedioOutputDTOList, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "A lista de remédios está vazia");

        }
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RemedioOutputDTO> getOne(@PathVariable Long id) {
        Remedio remedio = remedioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Remédio não encontrado"));
        RemedioOutputDTO remedioOutputDTO = new RemedioOutputDTO(remedio);
        return new ResponseEntity<>(remedioOutputDTO, HttpStatus.OK);
    }

    @Transactional
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RemedioOutputDTO> update(@PathVariable Long id, @RequestBody RemedioInputDTO remedioInputDTO) {
        Optional<Remedio> remedioOptional = remedioRepository.findById(id);

        RemedioOutputDTO remedioOutputDTO = null;

        if (remedioOptional.isPresent()) {
            Remedio novoRemedio = remedioOptional.get();
            novoRemedio.setNome(remedioInputDTO.getNome());
            novoRemedio.setDescricao(remedioInputDTO.getDescricao());
            novoRemedio.setMarca(remedioInputDTO.getMarca());
            novoRemedio.setQuantidade(remedioInputDTO.getQuantidade());
            novoRemedio.setValor(remedioInputDTO.getValor());

            Farmacia farmacia = farmaciaRepository.findByNome_(remedioInputDTO.getFarmacia());


            novoRemedio.setFarmacia(farmacia);

            remedioRepository.save(novoRemedio);

            remedioOutputDTO = new RemedioOutputDTO(novoRemedio);
        }

        if (remedioOutputDTO != null) {
            return new ResponseEntity<>(remedioOutputDTO, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "O remédio de id " + id + " não foi encontrado");
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RemedioOutputDTO> create(@RequestBody RemedioInputDTO remedioInputDTO) {
        try {
            RemedioOutputDTO remedioOutputDTO = new RemedioOutputDTO(
                    remedioRepository.save(remedioInputDTO.build(farmaciaRepository))
            );

            return new ResponseEntity<>(remedioOutputDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "O remédio não foi criado: " + e.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            remedioRepository.deleteById(id);

            return new ResponseEntity<>("remédio excluído com sucesso", HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "O remédio não foi excluído: " + e.getMessage());
        }
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<RemedioOutputDTO>> getByNome(@PathVariable String nome) {
        List<Remedio> remedios = remedioRepository.findByNome(nome);
        if (remedios.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Nenhum remédio encontrado com o nome: " + nome);
        }
        List<RemedioOutputDTO> remedioOutputDTOList = new ArrayList<>();
        for (Remedio remedio : remedios) {
            remedioOutputDTOList.add(new RemedioOutputDTO(remedio));
        }
        return new ResponseEntity<>(remedioOutputDTOList, HttpStatus.OK);
    }

    @GetMapping("/farmacia/{id}")
    public ResponseEntity<List<RemedioOutputDTO>> getByfarmacia(@PathVariable Long id) {
        List<Remedio> remedios = remedioRepository.findByFarmacia(id);
        if (remedios.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Nenhum remédio encontrado");
        }
        List<RemedioOutputDTO> remedioOutputDTOList = new ArrayList<>();
        for (Remedio remedio : remedios) {
            remedioOutputDTOList.add(new RemedioOutputDTO(remedio));
        }
        return new ResponseEntity<>(remedioOutputDTOList, HttpStatus.OK);
    }

    @GetMapping(path = "/name/{nome}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RemedioOutputDTO>> getNome(@PathVariable String nome) throws UnsupportedEncodingException {
        List<Remedio> remedioOptional = remedioRepository.findByNome(nome);

        List<RemedioOutputDTO> remedioOutputDTOList = new ArrayList<>();

        for (Remedio remedio : remedioOptional) {
            remedioOutputDTOList.add(new RemedioOutputDTO(remedio));
        }

        if (remedioOutputDTOList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "O remédio de nome" + nome + " não foi encontrado");
        } else {
            return new ResponseEntity<>(remedioOutputDTOList, HttpStatus.OK);
        }
    }
}
