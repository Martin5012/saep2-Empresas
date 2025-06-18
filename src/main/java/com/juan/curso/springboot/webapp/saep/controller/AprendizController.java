package com.juan.curso.springboot.webapp.saep.controller;

import com.juan.curso.springboot.webapp.saep.model.Aprendiz;
import com.juan.curso.springboot.webapp.saep.repository.AprendizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController // Indica que devuelve JSON y no HTML
@RequestMapping ("/api/aprendiz") // Prefijo común para todas las rutas
public class AprendizController {
    @Autowired
    private AprendizRepository aprendizRepository;


    @GetMapping
    public List<Aprendiz> getAll() {
        return aprendizRepository.findAll(); // Devuelve todos los productos en JSON
    }

    @GetMapping("/{id}")
    public Aprendiz getById(@PathVariable Long id) {
        return aprendizRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Aprendiz create(@RequestBody Aprendiz aprendiz) {
        return aprendizRepository.save(aprendiz); // Guarda un nuevo producto
    }

    @PutMapping("/{id}")
    public Aprendiz update(@PathVariable Long id, @RequestBody Aprendiz aprendiz) {
        aprendiz.setId_aprendices(id);
        return aprendizRepository.save(aprendiz); // Actualiza producto existente
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        aprendizRepository.deleteById(id); // Elimina el producto
    }
}
