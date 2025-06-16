package com.juan.curso.springboot.webapp.saep.controller;

import com.juan.curso.springboot.webapp.saep.model.Rol;
import com.juan.curso.springboot.webapp.saep.model.Sedes;
import com.juan.curso.springboot.webapp.saep.repository.SedesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class VistaSedes
{
    @Autowired
    private SedesRepository sedesRepository;

    @GetMapping("/vista/sedes")
    public String listar(Model model) {
        model.addAttribute("sedes", sedesRepository.findAll()); // Envía los productos a la vista
        return "sedes"; // Devuelve la plantilla productos.html
    }

    @GetMapping("/vistas/form")
    public String formulario(Model model) {
        model.addAttribute("sedes", new Sedes()); // Objeto vacío para el formulario
        return "sedes_form"; // Vista del formulario para crear
    }

    @PostMapping("/vistas/guardar")
    public String guardar(@ModelAttribute Sedes sedes, RedirectAttributes ra) {
        sedesRepository.save(sedes);
        ra.addFlashAttribute("mensaje", "Sede guardada exitosamente");
        return "redirect:/vista/sedes"; // Redirige al listado
    }

    @GetMapping("/vistas/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Sedes sedes = sedesRepository.findById(id).orElse(null);
        model.addAttribute("sedes", sedes);
        return "sedes_form"; // Usa la misma vista que para crear
    }

    @PostMapping("/vistas/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        sedesRepository.deleteById(id);
        ra.addFlashAttribute("mensaje", "Sede eliminada exitosamente");
        return "redirect:/vista/sedes";
    }
}
