<!-- Script para ocultar/mostrar sidebar -->
function ocultarBarra()
{

    const sidebar = document.getElementById('sidebar');
    const toggleBtn = document.getElementById('toggleSidebar');

    toggleBtn.addEventListener('click', () => {
        if (sidebar.style.display === 'none') {
            sidebar.style.display = 'block';
            document.body.style.marginLeft = '220px';
        } else {
            sidebar.style.display = 'none';
            document.body.style.marginLeft = '0';
        }
    });
}

<!--GRAFICCOOOOO-->

// resources/static/js/grafico.js

document.addEventListener('DOMContentLoaded', function() {
    // Obtener elementos del DOM
    const progressCircle = document.getElementById('progress-circle');
    const progressText = document.getElementById('progress-text');

    // Obtener el porcentaje de progreso (usando Thymeleaf o valor por defecto)
    let progress = 77; // Valor por defecto
    if(progressText.textContent) {
        progress = parseInt(progressText.textContent);
    }

    // Calcular el ángulo de rotación
    const angle = (progress / 100) * 360;

    // Aplicar la rotación al círculo de progreso
    if (angle <= 180) {
        progressCircle.style.transform = 'rotate(' + (90 + angle) + 'deg)';
        progressCircle.style.clip = 'rect(0, 75px, 150px, 0)';
    } else {
        progressCircle.style.transform = 'rotate(' + (90 + angle) + 'deg)';
        progressCircle.style.clip = 'rect(auto, auto, auto, auto)';

        // Crear un segundo arco para ángulos mayores a 180 grados
        const secondArc = document.createElement('div');
        secondArc.className = 'progress-circle-progress';
        secondArc.style.borderTopColor = '#39A900';
        secondArc.style.transform = 'rotate(90deg)';
        secondArc.style.clip = 'rect(0, 150px, 150px, 75px)';
        progressCircle.parentNode.insertBefore(secondArc, progressCircle.nextSibling);
    }
});

<!--EDITAR PERFILLL-->

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
        * Interceptar el envío del formulario para validación y manejo de estado
        */
       document.querySelector('form').addEventListener('submit', function(e) {
           // Solo validación, NO restablecer estado aquí
           const campos = document.querySelectorAll('.form-control-wide, .form-select-wide');

           for (let campo of campos) {
               if (campo.value.trim() === '') {
                   e.preventDefault();
                   alert('Por favor complete todos los campos obligatorios');
                   campo.focus();
                   return;
               }
           }
           // El formulario se enviará normalmente con todos los campos habilitados
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
