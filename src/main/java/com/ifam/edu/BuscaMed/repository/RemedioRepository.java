package com.ifam.edu.BuscaMed.repository;

import com.ifam.edu.BuscaMed.model.Farmacia;
import com.ifam.edu.BuscaMed.model.Remedio;
import com.ifam.edu.BuscaMed.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RemedioRepository extends JpaRepository<Remedio, Long> {
    @Query("select r from Remedio r where r.nome like %?1%")
    List<Remedio> findByNome(String nome);

    @Query("select r from Remedio r where r.farmacia.id = ?1")
    List<Remedio> findByFarmacia(Long id);

}

