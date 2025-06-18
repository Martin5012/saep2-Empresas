package com.juan.curso.springboot.webapp.saep.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="aprendiz")
public class Aprendiz {
    @Id
    @GeneratedValue(strategy = GenerationType. IDENTITY)
    private Long id_aprendices;
    private Integer id_usuarios;
    private String estado;
    private Integer id_fichas;
    private Integer id_empresas;
    private Integer id_instructor;
    private Integer id_modalidad;
    private String nombre;
    private Integer progreso;
    private Date fechaInicio;
    private Date fechaFin;

    public Long getId_aprendices() {
        return id_aprendices;
    }

    public void setId_aprendices(Long id_aprendices) {
        this.id_aprendices = id_aprendices;
    }

    public Integer getId_usuarios() {
        return id_usuarios;
    }

    public void setId_usuarios(Integer id_usuarios) {
        this.id_usuarios = id_usuarios;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getId_fichas() {
        return id_fichas;
    }

    public void setId_fichas(Integer id_fichas) {
        this.id_fichas = id_fichas;
    }

    public Integer getId_empresas() {
        return id_empresas;
    }

    public void setId_empresas(Integer id_empresas) {
        this.id_empresas = id_empresas;
    }

    public Integer getId_instructor() {
        return id_instructor;
    }

    public void setId_instructor(Integer id_instructor) {
        this.id_instructor = id_instructor;
    }

    public Integer getId_modalidad() {
        return id_modalidad;
    }

    public void setId_modalidad(Integer id_modalidad) {
        this.id_modalidad = id_modalidad;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getProgreso() {
        return progreso;
    }

    public void setProgreso(Integer progreso) {
        this.progreso = progreso;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int calcularProgreso() {
        return this.progreso != null ? this.progreso : 0;
    }
}

