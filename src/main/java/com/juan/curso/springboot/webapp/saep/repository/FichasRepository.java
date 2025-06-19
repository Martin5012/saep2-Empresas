package com.juan.curso.springboot.webapp.saep.repository;

import com.juan.curso.springboot.webapp.saep.model.Fichas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FichasRepository extends JpaRepository<Fichas, Long> {

    @Query("SELECT f FROM Fichas f " +
            "WHERE LOWER(f.codigo) LIKE LOWER(CONCAT('%', :criterio, '%')) " +
            "OR LOWER(f.estado) LIKE LOWER(CONCAT('%', :criterio, '%')) " +
            "OR LOWER(f.modalidad) LIKE LOWER(CONCAT('%', :criterio, '%')) " +
            "OR LOWER(f.tipo_oferta) LIKE LOWER(CONCAT('%', :criterio, '%')) " +
            "OR LOWER(f.jornada) LIKE LOWER(CONCAT('%', :criterio, '%')) " +
            "OR LOWER(f.nivel_formacion) LIKE LOWER(CONCAT('%', :criterio, '%')) " +
            "OR LOWER(f.idSedes.nombre) LIKE LOWER(CONCAT('%', :criterio, '%')) " +
            "OR LOWER(f.idProgramas.nombre) LIKE LOWER(CONCAT('%', :criterio, '%'))")
    List<Fichas> buscarPorCriterio(@Param("criterio") String criterio);
}
