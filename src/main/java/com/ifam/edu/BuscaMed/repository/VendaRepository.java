package com.ifam.edu.BuscaMed.repository;

import com.ifam.edu.BuscaMed.model.Farmacia;
import com.ifam.edu.BuscaMed.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VendaRepository  extends JpaRepository<Venda, Long> {

    @Query("select v from Venda v where v.remedio.farmacia.id = ?1")
    List<Venda> findByFarmacia(Long id);

    @Query("select v from Venda v where v.usuario.id = ?1")
    List<Venda> findByUser(Long id);
}
