package com.juan.curso.springboot.webapp.saep.model;

import jakarta.persistence.*;

@Entity
@Table(name="empresas")
public class Empresas
{
    @Id
    @GeneratedValue(strategy = GenerationType. IDENTITY) // Auto-incremental
    private Long id_empresas;
    @ManyToOne
    @JoinColumn (name = "id_usuarios")
    private Usuarios id_usuarios;


    private String nit;
    private String nombre;
    private String direccion;
    private String area;
    private String contacto;
    private String email;
    private String departamento;
    private String ciudad;
    private String estado;

    public Long getId_empresas() {
        return id_empresas;
    }

    public void setId_empresas(Long id_empresas) {
        this.id_empresas = id_empresas;
    }

    public Usuarios getId_usuarios() {
        return id_usuarios;
    }

    public void setId_usuarios(Usuarios id_usuarios) {
        this.id_usuarios = id_usuarios;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
