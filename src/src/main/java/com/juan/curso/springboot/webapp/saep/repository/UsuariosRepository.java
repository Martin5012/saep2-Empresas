package com.juan.curso.springboot.webapp.saep.repository;

import com.juan.curso.springboot.webapp.saep.model.Seguimiento;
import com.juan.curso.springboot.webapp.saep.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuariosRepository extends JpaRepository<Usuarios, Long> {
}
