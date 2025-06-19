package com.juan.curso.springboot.webapp.saep.controller;

import com.juan.curso.springboot.webapp.saep.model.Aprendices;
import com.juan.curso.springboot.webapp.saep.model.Aprendiz;
import com.juan.curso.springboot.webapp.saep.model.Rol;
import com.juan.curso.springboot.webapp.saep.model.Usuarios;
import com.juan.curso.springboot.webapp.saep.repository.AprendizRepository;
import com.juan.curso.springboot.webapp.saep.repository.RolRepository;
import com.juan.curso.springboot.webapp.saep.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController // Indica que devuelve JSON y no HTML
@RequestMapping ("/api/aprendiz") // Prefijo común para todas las rutas
public class AprendizController {
    @Autowired
    private AprendizRepository aprendizRepository;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private UsuariosRepository usuariosRepository;


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

    @GetMapping("/formulario-evaluadores")
    public String mostrarFormulario(Model model) {
        Rol coevaluadorRol = rolRepository.findById(2L).orElse(null);
        List<Usuarios> coevaluadores = usuariosRepository.findByRol(coevaluadorRol);
        model.addAttribute("aprendices", new Aprendices());
        model.addAttribute("usuarios", coevaluadores);
        return "formulario-evaluadores";
    }
    @GetMapping("/formulario-aprendices")
    public String mostrarFormularioA(Model model) {
        Rol coevaluadorRol = rolRepository.findById(1L).orElse(null);
        List<Usuarios> coevaluadores = usuariosRepository.findByRol(coevaluadorRol);
        model.addAttribute("aprendices", new Aprendices());
        model.addAttribute("usuarios", coevaluadores);
        return "formulario-aprendices";
    }
}
