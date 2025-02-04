package com.ifam.edu.BuscaMed.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifam.edu.BuscaMed.model.Farmacia;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FarmaciaRepository extends JpaRepository<Farmacia, Long> {
    @Query("select f from Farmacia f where f.nome like %?1%")
    List<Farmacia> findByNome(String nome);

    @Query("select f from Farmacia f where f.nome = ?1")
    Farmacia findByNome_ (String nome);

    @Query("select f from Farmacia f where f.login.id = ?1")
    Farmacia findByLogin(Long id);
}
