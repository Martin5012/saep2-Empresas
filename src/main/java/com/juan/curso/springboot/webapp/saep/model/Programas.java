package com.juan.curso.springboot.webapp.saep.model;

import jakarta.persistence.*;

@Entity
@Table (name = "programas")
public class Programas
{
    @Id
    @GeneratedValue(strategy = GenerationType. IDENTITY)
    private Long id_programas;
    private String nombre,version,estado;

    public Long getId_programas() {
        return id_programas;
    }

    public void setId_programas(Long id_programas) {
        this.id_programas = id_programas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
