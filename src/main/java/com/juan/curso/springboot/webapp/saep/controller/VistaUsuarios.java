package com.juan.curso.springboot.webapp.saep.controller;

import com.juan.curso.springboot.webapp.saep.model.Usuarios;
import com.juan.curso.springboot.webapp.saep.repository.RolRepository;
import com.juan.curso.springboot.webapp.saep.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class VistaUsuarios
{
    @Autowired
    private UsuariosRepository usuariosRepository;
    @Autowired
    private RolRepository rolRepository;

    @GetMapping("/vista/usuarios")
    public String listar(@RequestParam(name = "buscar", required = false) String buscar,
                         @RequestParam(name = "page", defaultValue = "0") int page,
                         @RequestParam(name = "size", defaultValue = "15") int size,
                         Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Usuarios> usuariosPage;

        if (buscar != null && !buscar.isEmpty()) {
            usuariosPage = usuariosRepository.buscarPorCriterio(buscar, pageable);
        } else {
            usuariosPage = usuariosRepository.findAll(pageable);
        }

        model.addAttribute("usuarios", usuariosPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", usuariosPage.getTotalPages());
        model.addAttribute("totalElements", usuariosPage.getTotalElements());
        model.addAttribute("size", size);
        model.addAttribute("buscar", buscar);
        model.addAttribute("hasNext", usuariosPage.hasNext());
        model.addAttribute("hasPrevious", usuariosPage.hasPrevious());

        return "usuarios";
    }

    @GetMapping("/vistau/form")
    public String formulario(Model model) {
        model.addAttribute("usuarios", new Usuarios()); // Objeto vacío para el formulario
        model.addAttribute("rol", rolRepository.findAll());
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
        model.addAttribute("rol", rolRepository.findAll());
        return "usuarios_form"; // Usa la misma vista que para crear
    }

    @PostMapping("/vistau/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        usuariosRepository.deleteById(id);
        ra.addFlashAttribute("mensaje", "Usuario eliminado exitosamente");
        return "redirect:/vista/usuarios";
    }
}