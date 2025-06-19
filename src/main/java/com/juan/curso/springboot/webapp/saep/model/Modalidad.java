package com.juan.curso.springboot.webapp.saep.model;

import jakarta.persistence.*;

@Entity
@Table(name="modalidad")
public class Modalidad
{
    @Id
    @GeneratedValue(strategy = GenerationType. IDENTITY)
    private Long id_modalidad;
    private String modalidades;

    public Long getId_modalidad() {
        return id_modalidad;
    }

    public void setId_modalidad(Long id_modalidad) {
        this.id_modalidad = id_modalidad;
    }

    public String getModalidades() {
        return modalidades;
    }

    public void setModalidades(String modalidades) {
        this.modalidades = modalidades;
    }
}
