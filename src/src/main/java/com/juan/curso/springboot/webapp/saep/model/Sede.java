package com.juan.curso.springboot.webapp.saep.model;


import jakarta.persistence.*;

@Entity
@Table(name = "sede")
public class    Sede {

    @Id
    @GeneratedValue(strategy = GenerationType. IDENTITY)

    private Long id_sede;
    private String nombre;
    private String direccion;
    private String estado;

    public Long getId_sede() {
        return id_sede;
    }

    public void setId_sede(Long id_sede) {
        this.id_sede = id_sede;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
