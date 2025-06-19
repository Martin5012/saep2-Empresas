package com.juan.curso.springboot.webapp.saep.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name="aprendices")
public class Aprendiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_aprendices;

    private String estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuarios")
    private Usuarios usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_fichas")
    private Fichas ficha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresas")
    private Empresas empresa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_instructor")
    private Usuarios instructor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_modalidad")
    private Modalidad modalidad;

    // Método para calcular el progreso basado en las fechas de la ficha
    public int calcularProgreso() {
        if (ficha == null || ficha.getFecha_fin_lec() == null || ficha.getFecha_final() == null) {
            return 0;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fechaInicio = LocalDate.parse(ficha.getFecha_fin_lec(), formatter);
            LocalDate fechaFinal = LocalDate.parse(ficha.getFecha_final(), formatter);
            LocalDate fechaActual = LocalDate.now();

            // Si aún no ha comenzado
            if (fechaActual.isBefore(fechaInicio)) {
                return 0;
            }

            // Si ya terminó
            if (fechaActual.isAfter(fechaFinal)) {
                return 100;
            }

            // Calcular progreso basado en días transcurridos
            long diasTotales = ChronoUnit.DAYS.between(fechaInicio, fechaFinal);
            long diasTranscurridos = ChronoUnit.DAYS.between(fechaInicio, fechaActual);

            if (diasTotales <= 0) {
                return 0;
            }

            int progreso = (int) Math.round((double) diasTranscurridos / diasTotales * 100);
            return Math.min(Math.max(progreso, 0), 100); // Asegurar que esté entre 0 y 100

        } catch (Exception e) {
            return 0;
        }
    }

    // Método para obtener el nombre completo del aprendiz
    public String getNombreCompleto() {
        if (usuario != null) {
            return (usuario.getNombres() != null ? usuario.getNombres() : "") + " " +
                    (usuario.getApellidos() != null ? usuario.getApellidos() : "");
        }
        return "Aprendiz";
    }

    // Método para obtener la fecha de inicio formateada
    public LocalDate getFechaInicio() {
        if (ficha != null && ficha.getFecha_fin_lec() != null) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return LocalDate.parse(ficha.getFecha_fin_lec(), formatter);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    // Método para obtener la fecha final formateada
    public LocalDate getFechaFin() {
        if (ficha != null && ficha.getFecha_final() != null) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return LocalDate.parse(ficha.getFecha_final(), formatter);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    // Getters y Setters
    public Long getId_aprendices() {
        return id_aprendices;
    }

    public void setId_aprendices(Long id_aprendices) {
        this.id_aprendices = id_aprendices;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Fichas getFicha() {
        return ficha;
    }

    public void setFicha(Fichas ficha) {
        this.ficha = ficha;
    }

    public Empresas getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresas empresa) {
        this.empresa = empresa;
    }

    public Usuarios getInstructor() {
        return instructor;
    }

    public void setInstructor(Usuarios instructor) {
        this.instructor = instructor;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }
}