document.addEventListener('DOMContentLoaded', function () {
    fetch('/api/ciudades')
        .then(response => response.json())
        .then(data => {
            const departamentoSelect = document.getElementById('departamentoSelect');
            const ciudadSelect = document.getElementById('ciudadSelect');

            // Llenar departamentos
            data.forEach(dep => {
                const option = document.createElement('option');
                option.value = dep.id;
                option.text = dep.departamento;
                departamentoSelect.add(option);
            });

            // Cambiar ciudades al seleccionar un departamento
            departamentoSelect.addEventListener('change', function () {
                const selectedDepId = this.value;

                // Limpiar ciudades
                ciudadSelect.innerHTML = '<option selected>Seleccione una ciudad</option>';

                const selectedDep = data.find(d => d.id == selectedDepId);
                if (selectedDep) {
                    selectedDep.ciudades.forEach(ciudad => {
                        const option = document.createElement('option');
                        option.value = ciudad;
                        option.text = ciudad;
                        ciudadSelect.add(option);
                    });
                }
            });
        });
});