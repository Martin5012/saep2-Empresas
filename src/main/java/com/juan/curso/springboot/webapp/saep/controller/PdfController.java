package com.juan.curso.springboot.webapp.saep.controller;

import com.juan.curso.springboot.webapp.saep.services.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @GetMapping("/empresas/pdf")
    public ResponseEntity<InputStreamResource> generarPdf() throws Exception {
        var pdfStream = pdfService.generarPdfEmpresas();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=empresas.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfStream));
    }
}
