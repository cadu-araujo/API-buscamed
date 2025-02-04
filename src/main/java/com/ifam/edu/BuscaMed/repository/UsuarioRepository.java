package com.ifam.edu.BuscaMed.repository;

import com.ifam.edu.BuscaMed.model.Farmacia;
import com.ifam.edu.BuscaMed.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("select u from Usuario u where u.nome = ?1")
    Usuario findByNome(String nome);

    @Query("select u from Usuario u where u.login.id = ?1")
    Usuario findByLogin(Long id);
}
