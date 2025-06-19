package com.juan.curso.springboot.webapp.saep.controller;

import com.juan.curso.springboot.webapp.saep.model.Aprendiz;
import com.juan.curso.springboot.webapp.saep.model.Usuarios;
import com.juan.curso.springboot.webapp.saep.repository.AprendizRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/vista")
public class VistaAprendiz {

    @Autowired
    private AprendizRepository aprendizRepository;

    @GetMapping("/aprendiz")
    public String mostrarVistaAprendiz(Model model, HttpSession session) {
        // Obtener el usuario logueado de la sesión
        Usuarios usuarioLogueado = (Usuarios) session.getAttribute("usuarioLogueado");
        Long idUsuarioLogueado = (Long) session.getAttribute("idUsuarioLogueado");

        if (usuarioLogueado == null || idUsuarioLogueado == null) {
            // Si no hay usuario en sesión, redirigir al login
            return "redirect:/login";
        }

        // Verificar que el usuario tenga rol de aprendiz
        if (usuarioLogueado.getRol() == null ||
                !"APRENDIZ".equalsIgnoreCase(usuarioLogueado.getRol().getRoles().trim())) {
            // Si no es aprendiz, redirigir a página de error o login
            model.addAttribute("error", "Acceso no autorizado");
            return "redirect:/login";
        }

        try {
            // Buscar el aprendiz asociado al usuario logueado
            Optional<Aprendiz> aprendizOpt = aprendizRepository.findByUsuarioIdWithDetails(idUsuarioLogueado);

            if (aprendizOpt.isPresent()) {
                Aprendiz aprendiz = aprendizOpt.get();

                // Crear un objeto DTO para la vista con los datos necesarios
                AprendizDTO aprendizDTO = new AprendizDTO();
                aprendizDTO.setNombre(aprendiz.getNombreCompleto());
                aprendizDTO.setProgreso(aprendiz.calcularProgreso());
                aprendizDTO.setFechaInicio(aprendiz.getFechaInicio());
                aprendizDTO.setFechaFin(aprendiz.getFechaFin());
                aprendizDTO.setEstado(aprendiz.getEstado());

                // Información adicional si está disponible
                if (aprendiz.getFicha() != null) {
                    aprendizDTO.setCodigoFicha(aprendiz.getFicha().getCodigo());
                    if (aprendiz.getFicha().getIdProgramas() != null) {
                        aprendizDTO.setNombrePrograma(aprendiz.getFicha().getIdProgramas().getNombre());
                    }
                }

                if (aprendiz.getEmpresa() != null) {
                    aprendizDTO.setNombreEmpresa(aprendiz.getEmpresa().getNombre());
                }

                model.addAttribute("aprendiz", aprendizDTO);

            } else {
                // Si no se encuentra el aprendiz, crear un objeto por defecto
                AprendizDTO aprendizDefault = new AprendizDTO();
                aprendizDefault.setNombre(usuarioLogueado.getNombres() + " " + usuarioLogueado.getApellidos());
                aprendizDefault.setProgreso(0);
                aprendizDefault.setEstado("No asignado");

                model.addAttribute("aprendiz", aprendizDefault);
                model.addAttribute("mensaje", "No se encontró información de aprendiz para este usuario.");
            }

        } catch (Exception e) {
            // En caso de error, crear objeto por defecto
            AprendizDTO aprendizDefault = new AprendizDTO();
            aprendizDefault.setNombre(usuarioLogueado.getNombres() + " " + usuarioLogueado.getApellidos());
            aprendizDefault.setProgreso(0);
            aprendizDefault.setEstado("Error al cargar datos");

            model.addAttribute("aprendiz", aprendizDefault);
            model.addAttribute("error", "Error al cargar la información del aprendiz");
        }

        return "aprendiz"; // Retorna la vista aprendiz.html
    }

    // Clase DTO interna para pasar datos a la vista
    public static class AprendizDTO {
        private String nombre;
        private int progreso;
        private java.time.LocalDate fechaInicio;
        private java.time.LocalDate fechaFin;
        private String estado;
        private String codigoFicha;
        private String nombrePrograma;
        private String nombreEmpresa;

        // Getters y Setters
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public int getProgreso() { return progreso; }
        public void setProgreso(int progreso) { this.progreso = progreso; }

        public java.time.LocalDate getFechaInicio() { return fechaInicio; }
        public void setFechaInicio(java.time.LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

        public java.time.LocalDate getFechaFin() { return fechaFin; }
        public void setFechaFin(java.time.LocalDate fechaFin) { this.fechaFin = fechaFin; }

        public String getEstado() { return estado; }
        public void setEstado(String estado) { this.estado = estado; }

        public String getCodigoFicha() { return codigoFicha; }
        public void setCodigoFicha(String codigoFicha) { this.codigoFicha = codigoFicha; }

        public String getNombrePrograma() { return nombrePrograma; }
        public void setNombrePrograma(String nombrePrograma) { this.nombrePrograma = nombrePrograma; }

        public String getNombreEmpresa() { return nombreEmpresa; }
        public void setNombreEmpresa(String nombreEmpresa) { this.nombreEmpresa = nombreEmpresa; }
    }
}