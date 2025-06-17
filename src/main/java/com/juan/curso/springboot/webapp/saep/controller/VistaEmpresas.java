package com.juan.curso.springboot.webapp.saep.controller;

import com.juan.curso.springboot.webapp.saep.model.Empresas;
import com.juan.curso.springboot.webapp.saep.repository.EmpresasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller // Este controlador devuelve páginas HTML
public class VistaEmpresas {
    @Autowired
    private EmpresasRepository empresasRepository;

    @GetMapping("/vista/empresas")
    public String listar(Model model) {
        model.addAttribute("empresas", empresasRepository.findAll()); // Envía los productos a la vista
        return "empresas"; // Devuelve la plantilla productos.html
    }
    @GetMapping("/vista/form")
    public String formulario(Model model) {
        model.addAttribute("empresas", new Empresas()); // Objeto vacío para el formulario
        return "empresas_form"; // Vista del formulario para crear
    }
    @PostMapping("/vista/guardar")
    public String guardar(@ModelAttribute Empresas empresas, RedirectAttributes ra) {
        empresasRepository.save(empresas);
        ra.addFlashAttribute("mensaje", "Empresa guardada exitosamente");
        return "redirect:/vista/empresas"; // Redirige al listado
    }
    @GetMapping("/vista/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Empresas empresas = empresasRepository.findById(id).orElse(null);
        model.addAttribute("empresas", empresas);
        return "empresas_form"; // Usa la misma vista que para crear
    }
    @PostMapping("/vista/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        empresasRepository.deleteById(id);
        ra.addFlashAttribute("mensaje", "Empresa eliminada exitosamente");
        return "redirect:/vista/empresas";
    }
}