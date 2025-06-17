package com.juan.curso.springboot.webapp.saep.controller;

import com.juan.curso.springboot.webapp.saep.model.Aprendiz;
import com.juan.curso.springboot.webapp.saep.repository.AprendizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller // Este controlador devuelve páginas HTML
@RequestMapping("/vista/aprendiz")
public class VistaAprendiz {
    @Autowired
    private AprendizRepository aprendizRepository;

    @GetMapping
    public String mostrarVistaAprendiz(Model model) {
        // Obtener el aprendiz actual (deberías obtenerlo por usuario autenticado)
        Aprendiz aprendiz = aprendizRepository.findById(1L).orElse(new Aprendiz()); // Ejemplo con ID 1

        // Calcular progreso si no está definido
        if(aprendiz.getProgreso() == null) {
            aprendiz.setProgreso(aprendiz.calcularProgreso());
        }

        model.addAttribute("aprendiz", aprendiz);
        return "aprendiz";
    }
    @GetMapping("/vista/aprendiz")
    public String listar(Model model) {
        model.addAttribute("aprendiz", aprendizRepository.findAll()); // Envía los productos a la vista
        return "aprendiz"; // Devuelve la plantilla productos.html
    }
    @GetMapping("/vistaA/form")
    public String formulario(Model model) {
        model.addAttribute("aprendiz", new Aprendiz()); // Objeto vacío para el formulario
        return "aprendiz_form"; // Vista del formulario para crear
    }
    @PostMapping("/vistaA/guardar")
    public String guardar(@ModelAttribute Aprendiz aprendiz, RedirectAttributes ra) {
        aprendizRepository.save(aprendiz);
        ra.addFlashAttribute("mensaje", "Aprendiz guardada exitosamente");
        return "redirect:/vista/aprendiz"; // Redirige al listado
    }
    @GetMapping("/vistaA/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Aprendiz aprendiz = aprendizRepository.findById(id).orElse(null);
        model.addAttribute("aprendiz", aprendiz);
        return "aprendiz_form"; // Usa la misma vista que para crear
    }
    @PostMapping("/vistaA/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        aprendizRepository.deleteById(id);
        ra.addFlashAttribute("mensaje", "Aprendiz eliminado exitosamente");
        return "redirect:/vista/aprendiz";
    }
}
