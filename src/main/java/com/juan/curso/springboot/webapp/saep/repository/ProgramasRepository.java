package com.juan.curso.springboot.webapp.saep.repository;

import com.juan.curso.springboot.webapp.saep.model.Programas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProgramasRepository extends JpaRepository<Programas,Long> {
    @Query("SELECT p FROM Programas p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :criterio, '%'))")
    List<Programas> buscarPorCriterio(@Param("criterio") String criterio);

}
