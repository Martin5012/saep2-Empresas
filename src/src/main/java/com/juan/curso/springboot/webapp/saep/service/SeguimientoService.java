package com.juan.curso.springboot.webapp.saep.service;

import com.juan.curso.springboot.webapp.saep.model.Seguimiento;
import com.juan.curso.springboot.webapp.saep.repository.SeguimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeguimientoService {

    @Autowired
    private SeguimientoRepository seguimientoRepository;

    public List<Seguimiento> obtenerConNombreUsuario() {
        return seguimientoRepository.findSeguimientosConNombreUsuario().stream()
                .map(obj -> {
                    Seguimiento s = (Seguimiento) obj[0];
                    String nombreCompleto = (String) obj[1];
                    s.setNombreUsuario(nombreCompleto);  // ahora contiene "Juan Pérez"
                    return s;
                }).collect(Collectors.toList());
    }

}
