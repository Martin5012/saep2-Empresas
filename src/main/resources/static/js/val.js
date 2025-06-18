
    function validarFormulario() {
        const campos = [
            'nit',
            'id_usuarios',
            'nombre',
            'direccion',
            'area',
            'contacto',
            'email',
            'departamento',
            'ciudad',
            'estado'
        ];

        let camposVacios = [];
        const mensajeError = document.getElementById('mensajeError');
        mensajeError.textContent = ''; // Limpiar mensaje anterior

        campos.forEach(campo => {
            const elemento = document.querySelector(`[name="${campo}"]`);
            if (!elemento || !elemento.value.trim()) {
                camposVacios.push(campo);
                if (elemento) {
                    elemento.style.border = '2px solid red';
                }
            } else {
                elemento.style.border = '';
            }
        });

        if (camposVacios.length > 0) {
            mensajeError.textContent = 'Por favor complete todos los campos.';
            return false;
        }

        return true;
    }

    document.addEventListener('DOMContentLoaded', function() {
        const campos = [
            'nit',
            'id_usuarios',
            'nombre',
            'direccion',
            'area',
            'contacto',
            'email',
            'departamento',
            'ciudad',
            'estado'
        ];

        // Quitar borde rojo cuando el usuario escribe
        campos.forEach(campo => {
            const elemento = document.querySelector(`[name="${campo}"]`);
            if (elemento) {
                elemento.addEventListener('input', () => {
                    if (elemento.value.trim()) {
                        elemento.style.border = '';
                        document.getElementById('mensajeError').textContent = '';
                    }
                });
            }
        });

        // Validar al hacer clic en "Guardar"
        const btnGuardar = document.querySelector('button[type="submit"]');
        if (btnGuardar) {
            btnGuardar.addEventListener('click', function(e) {
                if (!validarFormulario()) {
                    e.preventDefault();
                }
            });
        }
    });

