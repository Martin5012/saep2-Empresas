package com.juan.curso.springboot.webapp.saep.controller;

import com.juan.curso.springboot.webapp.saep.model.Empresas;
import com.juan.curso.springboot.webapp.saep.repository.EmpresasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Indica que devuelve JSON y no HTML
@RequestMapping ("/api/empresas") // Prefijo común para todas las rutas
public class EmpresasController
{
    @Autowired
    private EmpresasRepository empresasRepository;

    @GetMapping
    public List<Empresas> getAll() {
        return empresasRepository.findAll(); // Devuelve todos los productos en JSON
    }

    @GetMapping("/{id}")
    public Empresas getById(@PathVariable Long id) {
        return empresasRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Empresas create(@RequestBody Empresas empresas) {
        return empresasRepository.save(empresas); // Guarda un nuevo producto
    }

    @PutMapping("/{id}")
    public Empresas update(@PathVariable Long id, @RequestBody Empresas empresas) {
        empresas.setId_empresas(id);
        return empresasRepository.save(empresas); // Actualiza producto existente
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        empresasRepository.deleteById(id); // Elimina el producto
    }
}
