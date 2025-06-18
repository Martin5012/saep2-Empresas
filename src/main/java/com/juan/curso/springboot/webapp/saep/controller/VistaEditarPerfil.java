package com.juan.curso.springboot.webapp.saep.controller;

import com.juan.curso.springboot.webapp.saep.model.Usuarios;
import com.juan.curso.springboot.webapp.saep.repository.EditarPerfilRepository;
import jakarta.servlet.http.HttpSession;
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
    public String editarPerfil(Model model, HttpSession session) {
        Long idUsuarioLogueado = (Long) session.getAttribute("idUsuarioLogueado");

        if (idUsuarioLogueado == null) {
            // Si no hay usuario en sesión, redirigir al login
            return "redirect:/login";
        }

        Optional<Usuarios> usuario = editarPerfilRepository.findById(idUsuarioLogueado);

        if (usuario.isPresent()) {
            model.addAttribute("usuarios", usuario.get());
        } else {
            // Si no existe, se carga un objeto vacío para evitar errores
            return "redirect:/login";
        }

        //model.addAttribute("usuarios", editarPerfilRepository.findAll()); // Envía los productos a la vista
        return "editarperfil"; // Devuelve la plantilla productos.html
    }

    @GetMapping("/vistaEditar/editar/{id}")
    public String editar(@PathVariable Long id, Model model, HttpSession session) {
        // ★ VERIFICAMOS QUE EL USUARIO SOLO PUEDA EDITAR SU PROPIO PERFIL ★
        Long idUsuarioLogueado = (Long) session.getAttribute("idUsuarioLogueado");

        if (idUsuarioLogueado == null) {
            return "redirect:/login";
        }
        // Solo permitir editar si el ID coincide con el usuario logueado
        if (!idUsuarioLogueado.equals(id)) {
            return "redirect:/vista/editarperfil"; // Redirigir a su propio perfil
        }

        Usuarios usuarios = editarPerfilRepository.findById(id).orElse(null);
        if (usuarios == null) {
            return "redirect:/login";
        }
        model.addAttribute("usuarios", usuarios);
        return "editarperfil"; // Usa la misma vista que para crear
    }

    @PostMapping("/vistaEditar/guardar")
    public String guardarPerfil(@ModelAttribute Usuarios usuarios, RedirectAttributes ra, HttpSession session) {

        // ★ VERIFICAMOS QUE EL USUARIO SOLO PUEDA GUARDAR SU PROPIO PERFIL ★
        Long idUsuarioLogueado = (Long) session.getAttribute("idUsuarioLogueado");

        if (idUsuarioLogueado == null) {
            return "redirect:/login";
        }

        // Solo permitir guardar si el ID coincide con el usuario logueado
        if (!idUsuarioLogueado.equals(usuarios.getId_usuarios())) {
            ra.addFlashAttribute("error", "No tienes permisos para modificar este perfil");
            return "redirect:/vista/editarperfil";
        }
        editarPerfilRepository.save(usuarios);
        ra.addFlashAttribute("mensaje", "Datos guardados exitosamente");
        return "redirect:/vista/editarperfil"; // Redirige al listado
    }


    // ★ MÉTODO AUXILIAR PARA OBTENER INFORMACIÓN DEL USUARIO LOGUEADO ★
    @ModelAttribute("usuarioLogueado")
    public Usuarios usuarioLogueado(HttpSession session) {
        return (Usuarios) session.getAttribute("usuarioLogueado");
    }
}