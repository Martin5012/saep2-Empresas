package com.juan.curso.springboot.webapp.saep.repository;

import com.juan.curso.springboot.webapp.saep.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {

    // Método original con JOIN FETCH para cargar la relación
    @Query("SELECT u FROM Usuarios u LEFT JOIN FETCH u.rol WHERE u.numero = :numero AND u.clave = :clave")
    Usuarios findByNumeroAndClave(@Param("numero") String numero, @Param("clave") String clave);
}