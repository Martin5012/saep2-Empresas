package com.juan.curso.springboot.webapp.saep.repository;

import com.juan.curso.springboot.webapp.saep.model.Empresas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmpresasRepository extends JpaRepository<Empresas, Long>
{

    @Query("SELECT f FROM Empresas f " +
            "WHERE LOWER(f.id_empresas) LIKE LOWER(CONCAT('%', :criterio, '%')) " +
            "OR LOWER(f.id_usuarios) LIKE LOWER(CONCAT('%', :criterio, '%')) " +
            "OR LOWER(f.nit) LIKE LOWER(CONCAT('%', :criterio, '%')) " +
            "OR LOWER(f.nombre) LIKE LOWER(CONCAT('%', :criterio, '%')) " +
            "OR LOWER(f.direccion) LIKE LOWER(CONCAT('%', :criterio, '%')) " +
            "OR LOWER(f.area) LIKE LOWER(CONCAT('%', :criterio, '%')) " +
            "OR LOWER(f.contacto) LIKE LOWER(CONCAT('%', :criterio, '%')) " +
            "OR LOWER(f.email) LIKE LOWER(CONCAT('%', :criterio, '%')) " +
            "OR LOWER(f.departamento) LIKE LOWER(CONCAT('%', :criterio, '%')) " +
            "OR LOWER(f.ciudad) LIKE LOWER(CONCAT('%', :criterio, '%')) " +
            "OR LOWER(f.estado) LIKE LOWER(CONCAT('%', :criterio, '%'))")
    List<Empresas> buscarPorCriterio(@Param("criterio") String criterio);

}
