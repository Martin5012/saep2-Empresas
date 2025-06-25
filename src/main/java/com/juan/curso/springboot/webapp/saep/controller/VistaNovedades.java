package com.juan.curso.springboot.webapp.saep.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.juan.curso.springboot.webapp.saep.model.*;
import com.juan.curso.springboot.webapp.saep.repository.NovedadesRepository;
import com.juan.curso.springboot.webapp.saep.repository.RolRepository;
import com.juan.curso.springboot.webapp.saep.repository.SedesRepository;
import com.juan.curso.springboot.webapp.saep.repository.UsuariosRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.InputStream;
import java.util.List;


@Controller
public class VistaNovedades {

    @Autowired
    private NovedadesRepository novedadesRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;


    @GetMapping("/vista/novedades")
        public String mostrarBusqueda(@RequestParam(name = "buscar", required = false) String buscar, Model model) {
            List<Novedades> novedades;

            if (buscar != null && !buscar.isEmpty()) {
                novedades = novedadesRepository.buscarPorCriterio(buscar);
            } else {
                novedades = novedadesRepository.findAll();
            }

            model.addAttribute("novedades", novedades); // Para mantener el texto en el input
            model.addAttribute("buscar", buscar); // Para mantener el texto en el input
        return "novedades"; // Devuelve la plantilla productos.html
    }

    @GetMapping("/vistan/form")
    public String formulario(Model model) {
        model.addAttribute("novedades", new Novedades()); // Objeto vacío para el formulario

        // Obtener aprendices (rol ID = 1)
        Rol aprendizRol = rolRepository.findById(1L).orElse(null);
        List<Usuarios> aprendices = usuariosRepository.findByRol(aprendizRol);

        model.addAttribute("aprendices", aprendices);

        return "novedades_form"; // Vista del formulario para crear
    }

    @PostMapping("/vistan/guardar")
    public String guardar(@ModelAttribute Novedades novedades, RedirectAttributes ra) {
        novedadesRepository.save(novedades);
        ra.addFlashAttribute("mensaje", "Novedad guardada exitosamente");
        return "redirect:/vista/novedades"; // Redirige al listado
    }

    @GetMapping("/vistan/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Novedades novedades = novedadesRepository.findById(id).orElse(null);
        // Obtener aprendices (rol ID = 1)
        Rol aprendizRol = rolRepository.findById(1L).orElse(null);
        List<Usuarios> aprendices = usuariosRepository.findByRol(aprendizRol);

        model.addAttribute("aprendices", aprendices);
        model.addAttribute("novedades", novedades);
        return "novedades_form"; // Usa la misma vista que para crear
    }

    @PostMapping("/vistan/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        novedadesRepository.deleteById(id);
        ra.addFlashAttribute("mensaje", "Novedad eliminada exitosamente");
        return "redirect:/vista/novedades";
    }

    @GetMapping("/vistan/pdf")
    public void exportarPDF(HttpServletResponse response) throws Exception {
        String nombreArchivo = "Novedades.pdf";
        Document document = new Document(PageSize.A4, 25, 25, 62, 65);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + nombreArchivo);

        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // Imagen de fondo opcional
        try {
            InputStream imageStream = new ClassPathResource("static/img/plantillaPDF.jpg").getInputStream();
            Image background = Image.getInstance(imageStream.readAllBytes());

            background.setAbsolutePosition(0, 0);
            background.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
            writer.getDirectContentUnder().addImage(background);
        } catch (Exception e) {
            System.out.println("No se pudo cargar la imagen de fondo: " + e.getMessage());
        }

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Font.BOLD, BaseColor.WHITE);
        Paragraph title = new Paragraph("Listado de Novedades", titleFont);
        title.setIndentationLeft(50);
        document.add(title);

        PdfPTable table = new PdfPTable(3); // 3 columnas: Aprendiz, Novedad, Fecha
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);

        table.addCell("Aprendiz");
        table.addCell("Novedad");
        table.addCell("Fecha");

        for (Novedades f : novedadesRepository.findAll()) {
            table.addCell(f.getAprendiz().getNombres() + " " + f.getAprendiz().getApellidos());
            table.addCell(f.getNovedad());
            table.addCell(String.valueOf(f.getFecha()));
        }

        document.add(table); // Agregar tabla completa una vez
        document.close();
    }

}
