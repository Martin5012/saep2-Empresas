package com.juan.curso.springboot.webapp.saep.controller;


import com.juan.curso.springboot.webapp.saep.model.Empresas;
import com.juan.curso.springboot.webapp.saep.model.Seguimiento;
import com.juan.curso.springboot.webapp.saep.repository.EmpresasRepository;
import com.juan.curso.springboot.webapp.saep.repository.SeguimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController // Indica que devuelve JSON y no HTML
@RequestMapping("/api/seguimiento") // Prefijo común para todas las rutas
public class SeguimientoController {
    @Autowired
    private SeguimientoRepository seguimientoRepository;

    @GetMapping
    public List<Seguimiento> getAll() {
        return seguimientoRepository.findAll(); // Devuelve todos los productos en JSON
    }

    @GetMapping("/{id}")
    public Seguimiento getById(@PathVariable Long id) {
        return seguimientoRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Seguimiento create(@RequestBody Seguimiento seguimiento) {
        return seguimientoRepository.save(seguimiento); // Guarda un nuevo producto
    }

    @PutMapping("/{id}")
    public Seguimiento update(@PathVariable Long id, @RequestBody Seguimiento seguimiento) {
        seguimiento.setId_seguimiento(id);
        return seguimientoRepository.save(seguimiento); // Actualiza producto existente
    }



}
