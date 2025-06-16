document.addEventListener("DOMContentLoaded", function () {
    const departamentoSelect = document.getElementById("departamento");
    const ciudadSelect = document.getElementById("ciudad");

    const departamentoGuardado = document.getElementById("departamentoGuardado")?.value;
    const ciudadGuardada = document.getElementById("ciudadGuardada")?.value;

    fetch('/api/ciudades')
        .then(response => response.json())
        .then(data => {
            // Limpiar primero
            departamentoSelect.innerHTML = '<option value="">Seleccione un departamento</option>';

            data.forEach(dep => {
                const option = document.createElement("option");
                option.value = dep.departamento;
                option.text = dep.departamento;
                departamentoSelect.appendChild(option);
            });

            // Selecciona automáticamente el departamento guardado
            if (departamentoGuardado) {
                departamentoSelect.value = departamentoGuardado;

                // Llenar ciudades basadas en el departamento guardado
                const departamento = data.find(d => d.departamento === departamentoGuardado);
                if (departamento) {
                    ciudadSelect.innerHTML = '<option value="">Seleccione una ciudad</option>';
                    departamento.ciudades.forEach(ciudad => {
                        const option = document.createElement("option");
                        option.value = ciudad;
                        option.text = ciudad;
                        ciudadSelect.appendChild(option);
                    });

                    // Selecciona automáticamente la ciudad guardada
                    if (ciudadGuardada) {
                        ciudadSelect.value = ciudadGuardada;
                    }
                }
            }

            // Evento para cambiar las ciudades cuando se selecciona otro departamento
            departamentoSelect.addEventListener("change", function () {
                const seleccionado = this.value;
                const dep = data.find(d => d.departamento === seleccionado);

                ciudadSelect.innerHTML = '<option value="">Seleccione una ciudad</option>';
                if (dep) {
                    dep.ciudades.forEach(ciudad => {
                        const option = document.createElement("option");
                        option.value = ciudad;
                        option.text = ciudad;
                        ciudadSelect.appendChild(option);
                    });
                }
            });
        });
});
