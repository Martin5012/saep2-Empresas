package com.juan.curso.springboot.webapp.saep.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UbicacionController {

    @GetMapping("/ciudades")
    public ResponseEntity<String> obtenerCiudades() {
        try {
            ClassPathResource resource = new ClassPathResource("static/data/colombia.json");
            InputStream inputStream = resource.getInputStream();
            String json = new BufferedReader(new InputStreamReader(inputStream))
                    .lines().collect(Collectors.joining("\n"));
            return ResponseEntity.ok(json);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"error\":\"No se pudo cargar el JSON\"}");
        }
    }
}

