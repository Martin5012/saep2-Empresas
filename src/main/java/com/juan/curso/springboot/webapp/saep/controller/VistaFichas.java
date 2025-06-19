package com.juan.curso.springboot.webapp.saep.controller;

import com.itextpdf.text.Font;

import com.juan.curso.springboot.webapp.saep.model.Fichas;
import com.juan.curso.springboot.webapp.saep.repository.FichasRepository;
import com.juan.curso.springboot.webapp.saep.repository.ProgramasRepository;
import com.juan.curso.springboot.webapp.saep.repository.SedesRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.io.InputStream;
import java.util.List;

// Librerías para PDF y Excel
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Controller
public class VistaFichas {

    @Autowired
    private FichasRepository fichasRepository;

    @Autowired
    private SedesRepository sedesRepository;

    @Autowired
    private ProgramasRepository programasRepository;

    // ---------- RUTAS NORMALES PARA LA VISTA ----------

    @GetMapping("/vista/fichas")
    public String mostrarBusqueda(@RequestParam(name = "buscar", required = false) String buscar, Model model) {
        List<Fichas> fichas;

        if (buscar != null && !buscar.isEmpty()) {
            fichas = fichasRepository.buscarPorCriterio(buscar);
        } else {
            fichas = fichasRepository.findAll();
        }

        model.addAttribute("fichas", fichas);
        model.addAttribute("buscar", buscar); // Para mantener el texto en el input
        return "fichas";
    }


    @GetMapping("/vistaf/form")
    public String formulario(Model model) {
        model.addAttribute("fichas", new Fichas());
        model.addAttribute("sedes", sedesRepository.findAll());
        model.addAttribute("programas", programasRepository.findAll());
        return "fichas_form";
    }

    @PostMapping("/vistaf/guardar")
    public String guardar(@ModelAttribute Fichas fichas, RedirectAttributes ra) {
        fichasRepository.save(fichas);
        ra.addFlashAttribute("mensaje", "Ficha guardada exitosamente");
        return "redirect:/vista/fichas";
    }

    @GetMapping("/vistaf/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Fichas fichas = fichasRepository.findById(id).orElse(null);
        model.addAttribute("fichas", fichas);
        model.addAttribute("sedes", sedesRepository.findAll());
        model.addAttribute("programas", programasRepository.findAll());
        return "fichas_form";
    }

    @PostMapping("/vistaf/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        fichasRepository.deleteById(id);
        ra.addFlashAttribute("mensaje", "Ficha eliminada exitosamente");
        return "redirect:/vista/fichas";
    }

    // ---------- NUEVAS RUTAS PARA EXPORTAR PDF Y EXCEL ----------

    @GetMapping("/vistaf/pdf")
    public void exportarPDF(HttpServletResponse response) throws Exception {
        String nombreArchivo = "Fichas.pdf";
        Document document = new Document(PageSize.A4, 25 , 25, 62, 65);

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
        Paragraph title = new Paragraph("Listado de Fichas", titleFont);
        title.setIndentationLeft(50);
        document.add(title);


        PdfPTable table = new PdfPTable(6); // Ajusta columnas según los campos que quieras mostrar
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);

        table.addCell("ID");
        table.addCell("Código");
        table.addCell("Programa");
        table.addCell("Sede");
        table.addCell("Modalidad");
        table.addCell("Estado");

        for (Fichas f : fichasRepository.findAll()) {
            table.addCell(String.valueOf(f.getId_fichas()));
            table.addCell(f.getCodigo());
            table.addCell(f.getIdProgramas() != null ? f.getIdProgramas().getNombre() : "N/A");
            table.addCell(f.getIdSedes() != null ? f.getIdSedes().getNombre() : "N/A");
            table.addCell(f.getModalidad());
            table.addCell(f.getEstado());
        }

        document.add(table);
        document.close();
    }


    @GetMapping("/vistaf/excel")
    public void exportarExcel(HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Fichas.xlsx");

        List<Fichas> fichas = fichasRepository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Fichas");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Código");
        headerRow.createCell(2).setCellValue("Programa");
        headerRow.createCell(3).setCellValue("Sede");
        headerRow.createCell(4).setCellValue("Modalidad");
        headerRow.createCell(5).setCellValue("Estado");

        int rowNum = 1;
        for (Fichas f : fichas) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(f.getId_fichas());
            row.createCell(1).setCellValue(f.getCodigo());
            row.createCell(2).setCellValue(f.getIdProgramas() != null ? f.getIdProgramas().getNombre() : "N/A");
            row.createCell(3).setCellValue(f.getIdSedes() != null ? f.getIdSedes().getNombre() : "N/A");
            row.createCell(4).setCellValue(f.getModalidad());
            row.createCell(5).setCellValue(f.getEstado());
        }

        for (int i = 0; i < 6; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }

}
