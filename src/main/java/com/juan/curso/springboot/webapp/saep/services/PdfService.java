package com.juan.curso.springboot.webapp.saep.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.*;

@Service
public class PdfService {

    public ByteArrayInputStream generarPdfEmpresas() throws Exception {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();

            // Color institucional
            BaseColor verdeSena = new BaseColor(57, 169, 0);

            // Fondo (opcional, si quieres añadir una imagen como fondo)
            Image background = Image.getInstance("src/main/java/com/juan/curso/springboot/webapp/saep/services/img/fondo.png");
            background.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
            background.setAbsolutePosition(0, 0);
            writer.getDirectContentUnder().addImage(background);

            document.add(new Paragraph("\n\n\n\n\n"));

            Paragraph titulo = new Paragraph("Empresas Registradas",
                    FontFactory.getFont("Tahoma", 22, Font.BOLD, verdeSena));
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            document.add(new Paragraph("\n\n"));

            PdfPTable tabla = new PdfPTable(9);
            tabla.setWidthPercentage(100);

            String[] headers = {"NIT", "Nombre", "Dir", "Area", "Contacto", "Correo", "Depto", "Ciudad", "Estado"};

            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header,
                        FontFactory.getFont("Calibri", 12, Font.BOLD, BaseColor.WHITE)));
                cell.setBackgroundColor(verdeSena);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabla.addCell(cell);
            }

            try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/saep2", "root", "");
                 PreparedStatement pst = cn.prepareStatement("SELECT nit, nombre, direccion, area, contacto, email, departamento, ciudad, estado FROM empresas");
                 ResultSet rs = pst.executeQuery()) {

                while (rs.next()) {
                    for (int i = 1; i <= 9; i++) {
                        tabla.addCell(rs.getString(i));
                    }
                }
            }

            document.add(tabla);
            document.close();
        } catch (Exception e) {
            throw new Exception("Error generando PDF: " + e.getMessage(), e);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
