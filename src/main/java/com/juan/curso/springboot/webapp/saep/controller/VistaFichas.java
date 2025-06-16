package com.juan.curso.springboot.webapp.saep.controller;

import com.juan.curso.springboot.webapp.saep.model.Empresas;
import com.juan.curso.springboot.webapp.saep.model.Fichas;
import com.juan.curso.springboot.webapp.saep.repository.FichasRepository;
import com.juan.curso.springboot.webapp.saep.repository.ProgramasRepository;
import com.juan.curso.springboot.webapp.saep.repository.SedesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller // Este controlador devuelve páginas HTML
public class VistaFichas {
    @Autowired
    private FichasRepository fichasRepository;
    private SedesRepository sedesRepository;
    private ProgramasRepository programasRepository;

    @GetMapping("/vista/fichas")
    public String listar(Model model) {
        model.addAttribute("fichas", fichasRepository.findAll()); // Envía los productos a la vista
        return "fichas"; // Devuelve la plantilla productos.html
    }
    @GetMapping("/vistaf/form")
    public String formulario(Model model) {
        model.addAttribute("fichas", new Fichas()); // Objeto vacío para el formulario
        model.addAttribute("sede", sedesRepository.findAll()); // Objeto vacío para el formulario
        model.addAttribute("programas", programasRepository.findAll()); // Objeto vacío para el formulario
        return "fichas_form"; // Vista del formulario para crear
    }
    @PostMapping("/vistaf/guardar")
    public String guardar(@ModelAttribute Fichas fichas, RedirectAttributes ra) {
        fichasRepository.save(fichas);
        ra.addFlashAttribute("mensaje", "Ficha guardada exitosamente");
        return "redirect:/vista/fichas"; // Redirige al listado
    }
    @GetMapping("/vistaf/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
       Fichas fichas = fichasRepository.findById(id).orElse(null);
        model.addAttribute("fichas", fichas);
        return "fichas_form"; // Usa la misma vista que para crear
    }
    @PostMapping("/vistaf/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        fichasRepository.deleteById(id);
        ra.addFlashAttribute("mensaje", "Ficha eliminada exitosamente");
        return "redirect:/vista/fichas";
    }
}