package com.juan.curso.springboot.webapp.saep.repository;

import com.juan.curso.springboot.webapp.saep.model.Fichas;
import com.juan.curso.springboot.webapp.saep.model.Novedades;
import com.juan.curso.springboot.webapp.saep.model.Sedes;
import com.juan.curso.springboot.webapp.saep.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NovedadesRepository extends JpaRepository<Novedades,Long> {

    @Query("SELECT f FROM Novedades f " +
            "WHERE LOWER(CONCAT(f.aprendiz.nombres, ' ', f.aprendiz.apellidos)) LIKE LOWER(CONCAT('%', :criterio, '%')) " +
            "OR STR(f.fecha) LIKE LOWER(CONCAT('%', :criterio, '%')) ")
    List<Novedades> buscarPorCriterio(@Param("criterio") String criterio);
}
