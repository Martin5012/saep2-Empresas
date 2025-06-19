package com.juan.curso.springboot.webapp.saep.model;


import jakarta.persistence.*;

@Entity
@Table(name = "seguimiento")

public class Seguimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id_seguimiento;
    private String nombre_archivo;
    private String tipo_formato;
    private String fecha;
    private String archivo;
    private String observaciones;
    private Integer id_usuarios;
    private Integer id_aprendices;
    private String val1;
    private String val2;
    private String val3;

    @Transient
    private String nombreUsuario;


    public Long getId_seguimiento() {
        return id_seguimiento;
    }

    public void setId_seguimiento(Long id_seguimiento) {
        this.id_seguimiento = id_seguimiento;
    }

    public String getNombre_archivo() {
        return nombre_archivo;
    }

    public void setNombre_archivo(String nombre_archivo) {
        this.nombre_archivo = nombre_archivo;
    }

    public String getTipo_formato() {
        return tipo_formato;
    }

    public void setTipo_formato(String tipo_formato) {
        this.tipo_formato = tipo_formato;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Integer getId_usuarios() {
        return id_usuarios;
    }

    public void setId_usuarios(Integer id_usuarios) {
        this.id_usuarios = id_usuarios;
    }

    public Integer getId_aprendices() {
        return id_aprendices;
    }

    public void setId_aprendices(Integer id_aprendices) {
        this.id_aprendices = id_aprendices;
    }

    public String getVal1() {
        return val1;
    }

    public void setVal1(String val1) {
        this.val1 = val1;
    }

    public String getVal2() {
        return val2;
    }

    public void setVal2(String val2) {
        this.val2 = val2;
    }

    public String getVal3() {
        return val3;
    }

    public void setVal3(String val3) {
        this.val3 = val3;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
