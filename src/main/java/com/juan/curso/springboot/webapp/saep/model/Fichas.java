package com.juan.curso.springboot.webapp.saep.model;

import jakarta.persistence.*;

@Entity
@Table(name="fichas")
public class Fichas
{
    @Id
    @GeneratedValue(strategy = GenerationType. IDENTITY)
    private Long id_fichas;
    private String codigo,modalidad,jornada,nivel_formacion,fecha_inicio,fecha_fin_lec,fecha_final,tipo_oferta,estado;

    @ManyToOne
    @JoinColumn(name = "id_programas")
    private Programas idProgramas;

    @ManyToOne
    @JoinColumn(name = "id_sede")
    private Sedes idSedes;


    public Long getId_fichas() {
        return id_fichas;
    }

    public void setId_fichas(Long id_fichas) {
        this.id_fichas = id_fichas;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    public String getNivel_formacion() {
        return nivel_formacion;
    }

    public void setNivel_formacion(String nivel_formacion) {
        this.nivel_formacion = nivel_formacion;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_fin_lec() {
        return fecha_fin_lec;
    }

    public void setFecha_fin_lec(String fecha_fin_lec) {
        this.fecha_fin_lec = fecha_fin_lec;
    }

    public String getFecha_final() {
        return fecha_final;
    }

    public void setFecha_final(String fecha_final) {
        this.fecha_final = fecha_final;
    }

    public String getTipo_oferta() {
        return tipo_oferta;
    }

    public void setTipo_oferta(String tipo_oferta) {
        this.tipo_oferta = tipo_oferta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Programas getIdProgramas() {
        return idProgramas;
    }

    public void setIdProgramas(Programas idProgramas) {
        this.idProgramas = idProgramas;
    }

    public Sedes getIdSedes() {
        return idSedes;
    }

    public void setIdSedes(Sedes idSedes) {
        this.idSedes = idSedes;
    }
}
