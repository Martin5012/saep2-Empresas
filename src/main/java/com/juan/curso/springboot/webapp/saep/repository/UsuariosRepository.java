package com.juan.curso.springboot.webapp.saep.repository;

import com.juan.curso.springboot.webapp.saep.model.Rol;
import com.juan.curso.springboot.webapp.saep.model.Usuarios;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuariosRepository extends JpaRepository<Usuarios,Long> {

    // Método de búsqueda sin paginación (mantener para compatibilidad)
    @Query("SELECT u FROM Usuarios u WHERE " +
            "LOWER(u.nombres) LIKE LOWER(CONCAT('%', :criterio, '%')) OR " +
            "LOWER(u.apellidos) LIKE LOWER(CONCAT('%', :criterio, '%')) OR " +
            "LOWER(u.idRol.roles) LIKE LOWER(CONCAT('%', :criterio, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :criterio, '%'))")
    List<Usuarios> buscarPorCriterio(@Param("criterio") String criterio);

    // Método de búsqueda con paginación
    @Query("SELECT u FROM Usuarios u WHERE " +
            "LOWER(u.nombres) LIKE LOWER(CONCAT('%', :criterio, '%')) OR " +
            "LOWER(u.apellidos) LIKE LOWER(CONCAT('%', :criterio, '%')) OR " +
            "LOWER(u.idRol.roles) LIKE LOWER(CONCAT('%', :criterio, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :criterio, '%'))")
    Page<Usuarios> buscarPorCriterio(@Param("criterio") String criterio, Pageable pageable);

    List<Usuarios> findByRol(Rol rol);

//    // Buscar usuarios por rol
//    List<Usuarios> findByIdRol(int idRol);

    // Buscar usuarios por rol y estado
//    List<Usuarios> findByIdRolAndEstado(int idRol, String estado);

}