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

/*=========================SELECTS FILTRABLES===============================*/

/**
 * Clase para manejar selects con filtrado en tiempo real (SIN TILDES)
 */
class FilterableSelect {
    constructor(searchInputId, dropdownId, hiddenSelectId) {
        this.searchInput = document.getElementById(searchInputId);
        this.dropdown = document.getElementById(dropdownId);
        this.hiddenSelect = document.getElementById(hiddenSelectId);

        if (!this.searchInput || !this.dropdown || !this.hiddenSelect) {
            console.warn(`FilterableSelect: No se encontraron elementos para ${searchInputId}`);
            return;
        }

        this.options = Array.from(this.dropdown.querySelectorAll('.filterable-option'));
        this.selectedIndex = -1;

        this.init();
    }

    init() {
        // Configurar eventos
        this.searchInput.addEventListener('input', (e) => this.filterOptions(e.target.value));
        this.searchInput.addEventListener('focus', () => this.showDropdown());
        this.searchInput.addEventListener('blur', () => {
            // Delay para permitir click en opciones
            setTimeout(() => this.hideDropdown(), 200);
        });

        // Navegación con teclado
        this.searchInput.addEventListener('keydown', (e) => this.handleKeydown(e));

        // Click en opciones
        this.options.forEach((option, index) => {
            option.addEventListener('click', () => this.selectOption(option, index));
            option.addEventListener('mouseenter', () => this.highlightOption(index));
        });

        // Cerrar dropdown al hacer click fuera
        document.addEventListener('click', (e) => {
            if (!e.target.closest('.filterable-select-container')) {
                this.hideDropdown();
            }
        });

        // Configurar valor inicial si existe
        this.setInitialValue();
    }

    /**
     * Función para normalizar texto removiendo tildes y caracteres especiales
     * Permite búsquedas flexibles sin importar acentos
     */
    normalizeText(text) {
        return text
            .toLowerCase()
            .normalize("NFD") // Descompone caracteres con tildes
            .replace(/[\u0300-\u036f]/g, "") // Remueve los diacríticos (tildes)
            .trim();
    }

    filterOptions(searchTerm) {
        const normalizedSearchTerm = this.normalizeText(searchTerm);
        let hasResults = false;
        let visibleOptions = [];

        this.options.forEach((option, index) => {
            const originalText = option.dataset.text;
            const normalizedText = this.normalizeText(originalText);
            const matches = normalizedText.includes(normalizedSearchTerm);

            option.style.display = matches ? 'block' : 'none';
            if (matches) {
                hasResults = true;
                visibleOptions.push(index);
            }
        });

        // Resetear selección
        this.selectedIndex = -1;
        this.removeHighlight();

        // Mostrar mensaje si no hay resultados
        this.showNoResults(!hasResults && normalizedSearchTerm.length > 0);
        this.showDropdown();

        return visibleOptions;
    }

    handleKeydown(e) {
        const visibleOptions = this.getVisibleOptions();

        switch(e.key) {
            case 'ArrowDown':
                e.preventDefault();
                this.selectedIndex = Math.min(this.selectedIndex + 1, visibleOptions.length - 1);
                this.highlightOption(visibleOptions[this.selectedIndex]);
                this.showDropdown();
                break;

            case 'ArrowUp':
                e.preventDefault();
                this.selectedIndex = Math.max(this.selectedIndex - 1, 0);
                this.highlightOption(visibleOptions[this.selectedIndex]);
                this.showDropdown();
                break;

            case 'Enter':
                e.preventDefault();
                if (this.selectedIndex >= 0 && visibleOptions[this.selectedIndex] !== undefined) {
                    const optionIndex = visibleOptions[this.selectedIndex];
                    this.selectOption(this.options[optionIndex], optionIndex);
                }
                break;

            case 'Escape':
                this.hideDropdown();
                break;
        }
    }

    getVisibleOptions() {
        return this.options
            .map((option, index) => option.style.display !== 'none' ? index : null)
            .filter(index => index !== null);
    }

    highlightOption(index) {
        this.removeHighlight();
        if (index >= 0 && this.options[index]) {
            this.options[index].classList.add('selected');
        }
    }

    removeHighlight() {
        this.options.forEach(opt => opt.classList.remove('selected'));
    }

    showNoResults(show) {
        let noResultsDiv = this.dropdown.querySelector('.no-results');
        if (show && !noResultsDiv) {
            noResultsDiv = document.createElement('div');
            noResultsDiv.className = 'no-results';
            noResultsDiv.textContent = 'No se encontraron resultados';
            this.dropdown.appendChild(noResultsDiv);
        } else if (!show && noResultsDiv) {
            noResultsDiv.remove();
        }
    }

    selectOption(option, index) {
        const value = option.dataset.value;
        const text = option.dataset.text;

        // Actualizar input y select oculto
        this.searchInput.value = text;
        this.hiddenSelect.value = value;

        // Marcar como seleccionado
        this.removeHighlight();
        option.classList.add('selected');

        this.hideDropdown();

        // Disparar evento change para validaciones
        this.hiddenSelect.dispatchEvent(new Event('change', { bubbles: true }));
    }

    showDropdown() {
        this.dropdown.style.display = 'block';
    }

    hideDropdown() {
        this.dropdown.style.display = 'none';
        this.showNoResults(false);
        this.selectedIndex = -1;
        this.removeHighlight();
    }

    setInitialValue() {
        const selectedValue = this.hiddenSelect.value;
        if (selectedValue) {
            const selectedOption = this.options.find(opt => opt.dataset.value === selectedValue);
            if (selectedOption) {
                this.searchInput.value = selectedOption.dataset.text;
                selectedOption.classList.add('selected');
            }
        }
    }

    // Método público para limpiar el select
    clear() {
        this.searchInput.value = '';
        this.hiddenSelect.value = '';
        this.removeHighlight();
        this.hideDropdown();
    }

    // Método público para establecer un valor
    setValue(value) {
        const option = this.options.find(opt => opt.dataset.value === value);
        if (option) {
            this.selectOption(option, this.options.indexOf(option));
        }
    }
}

/**
 * Función para inicializar todos los selects filtrables
 */
function initializeFilterableSelects() {
    // Crear instancias de selects filtrables
    const selectConfigs = [
        { search: 'aprendiz-search', dropdown: 'aprendiz-dropdown', select: 'aprendiz-select' },
        { search: 'ficha-search', dropdown: 'ficha-dropdown', select: 'ficha-select' },
        { search: 'empresa-search', dropdown: 'empresa-dropdown', select: 'empresa-select' },
        { search: 'evaluador-search', dropdown: 'evaluador-dropdown', select: 'evaluador-select' }
    ];

    const filterableSelects = {};

    selectConfigs.forEach(config => {
        if (document.getElementById(config.search)) {
            filterableSelects[config.search] = new FilterableSelect(
                config.search,
                config.dropdown,
                config.select
            );
        }
    });

    // Hacer disponible globalmente para debugging
    window.filterableSelects = filterableSelects;
}

/**
 * Inicializar selects filtrables cuando el DOM esté listo
 */
document.addEventListener('DOMContentLoaded', function() {
    initializeFilterableSelects();
});