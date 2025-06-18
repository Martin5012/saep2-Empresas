package com.juan.curso.springboot.webapp.saep.controller;

import com.juan.curso.springboot.webapp.saep.model.Fichas;
import com.juan.curso.springboot.webapp.saep.repository.FichasRepository;
import com.juan.curso.springboot.webapp.saep.repository.ProgramasRepository;
import com.juan.curso.springboot.webapp.saep.repository.SedesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping // Opcional: Agregar RequestMapping si es necesario
public class VistaFichas {
    @Autowired
    private FichasRepository fichasRepository;

    @Autowired // Agregar anotación @Autowired
    private SedesRepository sedesRepository;

    @Autowired // Agregar anotación @Autowired
    private ProgramasRepository programasRepository;

    @GetMapping("/vista/fichas")
    public String listar(Model model) {
        System.out.println("Accediendo a /vista/fichas"); // Debug
        model.addAttribute("fichas", fichasRepository.findAll());
        return "fichas";
    }

    @GetMapping("/vistaf/form")
    public String formulario(Model model) {
        model.addAttribute("fichas", new Fichas());
        model.addAttribute("sedes", sedesRepository.findAll()); // Cambiado de "sede" a "sedes"
        model.addAttribute("programas", programasRepository.findAll());
        return "fichas_form";
    }

    @PostMapping("/vistaf/guardar")
    public String guardar(@ModelAttribute Fichas fichas, RedirectAttributes ra) {
        fichasRepository.save(fichas);
        ra.addFlashAttribute("mensaje", "Ficha guardada exitosamente");
        return "redirect:/vista/fichas";
    }

    @GetMapping("/vistaf/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Fichas fichas = fichasRepository.findById(id).orElse(null);
        model.addAttribute("fichas", fichas);
        model.addAttribute("sedes", sedesRepository.findAll()); // Agregar sedes disponibles
        model.addAttribute("programas", programasRepository.findAll()); // Agregar programas disponibles
        return "fichas_form";
    }

    @PostMapping("/vistaf/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        fichasRepository.deleteById(id);
        ra.addFlashAttribute("mensaje", "Ficha eliminada exitosamente");
        return "redirect:/vista/fichas";
    }
}