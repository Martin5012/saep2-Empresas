package com.juan.curso.springboot.webapp.saep.model;

import com.itextpdf.text.pdf.PdfPCell;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="novedades")
public class Novedades {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_novedad;

    private String novedad;

    @CreationTimestamp
    @Column(name = "fecha")
    private LocalDateTime fecha;

    // Relación correcta: apunta directamente al usuario que es aprendiz
    @ManyToOne
    @JoinColumn(name = "id_aprendiz") // Coincide con el nombre en la BD
    private Usuarios aprendiz; // Nombre más claro

    // Constructor vacío
    public Novedades() {}

    // Getters y Setters
    public Long getId_novedad() {
        return id_novedad;
    }

    public void setId_novedad(Long id_novedad) {
        this.id_novedad = id_novedad;
    }

    public String getNovedad() {
        return novedad;
    }

    public void setNovedad(String novedad) {
        this.novedad = novedad;
    }


    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }


    public void setAprendiz(Usuarios aprendiz) {
        this.aprendiz = aprendiz;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public Usuarios getAprendiz() {
        return aprendiz;
    }
}