package com.juan.curso.springboot.webapp.saep.controller;

import com.juan.curso.springboot.webapp.saep.model.Modalidad;
import com.juan.curso.springboot.webapp.saep.model.Rol;
import com.juan.curso.springboot.webapp.saep.repository.ModalidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class VistaModalidad
{
    @Autowired
    private ModalidadRepository modalidadRepository;

    @GetMapping("/vista/modalidad")
    public String listar(Model model) {
        model.addAttribute("modalidad", modalidadRepository.findAll()); // Envía los productos a la vista
        return "modalidad"; // Devuelve la plantilla productos.html
    }

    @GetMapping("/vistam/form")
    public String formulario(Model model) {
        model.addAttribute("modalidad", new Modalidad()); // Objeto vacío para el formulario
        return "modalidad_form"; // Vista del formulario para crear
    }

    @PostMapping("/vistam/guardar")
    public String guardar(@ModelAttribute Modalidad modalidad, RedirectAttributes ra) {
        modalidadRepository.save(modalidad);
        ra.addFlashAttribute("mensaje", "Modalidad guardada exitosamente");
        return "redirect:/vista/modalidad"; // Redirige al listado
    }

    @GetMapping("/vistam/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Modalidad modalidad = modalidadRepository.findById(id).orElse(null);
        model.addAttribute("modalidad", modalidad);
        return "modalidad_form"; // Usa la misma vista que para crear
    }

    @PostMapping("/vistam/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        modalidadRepository.deleteById(id);
        ra.addFlashAttribute("mensaje", "Modalidad eliminada exitosamente");
        return "redirect:/vista/modalidad";
    }
}
