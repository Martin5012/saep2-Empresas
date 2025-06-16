package com.juan.curso.springboot.webapp.saep.repository;

import com.juan.curso.springboot.webapp.saep.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EditarPerfilRepository extends JpaRepository<Usuarios, Long> {

    // Sobrescribir findById para cargar la relación Rol
    @Query("SELECT u FROM Usuarios u LEFT JOIN FETCH u.rol WHERE u.id_usuarios = :id")
    Optional<Usuarios> findById(@Param("id") Long id);
}