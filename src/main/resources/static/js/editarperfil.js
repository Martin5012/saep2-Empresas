/**
     * Función para habilitar la edición del formulario
     * - Deshabilita el botón "Editar"
     * - Habilita todos los campos del formulario
     * - Habilita los botones "Guardar" y "Cancelar"
     */
    function habilitarEdicion() {
        // Deshabilitar botón Editar
        document.getElementById('btnEditar').disabled = true;

        // Habilitar todos los campos del formulario (sin efectos visuales)
        const campos = document.querySelectorAll('.form-control-wide, .form-select-wide');
        campos.forEach(campo => {
            campo.disabled = false;
        });

        // Habilitar botones Guardar y Cancelar
        document.getElementById('btnGuardar').disabled = false;
        document.getElementById('btnCancelar').disabled = false;

        // Enfocar el primer campo
        document.querySelector('.form-control-wide').focus();
    }

    /**
     * Función para cancelar la edición del formulario
     * - Recarga la página para restaurar los valores originales
     * - Restablece el estado inicial del formulario
     */
    function cancelarEdicion() {
        // Confirmar cancelación
        if (confirm('¿Está seguro de que desea cancelar? Se perderán los cambios no guardados.')) {
            // Recargar la página para restaurar valores originales
            window.location.reload();
        }
    }

    /**
     * Función para restablecer el estado inicial después de guardar
     * Se ejecuta cuando el formulario se envía exitosamente
     */
    function restablecerEstadoInicial() {
        // Habilitar botón Editar
        document.getElementById('btnEditar').disabled = false;

        // Deshabilitar todos los campos del formulario
        const campos = document.querySelectorAll('.form-control-wide, .form-select-wide');
        campos.forEach(campo => {
            campo.disabled = true;
        });

        // Deshabilitar botones Guardar y Cancelar
        document.getElementById('btnGuardar').disabled = true;
        document.getElementById('btnCancelar').disabled = true;
    }

    /**
     * Interceptar el envío del formulario para manejar el estado después de guardar
     */
    document.querySelector('form').addEventListener('submit', function(e) {
        // Simular el guardado exitoso (en un entorno real, esto se manejaría desde el servidor)
        setTimeout(function() {
            restablecerEstadoInicial();
        }, 100);
    });

    /**
     * Asegurar que el estado inicial esté correcto cuando se carga la página
     */
    document.addEventListener('DOMContentLoaded', function() {
        // Si hay un mensaje de éxito, restablecer el estado inicial
        if (document.querySelector('.alert-success')) {
            restablecerEstadoInicial();
        }
    });



