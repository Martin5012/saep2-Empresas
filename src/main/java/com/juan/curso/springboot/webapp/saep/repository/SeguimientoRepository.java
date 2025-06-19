package com.juan.curso.springboot.webapp.saep.repository;

import com.juan.curso.springboot.webapp.saep.model.Seguimiento;
import org.apache.catalina.LifecycleState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeguimientoRepository extends JpaRepository<Seguimiento, Long> {
    @Query("SELECT s, CONCAT(u.nombres, ' ', u.apellidos) FROM Seguimiento s JOIN Usuarios u ON s.id_usuarios = u.id_usuarios")
    List<Object[]> findSeguimientosConNombreUsuario();

    @Query("SELECT s, CONCAT(u.nombres, ' ', u.apellidos) " +
            "FROM Seguimiento s JOIN Usuarios u ON s.id_usuarios = u.id_usuarios " +
            "WHERE s.tipo_formato = :tipo AND s.id_usuarios = :idUsuario")
    List<Object[]> findSeguimientosConNombreUsuarioByTipoAndUsuario(@Param("tipo") String tipo, @Param("idUsuario") Long idUsuario);

}
