package com.ifam.edu.BuscaMed.dto;

import com.ifam.edu.BuscaMed.model.Remedio;
import com.ifam.edu.BuscaMed.model.Usuario;
import com.ifam.edu.BuscaMed.model.Venda;
import com.ifam.edu.BuscaMed.repository.RemedioRepository;
import com.ifam.edu.BuscaMed.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class VendaInputDTO {
    private Long usuarioId;
    private Long remedioId;
    private String concluida;

    public Venda build(UsuarioRepository usuarioRepository, RemedioRepository remedioRepository) {
        Usuario usuarioObj = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        Remedio remedioObj = remedioRepository.findById(remedioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Remédio não encontrado"));

        return new Venda(usuarioObj, remedioObj, concluida);
    }

    public VendaInputDTO() {
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getRemedioId() {
        return remedioId;
    }

    public void setRemedioId(Long remedioId) {
        this.remedioId = remedioId;
    }

    public String getConcluida() {
        return concluida;
    }

    public void setConcluida(String concluida) {
        this.concluida = concluida;
    }
}
