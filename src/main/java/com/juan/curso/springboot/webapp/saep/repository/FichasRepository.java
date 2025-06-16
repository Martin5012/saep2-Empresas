package com.juan.curso.springboot.webapp.saep.repository;

import com.juan.curso.springboot.webapp.saep.model.Fichas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FichasRepository extends JpaRepository <Fichas,Long> {
}
