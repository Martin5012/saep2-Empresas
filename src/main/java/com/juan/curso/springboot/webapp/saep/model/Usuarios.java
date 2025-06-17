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

    // SOLUCIÓN: Usar @ManyToOne con insertable=true, updatable=true
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol", insertable = true, updatable = true)
    private Rol rol;

    // Método auxiliar para obtener el id_rol como Integer (para compatibilidad con el HTML)
    public Integer getId_rol() {
        if (rol != null && rol.getId_rol() != null) {
            return rol.getId_rol().intValue(); // Convertir Long a Integer
        }
        return null;
    }

    // Método auxiliar para establecer el rol por ID (recibe Integer desde el HTML)
    public void setId_rol(Integer id_rol) {
        if (id_rol != null) {
            // Crear un objeto Rol temporal solo con el ID
            Rol rolTemp = new Rol();
            rolTemp.setId_rol(id_rol.longValue()); // Convertir Integer a Long
            this.rol = rolTemp;
        } else {
            this.rol = null;
        }
    }

    // Getters y Setters originales
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
  
    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}

