package com.juan.curso.springboot.webapp.saep.repository;

import com.juan.curso.springboot.webapp.saep.model.Aprendices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AprendicesRepository extends JpaRepository<Aprendices,Long> {
    @Query("SELECT f FROM Aprendices f " +
            "WHERE LOWER(CONCAT(f.idUsuarios.nombres, ' ', f.idUsuarios.apellidos)) LIKE LOWER(CONCAT('%', :criterio, '%')) " +
            "OR LOWER(f.idUsuarios.numero) LIKE LOWER(CONCAT('%', :criterio, '%')) " +
            "OR LOWER(f.idFichas.codigo) LIKE LOWER(CONCAT('%', :criterio, '%')) " +
            "OR LOWER(CONCAT(f.idInstructor.nombres, ' ', f.idInstructor.apellidos)) LIKE LOWER(CONCAT('%', :criterio, '%')) " +
            "OR LOWER(f.idModalidades.modalidades) LIKE LOWER(CONCAT('%', :criterio, '%')) " +
            "OR LOWER(f.idEmpresas.nombre) LIKE LOWER(CONCAT('%', :criterio, '%')) " +
            "OR LOWER(f.estado) LIKE LOWER(CONCAT('%', :criterio, '%'))")
    List<Aprendices> buscarPorCriterio(@Param("criterio") String criterio);
}
