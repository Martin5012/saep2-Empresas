package com.juan.curso.springboot.webapp.saep.repository;

import com.juan.curso.springboot.webapp.saep.model.Empresas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmpresasRepository extends JpaRepository<Empresas, Long> {

    @Query("SELECT e FROM Empresas e " +
            "WHERE LOWER(e.nit) LIKE LOWER(CONCAT('%', :criterio, '%')) " +
            "OR LOWER(e.nombre) LIKE LOWER(CONCAT('%', :criterio, '%')) " +
            "OR LOWER(e.direccion) LIKE LOWER(CONCAT('%', :criterio, '%')) " +
            "OR LOWER(e.area) LIKE LOWER(CONCAT('%', :criterio, '%')) " +
            "OR LOWER(e.contacto) LIKE LOWER(CONCAT('%', :criterio, '%')) " +
            "OR LOWER(e.email) LIKE LOWER(CONCAT('%', :criterio, '%')) " +
            "OR LOWER(e.departamento) LIKE LOWER(CONCAT('%', :criterio, '%')) " +
            "OR LOWER(e.ciudad) LIKE LOWER(CONCAT('%', :criterio, '%')) " +
            "OR LOWER(e.estado) LIKE LOWER(CONCAT('%', :criterio, '%'))")
    List<Empresas> buscarPorCriterio(@Param("criterio") String criterio);
}