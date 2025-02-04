package com.ifam.edu.BuscaMed.repository;

import com.ifam.edu.BuscaMed.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LoginRepository  extends JpaRepository<Login, Long> {
    Login findByEmail(String email);
}
