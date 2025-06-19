package com.juan.curso.springboot.webapp.saep.controller;

import com.juan.curso.springboot.webapp.saep.model.Usuarios;
import com.juan.curso.springboot.webapp.saep.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
                                Model model, HttpSession session) {
        Usuarios usuario = usuarioRepository.findByNumeroAndClave(username, password);

        String nombreRol = null;
        if (usuario != null) {
            nombreRol = usuario.getRol().getRoles();

            // ★ AQUÍ GUARDAMOS EL USUARIO EN LA SESIÓN ★
            session.setAttribute("usuarioLogueado", usuario);
            session.setAttribute("idUsuarioLogueado", usuario.getId_usuarios());

            // Redirección según el rol
            switch (nombreRol.trim().toUpperCase()) {
                case "ADMINISTRADOR DEL SISTEMA":
                    return "redirect:/vista/empresas";
                case "EVALUADOR":
                    return "redirect:/vista/empresas";
                case "APRENDIZ":
                    return "redirect:/vista/aprendiz";
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

