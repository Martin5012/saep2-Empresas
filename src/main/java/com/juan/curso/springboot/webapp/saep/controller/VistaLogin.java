package com.juan.curso.springboot.webapp.saep.controller;

import com.juan.curso.springboot.webapp.saep.model.Usuarios;
import com.juan.curso.springboot.webapp.saep.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class VistaLogin {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login"; // login.html
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String username,
                                @RequestParam String password,
                                Model model) {
        Usuarios usuario = usuarioRepository.findByNumeroAndClave(username, password);

        String nombreRol = null;
        if (usuario != null) {
            nombreRol = usuario.getRol().getRoles();

            // Redirección según el rol
            switch (nombreRol.trim().toUpperCase()) {
                case "ADMINISTRADOR DEL SISTEMA":
                    return "redirect:/vista/empresas";
                case "INSTRUCTOR":
                    return "redirect:/instructor/inicio";
                case "APRENDIZ":
                    return "redirect:/aprendiz/inicio";
                default:
                    model.addAttribute("error", "Rol no reconocido: " + nombreRol);
                    return "login";
            }
        } else {
           model.addAttribute("error", "Usuario o contraseña incorrectos");

            return "login";
        }
    }
}

