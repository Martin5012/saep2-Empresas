package com.juan.curso.springboot.webapp.saep.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juan.curso.springboot.webapp.saep.model.Empresas;
import com.juan.curso.springboot.webapp.saep.repository.EmpresasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController // Indica que devuelve JSON y no HTML
@RequestMapping ("/api/empresas") // Prefijo común para todas las rutas
public class EmpresasController
{

    @Autowired
    private EmpresasRepository empresasRepository;
    @GetMapping("/vista/empresas")
    public String mostrarBusqueda(@RequestParam(name = "buscar", required = false) String buscar, Model model) {
        List<Empresas> empresas;

        if (buscar != null && !buscar.isEmpty()) {
            // Usa tu repositorio para filtrar (requiere método personalizado)
            empresas = empresasRepository.buscarPorCriterio(buscar);
        } else {
            empresas = empresasRepository.findAll();
        }

        model.addAttribute("empresas", empresas);
        return "empresas";
    }
    @GetMapping
    public List<Empresas> getAll() {
        return empresasRepository.findAll(); // Devuelve todos los productos en JSON
    }

    @GetMapping("/{id}")
    public Empresas getById(@PathVariable Long id) {
        return empresasRepository.findById(id).orElse(null);
    }

    @GetMapping("/vista/empresas/nueva") // o el endpoint que uses
    public String mostrarFormularioEmpresa(Model model) {
        model.addAttribute("empresas", new Empresas());


        return "formularioEmpresa"; // el nombre de tu archivo .html
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

    @GetMapping("/ciudades")
    public List<String> obtenerCiudadesPorDepartamento(@RequestParam String departamento) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = new ClassPathResource("static/data/colombia.json").getInputStream();
            List<Map<String, Object>> data = mapper.readValue(inputStream, new TypeReference<>() {});

            for (Map<String, Object> entry : data) {
                if (entry.get("departamento").equals(departamento)) {
                    return (List<String>) entry.get("ciudades");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }



}