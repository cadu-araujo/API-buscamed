package com.ifam.edu.BuscaMed.controller;

import com.ifam.edu.BuscaMed.model.Farmacia;
import com.ifam.edu.BuscaMed.model.Remedio;
import com.ifam.edu.BuscaMed.model.Usuario;
import com.ifam.edu.BuscaMed.model.Venda;
import com.ifam.edu.BuscaMed.dto.VendaInputDTO;
import com.ifam.edu.BuscaMed.dto.VendaOutputDTO;
import com.ifam.edu.BuscaMed.repository.RemedioRepository;
import com.ifam.edu.BuscaMed.repository.UsuarioRepository;
import com.ifam.edu.BuscaMed.repository.VendaRepository;
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
@RequestMapping("/api/venda")
public class VendaController {
    @Autowired
    private RemedioRepository remedioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private VendaRepository vendaRepository;

    @GetMapping
    public ResponseEntity<List<VendaOutputDTO>> list() {
        List<Venda> vendaList = vendaRepository.findAll();
        List<VendaOutputDTO> vendaOutputDTOList = new ArrayList<>();

        for (Venda venda : vendaList) {
            vendaOutputDTOList.add(new VendaOutputDTO(venda));
        }

        if (!vendaOutputDTOList.isEmpty()) {
            return new ResponseEntity<>(vendaOutputDTOList, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "A lista de vendas está vazia");
        }
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VendaOutputDTO> getOne(@PathVariable Long id) {
        Optional<Venda> vendaOptional = vendaRepository.findById(id);

        VendaOutputDTO vendaOutputDTO = null;

        if (vendaOptional.isPresent()) {
            vendaOutputDTO = new VendaOutputDTO(vendaOptional.get());
        }

        if (vendaOutputDTO != null) {
            return new ResponseEntity<>(vendaOutputDTO, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "O venda de id " + id + " não foi encontrado");
        }
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VendaOutputDTO> update(@PathVariable Long id, @RequestBody VendaInputDTO vendaInputDTO) {
        Optional<Venda> vendaOptional = vendaRepository.findById(id);

        if (!vendaOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Venda de id " + id + " não encontrada");
        }
        Venda novaVenda = vendaOptional.get();

        Remedio remedio = remedioRepository.findById(vendaInputDTO.getRemedioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Remédio não encontrado"));

        novaVenda.setRemedio(remedio);

        novaVenda.setConcluida(vendaInputDTO.getConcluida());

        Usuario usuario = usuarioRepository.findById(vendaInputDTO.getUsuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        novaVenda.setUsuario(usuario);

        vendaRepository.save(novaVenda);

        VendaOutputDTO vendaOutputDTO = new VendaOutputDTO(novaVenda);
        return new ResponseEntity<>(vendaOutputDTO, HttpStatus.OK);
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Venda> create(@RequestBody VendaInputDTO vendaInputDTO) {
        try {
            Venda venda = vendaInputDTO.build(usuarioRepository, remedioRepository);
            vendaRepository.save(venda);
            return new ResponseEntity<>(venda, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao criar a venda: " + e.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            vendaRepository.deleteById(id);
            return new ResponseEntity<>("venda excluído com sucesso", HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "O venda não foi excluído: " + e.getMessage());
        }
    }
}
