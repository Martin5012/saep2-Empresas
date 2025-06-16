package com.juan.curso.springboot.webapp.saep.repository;

import com.juan.curso.springboot.webapp.saep.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EditarPerfilRepository extends JpaRepository<Usuarios, Long> {
}
