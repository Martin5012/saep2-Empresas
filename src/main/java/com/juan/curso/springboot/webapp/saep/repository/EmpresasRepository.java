package com.juan.curso.springboot.webapp.saep.repository;

import com.juan.curso.springboot.webapp.saep.model.Empresas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresasRepository extends JpaRepository<Empresas, Long>
{

}
