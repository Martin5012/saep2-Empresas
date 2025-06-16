package com.juan.curso.springboot.webapp.saep.controller;

import com.juan.curso.springboot.webapp.saep.model.Empresas;
import com.juan.curso.springboot.webapp.saep.model.Usuarios;
import com.juan.curso.springboot.webapp.saep.repository.EditarPerfilRepository;
import com.juan.curso.springboot.webapp.saep.repository.EmpresasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller // Este controlador devuelve páginas HTML
public class VistaEditarPerfil {
    @Autowired
    private EditarPerfilRepository editarPerfilRepository;

    @GetMapping("/vista/editarperfil")
    public String editarPerfil(Model model) {
        Long idUsuarioPorDefecto = 6L; // ← Aquí puedes cambiar el ID si quieres otro

        Optional<Usuarios> usuario = editarPerfilRepository.findById(idUsuarioPorDefecto);

        if (usuario.isPresent()) {
            model.addAttribute("usuarios", usuario.get());
        } else {
            // Si no existe, se carga un objeto vacío para evitar errores
            model.addAttribute("usuarios", new Usuarios());
        }

        //model.addAttribute("usuarios", editarPerfilRepository.findAll()); // Envía los productos a la vista
        return "editarperfil"; // Devuelve la plantilla productos.html
    }

    @GetMapping("/vistaEditar/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Usuarios usuarios = editarPerfilRepository.findById(id).orElse(null);
        model.addAttribute("usuarios", usuarios);
        return "editarperfil"; // Usa la misma vista que para crear
    }

    @PostMapping("/vistaEditar/guardar")
    public String guardarPerfil(@ModelAttribute Usuarios usuarios, RedirectAttributes ra) {
        editarPerfilRepository.save(usuarios);
        ra.addFlashAttribute("mensaje", "Datos guardados exitosamente");
        return "redirect:/vista/editarperfil"; // Redirige al listado
    }
}