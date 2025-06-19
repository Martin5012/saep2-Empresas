package com.juan.curso.springboot.webapp.saep.controller;

import com.juan.curso.springboot.webapp.saep.model.Aprendices;
import com.juan.curso.springboot.webapp.saep.model.Fichas;
import com.juan.curso.springboot.webapp.saep.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class VistaAprendices
{
    @Autowired
    private AprendicesRepository aprendicesRepository;
    @Autowired
    private UsuariosRepository usuariosRepository;
    @Autowired
    private FichasRepository fichasRepository;
    @Autowired
    private ModalidadRepository modalidadRepository;
    @Autowired
    private EmpresasRepository empresasRepository;

    @GetMapping("/vista/aprendices")
    public String listar(Model model) {
        model.addAttribute("aprendices", aprendicesRepository.findAll()); // Envía los productos a la vista
        return "aprendices"; // Devuelve la plantilla productos.html
    }
    @GetMapping("/vistaa/form")
    public String formulario(Model model) {
        model.addAttribute("aprendices", new Aprendices());
        model.addAttribute("usuarios", usuariosRepository.findAll()); // Solo instructores
        model.addAttribute("fichas", fichasRepository.findAll());
        model.addAttribute("modalidad", modalidadRepository.findAll());
        model.addAttribute("empresas", empresasRepository.findAll());
        return "aprendices_form";
    }
    @PostMapping("/vistaa/guardar")
    public String guardar(@ModelAttribute Aprendices aprendices, RedirectAttributes ra) {
        aprendicesRepository.save(aprendices);
        ra.addFlashAttribute("mensaje", "Aprendiz guardada exitosamente");
        return "redirect:/vista/aprendices"; // Redirige al listado
    }
    @GetMapping("/vistaa/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Aprendices aprendices = aprendicesRepository.findById(id).orElse(null);
        model.addAttribute("usuarios", usuariosRepository.findAll()); // Objeto vacío para el formulario
        model.addAttribute("fichas", fichasRepository.findAll()); // Objeto vacío para el formulario
        model.addAttribute("modalidad", modalidadRepository.findAll()); // Objeto vacío para el formulario
        model.addAttribute("empresas", empresasRepository.findAll()); // Objeto vacío para el formulario
        model.addAttribute("aprendices", aprendices);
        return "aprendices_form"; // Usa la misma vista que para crear
    }
    @PostMapping("/vistaa/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        aprendicesRepository.deleteById(id);
        ra.addFlashAttribute("mensaje", "Aprendiz eliminada exitosamente");
        return "redirect:/vista/aprendices";
    }
}
