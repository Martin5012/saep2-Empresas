package com.juan.curso.springboot.webapp.saep.repository;

import com.juan.curso.springboot.webapp.saep.model.Aprendiz;
import com.juan.curso.springboot.webapp.saep.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AprendizRepository extends JpaRepository<Aprendiz, Long> {

    // Buscar aprendiz por usuario
    Optional<Aprendiz> findByUsuario(Usuarios usuario);

    // Buscar aprendiz por ID de usuario
    @Query("SELECT a FROM Aprendiz a WHERE a.usuario.id_usuarios = :idUsuario")
    Optional<Aprendiz> findByUsuarioId(@Param("idUsuario") Long idUsuario);

    // Buscar aprendiz por usuario con todas las relaciones cargadas
    @Query("SELECT a FROM Aprendiz a " +
            "LEFT JOIN FETCH a.usuario " +
            "LEFT JOIN FETCH a.ficha f " +
            "LEFT JOIN FETCH f.idProgramas " +
            "LEFT JOIN FETCH f.idSedes " +
            "LEFT JOIN FETCH a.empresa " +
            "LEFT JOIN FETCH a.modalidad " +
            "WHERE a.usuario.id_usuarios = :idUsuario")
    Optional<Aprendiz> findByUsuarioIdWithDetails(@Param("idUsuario") Long idUsuario);

    // Buscar aprendices por estado
    @Query("SELECT a FROM Aprendiz a WHERE a.estado = :estado")
    java.util.List<Aprendiz> findByEstado(@Param("estado") String estado);

    // Buscar aprendices por ficha
    @Query("SELECT a FROM Aprendiz a WHERE a.ficha.id_fichas = :idFicha")
    java.util.List<Aprendiz> findByFichaId(@Param("idFicha") Long idFicha);
}