package com.juan.curso.springboot.webapp.saep.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuarios;
    private String tipo_dc;
    private String numero;
    private String nombres;
    private String apellidos;
    private String email;
    private String email_insti;
    private String direccion;
    private String contacto1;
    private String contacto2;
    private String clave;
    private String estado;
    //private Integer id_rol;


    @Column(name = "id_rol", insertable = false, updatable = false)
    private Integer id_rol;  // Esta propiedad solo se mapea como una columna, sin modificarla

    @ManyToOne
    @JoinColumn(name = "id_rol") // esta columna debe existir en la tabla usuario
    private Rol rol;


    // Getters y Setters

    public Long getId_usuarios() {
        return id_usuarios;
    }

    public void setId_usuarios(Long id_usuarios) {
        this.id_usuarios = id_usuarios;
    }

    public String getTipo_dc() {
        return tipo_dc;
    }

    public void setTipo_dc(String tipo_dc) {
        this.tipo_dc = tipo_dc;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail_insti() {
        return email_insti;
    }

    public void setEmail_insti(String email_insti) {
        this.email_insti = email_insti;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getContacto1() {
        return contacto1;
    }

    public void setContacto1(String contacto1) {
        this.contacto1 = contacto1;
    }

    public String getContacto2() {
        return contacto2;
    }

    public void setContacto2(String contacto2) {
        this.contacto2 = contacto2;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getId_rol() {
        return id_rol;
    }

    public void setId_rol(Integer id_rol) {
        this.id_rol = id_rol;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
