package com.juan.curso.springboot.webapp.saep.repository;

import com.juan.curso.springboot.webapp.saep.model.Programas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramasRepository extends JpaRepository<Programas,Long> {
}
