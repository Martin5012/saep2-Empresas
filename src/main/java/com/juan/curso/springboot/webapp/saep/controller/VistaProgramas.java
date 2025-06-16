package com.juan.curso.springboot.webapp.saep.controller;

import com.juan.curso.springboot.webapp.saep.model.Programas;
import com.juan.curso.springboot.webapp.saep.model.Rol;
import com.juan.curso.springboot.webapp.saep.model.Sedes;
import com.juan.curso.springboot.webapp.saep.repository.ProgramasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class VistaProgramas
{
    @Autowired
    private ProgramasRepository programasRepository;

    @GetMapping("/vista/programas")
    public String listar(Model model) {
        model.addAttribute("programas", programasRepository.findAll()); // Envía los productos a la vista
        return "programas"; // Devuelve la plantilla productos.html
    }

    @GetMapping("/vistap/form")
    public String formulario(Model model) {
        model.addAttribute("programas", new Programas()); // Objeto vacío para el formulario
        return "programas_form"; // Vista del formulario para crear
    }

    @PostMapping("/vistap/guardar")
    public String guardar(@ModelAttribute Programas programas, RedirectAttributes ra) {
        programasRepository.save(programas);
        ra.addFlashAttribute("mensaje", "Programa guardada exitosamente");
        return "redirect:/vista/programas"; // Redirige al listado
    }

    @GetMapping("/vistap/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Programas programas = programasRepository.findById(id).orElse(null);
        model.addAttribute("programas", programas);
        return "programas_form"; // Usa la misma vista que para crear
    }

    @PostMapping("/vistap/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        programasRepository.deleteById(id);
        ra.addFlashAttribute("mensaje", "Programa eliminada exitosamente");
        return "redirect:/vista/programas";
    }
}
