package com.juan.curso.springboot.webapp.saep.controller;

import com.juan.curso.springboot.webapp.saep.model.Seguimiento;
import com.juan.curso.springboot.webapp.saep.repository.SeguimientoRepository;
import com.juan.curso.springboot.webapp.saep.service.SeguimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@Controller
public class VistaSeguimiento {

    @Autowired
    private SeguimientoRepository seguimientoRepository;

    @Autowired
    private SeguimientoService seguimientoService;

    @GetMapping("/vista/seguimiento")
    public String listar(Model model) {
        model.addAttribute("seguimiento", seguimientoService.obtenerConNombreUsuario());
        return "seguimiento";
    }

    @GetMapping("/vistase/form")
    public String formulario(Model model) {
        model.addAttribute("seguimiento", new Seguimiento()); // Objeto vacío para el formulario
        return "seguimiento_form"; // Vista del formulario para crear
    }

    @GetMapping("/vistase/form2")
    public String formulario2(Model model) {
        model.addAttribute("seguimiento", new Seguimiento()); // Objeto vacío para el formulario
        return "seguimiento_form2"; // Vista del formulario para crear
    }


    @PostMapping("/vistase/guardar")
    public String guardar(@RequestParam("archivoPdf") MultipartFile archivo,
                          @RequestParam("observaciones") String observaciones,
                          @RequestParam("id_usuarios") Integer idUsuarios,
                          RedirectAttributes ra) {

        try {
            // 1. Ruta donde se guardará el archivo en el servidor
            String nombreArchivo = archivo.getOriginalFilename();
            String rutaRelativa = "uploads/" + nombreArchivo;
            String rutaAbsoluta = new File("src/main/resources/static/" + rutaRelativa).getAbsolutePath();

            // 2. Guardar el archivo en esa ruta
            File destino = new File(rutaAbsoluta);
            archivo.transferTo(destino);

            // 3. Crear el seguimiento con la ruta del archivo
            Seguimiento seguimiento = new Seguimiento();
            seguimiento.setNombre_archivo(nombreArchivo);
            seguimiento.setArchivo(rutaRelativa); // << solo la ruta relativa
            seguimiento.setTipo_formato("147");
            seguimiento.setObservaciones(observaciones);
            seguimiento.setId_usuarios(idUsuarios);
            seguimiento.setId_aprendices(idUsuarios);
            seguimiento.setFecha(LocalDateTime.now().toString()); // opcional

            // valores por defecto si no usas los val1, val2, val3 aún
            seguimiento.setVal1("No Aprobado");
            seguimiento.setVal2("No Aprobado");
            seguimiento.setVal3("No Aprobado");

            seguimientoRepository.save(seguimiento);
            ra.addFlashAttribute("mensaje", "PDF subido y guardado correctamente.");
        } catch (IOException e) {
            ra.addFlashAttribute("mensaje", "Error al subir el PDF.");
            e.printStackTrace();
        }

        return "redirect:/vista/seguimiento";
    }

    @PostMapping("/vistase/guardar2")
    public String guardar2(@RequestParam("archivoPdf") MultipartFile archivo,
                          @RequestParam("observaciones") String observaciones,
                          @RequestParam("id_usuarios") Integer idUsuarios,
                          RedirectAttributes ra) {

        try {
            // 1. Ruta donde se guardará el archivo en el servidor
            String nombreArchivo = archivo.getOriginalFilename();
            String rutaRelativa = "uploads/" + nombreArchivo;
            String rutaAbsoluta = new File("src/main/resources/static/" + rutaRelativa).getAbsolutePath();

            // 2. Guardar el archivo en esa ruta
            File destino = new File(rutaAbsoluta);
            archivo.transferTo(destino);

            // 3. Crear el seguimiento con la ruta del archivo
            Seguimiento seguimiento = new Seguimiento();
            seguimiento.setNombre_archivo(nombreArchivo);
            seguimiento.setArchivo(rutaRelativa); // << solo la ruta relativa
            seguimiento.setTipo_formato("147");
            seguimiento.setObservaciones(observaciones);
            seguimiento.setId_usuarios(idUsuarios);
            seguimiento.setId_aprendices(idUsuarios);
            seguimiento.setFecha(LocalDateTime.now().toString()); // opcional

            // valores por defecto si no usas los val1, val2, val3 aún
            seguimiento.setVal1("No Aprobado");
            seguimiento.setVal2("No Aprobado");
            seguimiento.setVal3("No Aprobado");

            seguimientoRepository.save(seguimiento);
            ra.addFlashAttribute("mensaje", "PDF subido y guardado correctamente.");
        } catch (IOException e) {
            ra.addFlashAttribute("mensaje", "Error al subir el PDF.");
            e.printStackTrace();
        }

        return "redirect:/vista/seguimiento";
    }

    @GetMapping("/vistase/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Seguimiento seguimiento = seguimientoRepository.findById(id).orElse(null);
        model.addAttribute("seguimiento", seguimiento);
        return "seguimiento_form_editar"; // Usa la misma vista que para crear
    }

    @PostMapping("/vistase/guardar_editar")
    public String guardarEditar(@ModelAttribute Seguimiento seguimiento,
                                @RequestParam(value = "archivoPdf", required = false) MultipartFile archivo,
                                RedirectAttributes ra) {

        try {
            if (archivo != null && !archivo.isEmpty()) {
                String nombreArchivo = archivo.getOriginalFilename();
                String rutaRelativa = "uploads/" + nombreArchivo;
                String rutaAbsoluta = new File("src/main/resources/static/" + rutaRelativa).getAbsolutePath();

                File destino = new File(rutaAbsoluta);
                archivo.transferTo(destino);

                seguimiento.setNombre_archivo(nombreArchivo);
                seguimiento.setArchivo(rutaRelativa);
            }

            seguimientoRepository.save(seguimiento);
            ra.addFlashAttribute("mensaje", "Seguimiento actualizado correctamente.");
        } catch (IOException e) {
            ra.addFlashAttribute("mensaje", "Error al subir el nuevo archivo.");
            e.printStackTrace();
        }

        return "redirect:/vista/seguimiento";
    }

    @PostMapping("/vistase/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        seguimientoRepository.deleteById(id);
        ra.addFlashAttribute("mensaje", "Formato eliminado exitosamente");
        return "redirect:/vista/seguimiento";
    }


}



