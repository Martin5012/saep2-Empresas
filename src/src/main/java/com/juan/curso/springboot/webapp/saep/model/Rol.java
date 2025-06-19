package com.juan.curso.springboot.webapp.saep.model;

import jakarta.persistence.*;

    @Entity
    @Table(name = "rol")
    public class Rol
    {

        @Id
        @GeneratedValue(strategy = GenerationType. IDENTITY)

        private Long id_rol;
        private String roles;

        public Long getId_rol() {
            return id_rol;
        }

        public void setId_rol(Long id_rol) {
            this.id_rol = id_rol;
        }

        public String getRoles() {
            return roles;
        }

        public void setRoles(String roles) {
            this.roles = roles;
        }
    }
