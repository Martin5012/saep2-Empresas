package com.juan.curso.springboot.webapp.saep.controller;

import com.juan.curso.springboot.webapp.saep.model.Usuarios;
import com.juan.curso.springboot.webapp.saep.repository.RolRepository;
import com.juan.curso.springboot.webapp.saep.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class VistaUsuarios
{
    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private RolRepository rolRepository;

    @GetMapping("/vista/usuarios")
    public String listar(Model model) {
        model.addAttribute("usuarios", usuariosRepository.findAll()); // Envía los productos a la vista
        return "usuarios"; // Devuelve la plantilla productos.html
    }
    @GetMapping("/vistau/form")
    public String formulario(Model model) {
        model.addAttribute("usuarios", new Usuarios()); // Objeto vacío para el formulario
        model.addAttribute("rol", rolRepository.findAll()); // Objeto vacío para el formulario
        return "usuarios_form"; // Vista del formulario para crear
    }
    @PostMapping("/vistau/guardar")
    public String guardar(@ModelAttribute Usuarios usuarios, RedirectAttributes ra) {
        usuariosRepository.save(usuarios);
        ra.addFlashAttribute("mensaje", "Usuario guardado exitosamente");
        return "redirect:/vista/usuarios"; // Redirige al listado
    }
    @GetMapping("/vistau/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Usuarios usuarios = usuariosRepository.findById(id).orElse(null);
        model.addAttribute("usuarios", usuarios);
        return "usuarios_form"; // Usa la misma vista que para crear
    }
    @PostMapping("/vistau/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        usuariosRepository.deleteById(id);
        ra.addFlashAttribute("mensaje", "Usuario eliminado exitosamente");
        return "redirect:/vista/usuarios";
    }
}
