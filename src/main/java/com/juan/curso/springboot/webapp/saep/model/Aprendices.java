package com.juan.curso.springboot.webapp.saep.model;

import jakarta.persistence.*;

@Entity
@Table(name="aprendices")
public class Aprendices
{
    @Id
    @GeneratedValue(strategy = GenerationType. IDENTITY)
    private Long id_aprendices;
    private String estado;

    @ManyToOne
    @JoinColumn(name = "id_usuarios")
    private Usuarios idUsuarios;

    @ManyToOne
    @JoinColumn(name = "id_fichas")
    private Fichas idFichas;

    @ManyToOne
    @JoinColumn(name = "id_empresas")
    private Empresas idEmpresas;

    @ManyToOne
    @JoinColumn(name = "id_instructor")
    private Usuarios idInstructor;

    @ManyToOne
    @JoinColumn(name = "id_modalidad")
    private Modalidad idModalidades;


    public Long getId_aprendices() {
        return id_aprendices;
    }

    public void setId_aprendices(Long id_aprendices) {
        this.id_aprendices = id_aprendices;
    }

    public Usuarios getIdUsuarios() {
        return idUsuarios;
    }

    public void setIdUsuarios(Usuarios idUsuarios) {
        this.idUsuarios = idUsuarios;
    }

    public Fichas getIdFichas() {
        return idFichas;
    }

    public void setIdFichas(Fichas idFichas) {
        this.idFichas = idFichas;
    }

    public Empresas getIdEmpresas() {
        return idEmpresas;
    }

    public void setIdEmpresas(Empresas idEmpresas) {
        this.idEmpresas = idEmpresas;
    }

    public Usuarios getIdInstructor() {
        return idInstructor;
    }

    public void setIdInstructor(Usuarios idInstructor) {
        this.idInstructor = idInstructor;
    }

    public Modalidad getIdModalidades() {
        return idModalidades;
    }

    public void setIdModalidades(Modalidad idModalidades) {
        this.idModalidades = idModalidades;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
