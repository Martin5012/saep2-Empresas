package com.juan.curso.springboot.webapp.saep.controller;

import com.juan.curso.springboot.webapp.saep.model.Rol;
import com.juan.curso.springboot.webapp.saep.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class VistasRol
{
    @Autowired
    RolRepository rolRepository;

    @GetMapping("/vista/rol")
    public String listar(Model model) {
        model.addAttribute("rol", rolRepository.findAll()); // Envía los productos a la vista
        return "rol"; // Devuelve la plantilla productos.html
    }

    @GetMapping("/vistar/form")
    public String formulario(Model model) {
        model.addAttribute("rol", new Rol()); // Objeto vacío para el formulario
        return "rol_form"; // Vista del formulario para crear
    }

    @PostMapping("/vistar/guardar")
    public String guardar(@ModelAttribute Rol rol, RedirectAttributes ra) {
        rolRepository.save(rol);
        ra.addFlashAttribute("mensaje", "Rol guardada exitosamente");
        return "redirect:/vista/rol"; // Redirige al listado
    }

    @GetMapping("/vistar/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Rol rol = rolRepository.findById(id).orElse(null);
        model.addAttribute("rol", rol);
        return "rol_form"; // Usa la misma vista que para crear
    }

    @PostMapping("/vistar/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        rolRepository.deleteById(id);
        ra.addFlashAttribute("mensaje", "Rol eliminada exitosamente");
        return "redirect:/vista/rol";
    }
}
