package com.juan.curso.springboot.webapp.saep.controller;

import com.juan.curso.springboot.webapp.saep.model.Aprendices;
import com.juan.curso.springboot.webapp.saep.model.Modalidad;
import com.juan.curso.springboot.webapp.saep.model.Usuarios;
import com.juan.curso.springboot.webapp.saep.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller // Este controlador devuelve páginas HTML
public class VistaEditarPerfil {
    @Autowired
    private EditarPerfilRepository editarPerfilRepository;

    @Autowired
    private AprendicesRepository aprendicesRepository;

    @Autowired
    private FichasRepository fichasRepository;

    @Autowired
    private ModalidadRepository modalidadRepository;

    @Autowired
    private EmpresasRepository empresasRepository;



    @GetMapping("/vista/editarperfil")
    public String editarPerfil(Model model, HttpSession session) {
        Usuarios usuario = (Usuarios) session.getAttribute("usuarioLogueado");

        // Verificamos si hay un usuario logueado
        if (usuario == null) {
            // Redirige al login si no hay sesión
            return "redirect:/login"; // Cambia esto si tu login está en otra ruta
        }

        model.addAttribute("usuarios", usuario);

        if (usuario.getId_rol() == 1) {
            Aprendices aprendiz = aprendicesRepository.findByIdUsuario(usuario.getId_usuarios());
            model.addAttribute("aprendiz", aprendiz);
            model.addAttribute("listaFichas", fichasRepository.findAll());
            model.addAttribute("listaModalidades", modalidadRepository.findAll());
            model.addAttribute("listaEmpresas", empresasRepository.findAll());
        }


        return "editarperfil";
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
    public String guardarPerfil(@ModelAttribute Usuarios usuarios,
                                @RequestParam(required = false) Long id_aprendices,
                                @RequestParam(required = false, name = "estado_formativo") String estadoFormativo,
                                @RequestParam(required = false) Long id_modalidad,
                                RedirectAttributes ra,
                                HttpSession session) {

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

        // 2. Si es aprendiz, actualizar campos extra
        if (usuarios.getId_rol() == 1 && id_aprendices != null) {
            Aprendices aprendiz = aprendicesRepository.findById(id_aprendices).orElse(null);
            if (aprendiz != null) {
                aprendiz.setEstado(estadoFormativo);

                Modalidad modalidad = new Modalidad();
                modalidad.setId_modalidad(id_modalidad);
                aprendiz.setIdModalidades(modalidad);

                aprendicesRepository.save(aprendiz);
            }
        }


        // 🔄 Refrescar el objeto actualizado en la sesión
        Usuarios actualizado = editarPerfilRepository.findById(usuarios.getId_usuarios()).orElse(null);
        session.setAttribute("usuarioLogueado", actualizado);

        ra.addFlashAttribute("mensaje", "Datos guardados exitosamente");
        return "redirect:/vista/editarperfil"; // Redirige al listado
    }


    // ★ MÉTODO AUXILIAR PARA OBTENER INFORMACIÓN DEL USUARIO LOGUEADO ★
    @ModelAttribute("usuarioLogueado")
    public Usuarios usuarioLogueado(HttpSession session) {
        return (Usuarios) session.getAttribute("usuarioLogueado");
    }
}