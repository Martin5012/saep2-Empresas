package com.juan.curso.springboot.webapp.saep.controller;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.util.List;
import java.io.InputStream;
import com.juan.curso.springboot.webapp.saep.model.Empresas;
import com.juan.curso.springboot.webapp.saep.model.Rol;
import com.juan.curso.springboot.webapp.saep.model.Usuarios;
import com.juan.curso.springboot.webapp.saep.repository.EmpresasRepository;
import com.juan.curso.springboot.webapp.saep.repository.RolRepository;
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
import com.juan.curso.springboot.webapp.saep.repository.UsuariosRepository;


@Controller // Este controlador devuelve páginas HTML
public class VistaEmpresas {
    @Autowired
    private EmpresasRepository empresasRepository;
    @Autowired
    private UsuariosRepository usuariosRepository;
    @Autowired
    private RolRepository rolRepository;

    @GetMapping("/vista/empresas")
    public String listar(@RequestParam(required = false) String buscar, Model model) {
        List<Empresas> empresas;

        if (buscar != null && !buscar.trim().isEmpty()) {
            // Buscar empresas por todos los campos
            empresas = empresasRepository.findByAllFields(buscar);
        } else {
            // Si no hay búsqueda, mostrar todas
            empresas = empresasRepository.findAll();
        }

        model.addAttribute("empresas", empresas);
        return "empresas";
    }


    @GetMapping("/vista/form")
    public String formulario(Model model) {
        Rol coevaluadorRol = rolRepository.findById(3L).orElse(null);
        List<Usuarios> coevaluadores = usuariosRepository.findByRol(coevaluadorRol);

        model.addAttribute("empresas", new Empresas());
        model.addAttribute("usuarios", coevaluadores); // Envía los coevaluadores al combo

        return "empresas_form";
    }
    @PostMapping("/vista/guardar")
    public String guardar(@ModelAttribute Empresas empresas, RedirectAttributes ra) {
        empresasRepository.save(empresas);
        ra.addFlashAttribute("mensaje", "Empresa guardada exitosamente");
        return "redirect:/vista/empresas"; // Redirige al listado
    }

    @GetMapping("/vista/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Empresas empresas = empresasRepository.findById(id).orElse(null);

        Rol coevaluadorRol = rolRepository.findById(3L).orElse(null);
        List<Usuarios> coevaluadores = usuariosRepository.findByRol(coevaluadorRol);

        model.addAttribute("empresas", empresas);
        model.addAttribute("usuarios", coevaluadores);

        return "empresas_form";
    }
    @PostMapping("/vista/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        empresasRepository.deleteById(id);
        ra.addFlashAttribute("mensaje", "Empresa eliminada exitosamente");
        return "redirect:/vista/empresas";
    }

    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        Rol coevaluadorRol = rolRepository.findById(3L).orElse(null);
        List<Usuarios> coevaluadores = usuariosRepository.findByRol(coevaluadorRol);
        model.addAttribute("usuarios", coevaluadores);
        return "formulario";
    }

    @GetMapping("/empresas/pdf")
    public void exportarEmpresasPDF(HttpServletResponse response) throws Exception {
        String nombreArchivo = "Empresas.pdf";
        Document document = new Document(PageSize.A4, 25, 25, 62, 65);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + nombreArchivo);

        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // Imagen de fondo
        try {
            InputStream imageStream = new ClassPathResource("static/img/plantillaPDF.jpg").getInputStream();
            Image background = Image.getInstance(imageStream.readAllBytes());
            background.setAbsolutePosition(0, 0);
            background.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
            writer.getDirectContentUnder().addImage(background);
        } catch (Exception e) {
            System.out.println("No se pudo cargar la imagen de fondo: " + e.getMessage());
        }

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Font.BOLD, new BaseColor(255, 255, 255));
        Paragraph title = new Paragraph("Listado De Empresas", titleFont);
        title.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(title);
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(9); // 9 columnas visibles
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);

        // Encabezados
        table.addCell("NIT");
        table.addCell("Nombre");
        table.addCell("Dirección");
        table.addCell("Área");
        table.addCell("Contacto");
        table.addCell("Correo");
        table.addCell("Departamento");
        table.addCell("Ciudad");
        table.addCell("Estado");

        List<Empresas> empresas = empresasRepository.findAll();

        for (Empresas e : empresas) {
            table.addCell(e.getNit() != null ? e.getNit() : "N/A");
            table.addCell(e.getNombre() != null ? e.getNombre() : "N/A");
            table.addCell(e.getDireccion() != null ? e.getDireccion() : "N/A");
            table.addCell(e.getArea() != null ? e.getArea() : "N/A");
            table.addCell(e.getContacto() != null ? e.getContacto() : "N/A");
            table.addCell(e.getEmail() != null ? e.getEmail() : "N/A");
            table.addCell(e.getDepartamento() != null ? e.getDepartamento() : "N/A");
            table.addCell(e.getCiudad() != null ? e.getCiudad() : "N/A");
            table.addCell(e.getEstado() != null ? e.getEstado() : "N/A");
        }

        document.add(table);
        document.close();
    }


    @GetMapping("/empresas/excel")
    public void exportarEmpresasExcel(HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Empresas.xlsx");

        List<Empresas> empresas = empresasRepository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Empresas");

        // Encabezado
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("NIT");
        headerRow.createCell(1).setCellValue("Nombre");
        headerRow.createCell(2).setCellValue("Dirección");
        headerRow.createCell(3).setCellValue("Área");
        headerRow.createCell(4).setCellValue("Contacto");
        headerRow.createCell(5).setCellValue("Correo");
        headerRow.createCell(6).setCellValue("Departamento");
        headerRow.createCell(7).setCellValue("Ciudad");
        headerRow.createCell(8).setCellValue("Estado");

        // Datos
        int rowNum = 1;
        for (Empresas e : empresas) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(e.getNit() != null ? e.getNit() : "N/A");
            row.createCell(1).setCellValue(e.getNombre() != null ? e.getNombre() : "N/A");
            row.createCell(2).setCellValue(e.getDireccion() != null ? e.getDireccion() : "N/A");
            row.createCell(3).setCellValue(e.getArea() != null ? e.getArea() : "N/A");
            row.createCell(4).setCellValue(e.getContacto() != null ? e.getContacto() : "N/A");
            row.createCell(5).setCellValue(e.getEmail() != null ? e.getEmail() : "N/A");
            row.createCell(6).setCellValue(e.getDepartamento() != null ? e.getDepartamento() : "N/A");
            row.createCell(7).setCellValue(e.getCiudad() != null ? e.getCiudad() : "N/A");
            row.createCell(8).setCellValue(e.getEstado() != null ? e.getEstado() : "N/A");
        }

        // Ajustar ancho
        for (int i = 0; i < 9; i++) {
            sheet.autoSizeColumn(i);
        }
        workbook.write(response.getOutputStream());
        workbook.close();
    }

}