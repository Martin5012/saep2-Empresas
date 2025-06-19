package com.juan.curso.springboot.webapp.saep.controller;


import com.juan.curso.springboot.webapp.saep.model.Empresas;
import com.juan.curso.springboot.webapp.saep.model.Rol;
import com.juan.curso.springboot.webapp.saep.model.Sede;
import com.juan.curso.springboot.webapp.saep.repository.SedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class VistaSede {
    @Autowired
    private SedeRepository sedeRepository;

    @GetMapping("/vista/sede")
    public String listar(Model model) {
        model.addAttribute("sede", sedeRepository.findAll()); // Envía los productos a la vista
        return "sede"; // Devuelve la plantilla productos.html
    }

    @GetMapping("/vistas/form")
    public String formulario(Model model) {
        model.addAttribute("sede", new Sede()); // Objeto vacío para el formulario
        return "sede_form"; // Vista del formulario para crear
    }

    @PostMapping("/vistas/guardar")
    public String guardar(@ModelAttribute Sede sede, RedirectAttributes ra) {
        sedeRepository.save(sede);
        ra.addFlashAttribute("mensaje", "Sede guardada exitosamente");
        return "redirect:/vista/sede"; // Redirige al listado
    }

    @GetMapping("/vistas/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Sede sede = sedeRepository.findById(id).orElse(null);
        model.addAttribute("sede", sede);
        return "sede_form"; // Usa la misma vista que para crear
    }

    @PostMapping("/vistas/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        sedeRepository.deleteById(id);
        ra.addFlashAttribute("mensaje", "Sede eliminada exitosamente");
        return "redirect:/vista/sede";
    }

}
