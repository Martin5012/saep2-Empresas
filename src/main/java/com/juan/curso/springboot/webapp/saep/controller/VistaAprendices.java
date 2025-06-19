package com.juan.curso.springboot.webapp.saep.controller;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.juan.curso.springboot.webapp.saep.model.Aprendices;
import com.juan.curso.springboot.webapp.saep.model.Fichas;
import com.juan.curso.springboot.webapp.saep.model.Rol;
import com.juan.curso.springboot.webapp.saep.model.Usuarios;
import com.juan.curso.springboot.webapp.saep.repository.*;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.InputStream;
import java.util.List;

@Controller
public class VistaAprendices
{
    @Autowired
    private AprendicesRepository aprendicesRepository;
    @Autowired
    private UsuariosRepository usuariosRepository;
    @Autowired
    private FichasRepository fichasRepository;
    @Autowired
    private ModalidadRepository modalidadRepository;
    @Autowired
    private EmpresasRepository empresasRepository;
    @Autowired
    private RolRepository rolRepository;


//    @Autowired
//    private UsuariosRepository usuariosRepository;
//
//    @Autowired
//    private FichasRepository fichasRepository;
//
//    @Autowired
//    private ModalidadRepository modalidadRepository;
//
//    @Autowired
//    private EmpresasRepository empresasRepository;

    @GetMapping("/vista/aprendices")
    public String listar(Model model) {
        model.addAttribute("aprendices", aprendicesRepository.findAll()); // Envía los productos a la vista
        return "aprendices"; // Devuelve la plantilla productos.html
    }
    @GetMapping("/vistaa/form")
    public String formulario(Model model) {
        // Obtener evaluadores (rol ID = 2)
        Rol evaluadorRol = rolRepository.findById(2L).orElse(null);
        List<Usuarios> evaluadores = usuariosRepository.findByRol(evaluadorRol);

        // Obtener aprendices (rol ID = 1)
        Rol aprendizRol = rolRepository.findById(1L).orElse(null); // Cambié a 1L para aprendices
        List<Usuarios> aprendices = usuariosRepository.findByRol(aprendizRol);

        model.addAttribute("aprendices", new Aprendices());
        model.addAttribute("evaluadores", evaluadores); // Atributo separado para evaluadores
        model.addAttribute("usuariosAprendices", aprendices); // Atributo separado para aprendices
        model.addAttribute("fichas", fichasRepository.findAll());
        model.addAttribute("modalidad", modalidadRepository.findAll());
        model.addAttribute("empresas", empresasRepository.findAll());
        return "aprendices_form";
    }

    @GetMapping("/vistaa/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Aprendices aprendices = aprendicesRepository.findById(id).orElse(null);

        // Necesitas cargar las listas también para el formulario de edición
        Rol evaluadorRol = rolRepository.findById(2L).orElse(null);
        List<Usuarios> evaluadores = usuariosRepository.findByRol(evaluadorRol);

        Rol aprendizRol = rolRepository.findById(1L).orElse(null);
        List<Usuarios> usuariosAprendices = usuariosRepository.findByRol(aprendizRol);

        model.addAttribute("aprendices", aprendices);
        model.addAttribute("evaluadores", evaluadores);
        model.addAttribute("usuariosAprendices", usuariosAprendices);
        model.addAttribute("fichas", fichasRepository.findAll());
        model.addAttribute("modalidad", modalidadRepository.findAll());
        model.addAttribute("empresas", empresasRepository.findAll());

        return "aprendices_form";
    }
    @PostMapping("/vistaa/guardar")
    public String guardar(@ModelAttribute Aprendices aprendices, RedirectAttributes ra) {
        aprendicesRepository.save(aprendices);
        ra.addFlashAttribute("mensaje", "Aprendiz guardada exitosamente");
        return "redirect:/vista/aprendices"; // Redirige al listado
    }
//    @GetMapping("/vistaa/editar/{id}")
//    public String editar(@PathVariable Long id, Model model) {
//        Aprendices aprendices = aprendicesRepository.findById(id).orElse(null);
//        model.addAttribute("aprendices", aprendices);
//        return "aprendices_form"; // Usa la misma vista que para crear
//    }
    @PostMapping("/vistaa/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        aprendicesRepository.deleteById(id);
        ra.addFlashAttribute("mensaje", "Aprendiz eliminada exitosamente");
        return "redirect:/vista/aprendices";
    }

    @GetMapping("/formulario-evaluadores")
    public String mostrarFormulario(Model model) {
        Rol coevaluadorRol = rolRepository.findById(2L).orElse(null);
        List<Usuarios> coevaluadores = usuariosRepository.findByRol(coevaluadorRol);
        model.addAttribute("usuarios", coevaluadores);
        return "formulario-evaluadores";
    }
    @GetMapping("/formulario-aprendices")
    public String mostrarFormularioA(Model model) {
        Rol coevaluadorRol = rolRepository.findById(1L).orElse(null);
        List<Usuarios> coevaluadores = usuariosRepository.findByRol(coevaluadorRol);
        model.addAttribute("usuarios", coevaluadores);
        return "formulario-aprendices";
    }

    @GetMapping("/vistaa/asignacion")
    public String asignacion(@RequestParam(value = "idAprendiz", required = false) Long idAprendiz,
                             @RequestParam(value = "buscar", required = false) String buscar,
                             Model model) {

        List<Aprendices> aprendices;
        if (buscar != null && !buscar.isEmpty()) {
            aprendices = aprendicesRepository.buscarPorCriterio(buscar);
        } else {
            aprendices = aprendicesRepository.findAll();
        }

        model.addAttribute("aprendices", aprendices);

        List<Usuarios> evaluadores = usuariosRepository.findAll().stream()
                .filter(u -> u.getIdRol() != null
                        && u.getIdRol().getRoles() != null
                        && u.getIdRol().getRoles().equalsIgnoreCase("Evaluador")
                        && u.getEstado() != null
                        && u.getEstado().equalsIgnoreCase("Activo"))
                .toList();

        model.addAttribute("evaluadores", evaluadores);
        model.addAttribute("idAprendizSeleccionado", idAprendiz);

        return "asignacion";
    }




    @PostMapping("/vistaa/asignar-evaluador") //guardar evaluador en tabla de aprendices, ya sea asignar o reasignar
    public String asignarEvaluador(@RequestParam("idAprendiz") Long idAprendiz,
                                   @RequestParam("idInstructor") Integer idInstructor,
                                   RedirectAttributes redirectAttributes) {

        Aprendices aprendiz = aprendicesRepository.findById(idAprendiz).orElse(null);
        if (aprendiz != null) {
            Usuarios instructor = usuariosRepository.findById(Long.valueOf(idInstructor)).orElse(null);
            if (instructor != null) {
                aprendiz.setIdInstructor(instructor);
                aprendicesRepository.save(aprendiz);
                redirectAttributes.addFlashAttribute("mensaje", "Evaluador asignado correctamente.");
            }
        }
        return "redirect:/vistaa/asignacion";
    }

    @PostMapping("/vistaa/eliminar-evaluador")
    public String eliminarEvaluador(@RequestParam("idAprendiz") Long idAprendiz, RedirectAttributes redirectAttributes) {
        Aprendices aprendiz = aprendicesRepository.findById(idAprendiz).orElse(null);

        if (aprendiz != null) {
            aprendiz.setIdInstructor(null); // eliminamos el evaluador
            aprendicesRepository.save(aprendiz);
            redirectAttributes.addFlashAttribute("mensaje", "Evaluador eliminado correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("mensaje", "No se encontró el aprendiz.");
        }

        return "redirect:/vistaa/asignacion";
    }

    @GetMapping("/vistaA/pdf")
    public void exportarPDF(HttpServletResponse response) throws Exception {
        String nombreArchivo = "Aprendices.pdf";
        Document document = new Document(PageSize.A4, 25 , 25, 62, 65);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + nombreArchivo);

        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // Agregar imagen de fondo
        try {
            InputStream imageStream = new ClassPathResource("static/img/plantillaPDF.jpg").getInputStream();
            Image background = Image.getInstance(imageStream.readAllBytes());

            background.setAbsolutePosition(0, 0);
            background.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
            writer.getDirectContentUnder().addImage(background);
        }
        catch (Exception e)
        {
            System.out.println("No se pudo cargar la imagen de fondo: " + e.getMessage());
        }

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Font.BOLD, new BaseColor(255, 255, 255));
        Paragraph title = new Paragraph("Listado de Aprendices", titleFont);
        title.setAlignment(Paragraph.ALIGN_LEFT);

        document.add(title);
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);

        // Encabezados
        table.addCell("ID");
        table.addCell("Usuario");
        table.addCell("Fichas");
        table.addCell("Empresas");
        table.addCell("Instructor");
        table.addCell("Modalidad");
        table.addCell("Estado");

        List<Aprendices> aprendices = aprendicesRepository.findAll();

        for (Aprendices f : aprendices) {
            table.addCell(String.valueOf(f.getId_aprendices()));
            table.addCell(
                    f.getIdUsuarios() != null
                            ? f.getIdUsuarios().getNombres() + " " + f.getIdUsuarios().getApellidos()
                            : "N/A"
            );
            table.addCell(
                    f.getIdFichas() != null
                            ? f.getIdFichas().getCodigo()
                            : "N/A"
            );
            table.addCell(
                    f.getIdEmpresas() != null
                            ? f.getIdEmpresas().getNombre()
                            : "N/A"
            );
            table.addCell(
                    f.getIdInstructor() != null
                            ? f.getIdInstructor().getNombres() + " " + f.getIdInstructor().getApellidos()
                            : "N/A"
            );
            table.addCell(
                    f.getIdModalidades() != null
                            ? f.getIdModalidades().getModalidades()
                            : "N/A"
            );
            table.addCell(
                    f.getEstado() != null
                            ? f.getEstado()
                            : "N/A"
            );
        }

        document.add(table);
        document.close();
    }

    @GetMapping("/vistaA/excel")
    public void exportarExcel(HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Aprendices.xlsx");

        List<Aprendices> aprendices = aprendicesRepository.findAll(); // Reemplaza con tu repositorio

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Aprendices");

        // Crear encabezado
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Usuarios");
        headerRow.createCell(2).setCellValue("Fichas");
        headerRow.createCell(3).setCellValue("Empresas");
        headerRow.createCell(4).setCellValue("Instructor");
        headerRow.createCell(5).setCellValue("Modalidad");
        headerRow.createCell(6).setCellValue("Estado");

        // Agregar datos
        int rowNum = 1;
        for (Aprendices f : aprendices) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(f.getId_aprendices());
            row.createCell(1).setCellValue(f.getIdUsuarios().getNombres() + " " + f.getIdUsuarios().getApellidos());
            row.createCell(2).setCellValue(f.getIdFichas().getCodigo());
            row.createCell(3).setCellValue(f.getIdEmpresas().getNombre());
            row.createCell(4).setCellValue(f.getIdInstructor().getNombres() + " " + f.getIdInstructor().getApellidos());
            row.createCell(5).setCellValue(f.getIdModalidades().getModalidades());
            row.createCell(6).setCellValue(f.getEstado());
        }

        // Autoajustar columnas
        for (int i = 0; i < 7; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
