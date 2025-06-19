package com.juan.curso.springboot.webapp.saep.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.juan.curso.springboot.webapp.saep.model.Seguimiento;
import com.juan.curso.springboot.webapp.saep.repository.SeguimientoRepository;
import com.juan.curso.springboot.webapp.saep.service.SeguimientoService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class VistaSeguimiento {

    @Autowired
    private SeguimientoRepository seguimientoRepository;

    @Autowired
    private SeguimientoService seguimientoService;

    @GetMapping("/vista/seguimiento")
    public String listarPorTipoYUsuario(@RequestParam(value = "tipo", required = false) String tipo,
                                        HttpSession session,
                                        Model model) {

        Long idUsuario = (Long) session.getAttribute("idUsuarioLogueado");
        if (idUsuario == null) return "redirect:/login";

        List<Seguimiento> filtrados;
        if (tipo != null) {
            filtrados = seguimientoService.obtenerPorTipoYUsuario(tipo, idUsuario);
        } else {
            filtrados = seguimientoService.obtenerConNombreUsuario() // o una versión que también filtre por id
                    .stream()
                    .filter(s -> idUsuario.equals(s.getId_usuarios()))
                    .toList();
        }

        model.addAttribute("seguimiento", filtrados);
        model.addAttribute("tipoActual", tipo);

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
                          HttpSession session,
                          RedirectAttributes ra) {
        Long idUsuario = (Long) session.getAttribute("idUsuarioLogueado");

        if (idUsuario == null) return "redirect:/login";

        try {
            String nombreArchivo = archivo.getOriginalFilename();
            String rutaRelativa = "uploads/" + nombreArchivo;
            String rutaAbsoluta = new File("src/main/resources/static/" + rutaRelativa).getAbsolutePath();

            File destino = new File(rutaAbsoluta);
            archivo.transferTo(destino);

            Seguimiento seguimiento = new Seguimiento();
            seguimiento.setNombre_archivo(nombreArchivo);
            seguimiento.setArchivo(rutaRelativa);
            seguimiento.setTipo_formato("147");
            seguimiento.setObservaciones(observaciones);
            seguimiento.setId_usuarios(idUsuario.intValue());
            seguimiento.setId_aprendices(idUsuario.intValue());
            seguimiento.setFecha(LocalDateTime.now().toString());

            seguimiento.setVal1("No Aprobado");
            seguimiento.setVal2("No Aprobado");
            seguimiento.setVal3("No Aprobado");

            seguimientoRepository.save(seguimiento);
            ra.addFlashAttribute("mensaje", "PDF subido y guardado correctamente.");
        } catch (IOException e) {
            ra.addFlashAttribute("mensaje", "Error al subir el PDF.");
            e.printStackTrace();
        }

        return "redirect:/vista/seguimiento?tipo=147";
    }

    @PostMapping("/vistase/guardar2")
    public String guardar2(@RequestParam("archivoPdf") MultipartFile archivo,
                          @RequestParam("observaciones") String observaciones,
                          HttpSession session,
                          RedirectAttributes ra) {
        Long idUsuario = (Long) session.getAttribute("idUsuarioLogueado");

        if (idUsuario == null) return "redirect:/login";

        try {
            String nombreArchivo = archivo.getOriginalFilename();
            String rutaRelativa = "uploads/" + nombreArchivo;
            String rutaAbsoluta = new File("src/main/resources/static/" + rutaRelativa).getAbsolutePath();

            File destino = new File(rutaAbsoluta);
            archivo.transferTo(destino);

            Seguimiento seguimiento = new Seguimiento();
            seguimiento.setNombre_archivo(nombreArchivo);
            seguimiento.setArchivo(rutaRelativa);
            seguimiento.setTipo_formato("023");
            seguimiento.setObservaciones(observaciones);
            seguimiento.setId_usuarios(idUsuario.intValue());
            seguimiento.setId_aprendices(idUsuario.intValue());
            seguimiento.setFecha(LocalDateTime.now().toString());

            seguimiento.setVal1("No Aprobado");
            seguimiento.setVal2("No Aprobado");
            seguimiento.setVal3("No Aprobado");

            seguimientoRepository.save(seguimiento);
            ra.addFlashAttribute("mensaje", "PDF subido y guardado correctamente.");
        } catch (IOException e) {
            ra.addFlashAttribute("mensaje", "Error al subir el PDF.");
            e.printStackTrace();
        }

        return "redirect:/vista/seguimiento?tipo=023";
    }

    @GetMapping("/vistase/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Seguimiento seguimiento = seguimientoRepository.findById(id).orElse(null);
        model.addAttribute("seguimiento", seguimiento);

        if (seguimiento != null) {
            model.addAttribute("tipoActual", seguimiento.getTipo_formato());
        }

        return "seguimiento_form_editar";
    }

    @PostMapping("/vistase/guardar_editar")
    public String guardarEditar(@RequestParam("id_seguimiento") Long id,
                                @RequestParam("observaciones") String observaciones,
                                @RequestParam("tipo") String tipo,
                                RedirectAttributes ra) {

        Seguimiento seguimiento = seguimientoRepository.findById(id).orElse(null);

        if (seguimiento != null) {
            seguimiento.setObservaciones(observaciones);
            seguimientoRepository.save(seguimiento);
            ra.addFlashAttribute("mensaje", "Observación actualizada correctamente.");
        } else {
            ra.addFlashAttribute("mensaje", "Error: seguimiento no encontrado.");
        }

        return "redirect:/vista/seguimiento?tipo=" + tipo;
    }

    @PostMapping("/vistase/eliminar/{id}")
    public String eliminar(@PathVariable Long id,
                           @RequestParam("tipo") String tipo,
                           RedirectAttributes ra) {

        seguimientoRepository.deleteById(id);
        ra.addFlashAttribute("mensaje", "Formato eliminado exitosamente");

        // 🔁 redirección dinámica
        return "redirect:/vista/seguimiento?tipo=" + tipo;
    }

    @GetMapping("/vistase/pdf")
    public void exportarPDF(@RequestParam(value = "tipo", required = false) String tipo,
                            HttpServletResponse response,
                            HttpSession session) throws Exception {

        Long idUsuario = (Long) session.getAttribute("idUsuarioLogueado");
        if (idUsuario == null) {
            response.sendRedirect("/login");
            return;
        }

        String nombreArchivo = "Seguimiento.pdf";
        Document document = new Document(PageSize.A4, 25, 25, 62, 65);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + nombreArchivo);

        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

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
        Paragraph title = new Paragraph("Listado de Seguimientos", titleFont);
        title.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(title);
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);

        table.addCell("Nombre archivo");
        table.addCell("Tipo Formato");
        table.addCell("Fecha");
        table.addCell("Observaciones");
        table.addCell("Subido por");

        List<Seguimiento> seguimientos;
        if (tipo != null) {
            seguimientos = seguimientoService.obtenerPorTipoYUsuario(tipo, idUsuario);
        } else {
            seguimientos = seguimientoService.obtenerConNombreUsuario()
                    .stream()
                    .filter(s -> idUsuario.equals(s.getId_usuarios()))
                    .toList();
        }

        for (Seguimiento s : seguimientos) {
            table.addCell(s.getNombre_archivo());
            table.addCell(s.getTipo_formato());
            table.addCell(s.getFecha());
            table.addCell(s.getObservaciones());
            table.addCell(s.getNombreUsuario() != null ? s.getNombreUsuario() : "N/A");
        }

        document.add(table);
        document.close();
    }


}



