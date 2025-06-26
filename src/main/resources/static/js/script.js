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


/*GRAFICOOOO TORTTAAAAAA=================================

// JavaScript mejorado para el gráfico circular
    document.addEventListener('DOMContentLoaded', function() {
        initProgressChart();
    });

    function initProgressChart() {
        const progressFill = document.getElementById('progress-fill');
        const progressText = document.getElementById('progress-text');

        if (!progressFill || !progressText) {
            console.error('Elementos del gráfico no encontrados');
            return;
        }

        // Obtener el porcentaje de progreso del texto
        let progress = 0;
        const progressTextContent = progressText.textContent;

        if (progressTextContent) {
            const match = progressTextContent.match(/(\d+)/);
            if (match) {
                progress = parseInt(match[1]);
            }
        }

        // Validar que el progreso esté en el rango correcto
        progress = Math.min(Math.max(progress, 0), 100);

        // Actualizar el texto del progreso
        progressText.textContent = progress + '%';

        // Aplicar el progreso usando CSS custom properties
        const angle = (progress / 100) * 360;
        progressFill.style.setProperty('--progress-angle', angle + 'deg');

        // Animación suave
        progressFill.style.background = `conic-gradient(#39A900 0deg, #39A900 ${angle}deg, transparent ${angle}deg)`;
    }

    // Función para ocultar/mostrar barra lateral
    function ocultarBarra() {
        const sidebar = document.getElementById('sidebar');
        const mainContent = document.getElementById('main-content');
        const body = document.body;

        if (sidebar.style.left === '-220px') {
            sidebar.style.left = '0';
            body.style.marginLeft = '220px';
        } else {
            sidebar.style.left = '-220px';
            body.style.marginLeft = '0';
        }
    }*/






//  <!--GRAFICCOOOOO-->
// resources/static/js/grafico.js

document.addEventListener('DOMContentLoaded', function() {
    initDynamicProgressChart();
});

function initDynamicProgressChart() {
    const progressFill = document.getElementById('progress-fill');
    const progressText = document.getElementById('progress-text');

    if (!progressFill || !progressText) {
        console.error('Elementos del gráfico no encontrados');
        return;
    }

    // Función para actualizar el progreso
    function updateProgress(newPercentage) {
        const progress = Math.min(Math.max(newPercentage, 0), 100);
        progressText.textContent = `${progress}%`;
        animateProgress(progressFill, progress);
    }

    // Animación mejorada
    function animateProgress(element, targetPercent) {
        // Si el progreso es 0%, ocultamos completamente la barra
        if (targetPercent === 0) {
            element.style.background = `conic-gradient(#39A900 0deg, #39A900 0deg, transparent 0deg)`; // Establecer a 0%
            element.style.display = 'block'; // Mantener visible pero vacío
            return;
        }

        element.style.display = 'block';
        const targetAngle = (targetPercent / 100) * 360;
        let currentAngle = 0;
        const duration = 1500;
        const startTime = performance.now();

        function updateAnimation() {
            const elapsed = performance.now() - startTime;
            const progress = Math.min(elapsed / duration, 1);
            currentAngle = targetAngle * easeOutQuart(progress);

            // Aplicamos el ángulo calculado usando conic-gradient para el efecto de arco
            element.style.background = `conic-gradient(#39A900 0deg, #39A900 ${currentAngle}deg, transparent ${currentAngle}deg)`;

            if (progress < 1) {
                requestAnimationFrame(updateAnimation);
            }
        }

        updateAnimation();
    }

    // Función de easing
    function easeOutQuart(t) {
        return 1 - Math.pow(1 - t, 4);
    }

    // Carga inicial con el porcentaje del texto
    const initialText = progressText.textContent;
    const initialPercent = initialText ? parseInt(initialText.replace('%', '')) || 0 : 0;
    updateProgress(initialPercent);

    // Para actualizaciones dinámicas
    window.updateProgressChart = updateProgress;
}

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
 * Versión mejorada con mejor posicionamiento
 */
class FilterableSelect {
    constructor(searchInputId, dropdownId, hiddenSelectId) {
        this.searchInput = document.getElementById(searchInputId);
        this.dropdown = document.getElementById(dropdownId);
        this.hiddenSelect = document.getElementById(hiddenSelectId);
        this.container = this.searchInput?.closest('.filterable-select-container');

        if (!this.searchInput || !this.dropdown || !this.hiddenSelect || !this.container) {
            console.warn(`FilterableSelect: No se encontraron elementos para ${searchInputId}`);
            return;
        }

        this.options = Array.from(this.dropdown.querySelectorAll('.filterable-option'));
        this.selectedIndex = -1;
        this.isOpen = false;

        this.init();
    }

    init() {
        // IMPORTANTE: Ocultar dropdown inicialmente
        this.hideDropdown();

        // Configurar eventos
        this.searchInput.addEventListener('input', (e) => this.filterOptions(e.target.value));
        this.searchInput.addEventListener('focus', () => this.showDropdown());

        // Mejorar el manejo del blur
        this.searchInput.addEventListener('blur', (e) => {
            // Verificar si el click fue dentro del dropdown
            setTimeout(() => {
                if (!this.dropdown.matches(':hover') && !this.container.contains(document.activeElement)) {
                    this.hideDropdown();
                }
            }, 150);
        });

        // Navegación con teclado
        this.searchInput.addEventListener('keydown', (e) => this.handleKeydown(e));

        // Click en opciones con mejor manejo
        this.options.forEach((option, index) => {
            option.addEventListener('mousedown', (e) => {
                // Prevenir que el input pierda el foco antes del click
                e.preventDefault();
            });

            option.addEventListener('click', (e) => {
                e.stopPropagation();
                this.selectOption(option, index);
            });

            option.addEventListener('mouseenter', () => this.highlightOption(index));
        });

        // Mejorar el manejo de clicks fuera
        document.addEventListener('click', (e) => {
            if (!this.container.contains(e.target)) {
                this.hideDropdown();
            }
        });

        // Manejar redimensionamiento de ventana
        window.addEventListener('resize', () => {
            if (this.isOpen) {
                this.repositionDropdown();
            }
        });

        // Configurar valor inicial si existe
        this.setInitialValue();
    }

    /**
     * Función para normalizar texto removiendo tildes y caracteres especiales
     */
    normalizeText(text) {
        return text
            .toLowerCase()
            .normalize("NFD")
            .replace(/[\u0300-\u036f]/g, "")
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

        // Mostrar dropdown si hay término de búsqueda o está enfocado
        if (normalizedSearchTerm.length > 0 || document.activeElement === this.searchInput) {
            this.showDropdown();
        }

        return visibleOptions;
    }

    handleKeydown(e) {
        const visibleOptions = this.getVisibleOptions();

        switch(e.key) {
            case 'ArrowDown':
                e.preventDefault();
                if (!this.isOpen) {
                    this.showDropdown();
                }
                this.selectedIndex = Math.min(this.selectedIndex + 1, visibleOptions.length - 1);
                this.highlightOption(visibleOptions[this.selectedIndex]);
                break;

            case 'ArrowUp':
                e.preventDefault();
                if (!this.isOpen) {
                    this.showDropdown();
                }
                this.selectedIndex = Math.max(this.selectedIndex - 1, 0);
                this.highlightOption(visibleOptions[this.selectedIndex]);
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
                this.searchInput.blur();
                break;

            case 'Tab':
                // Permitir navegación con Tab
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
            // Scroll automático para mantener la opción visible
            this.scrollToOption(this.options[index]);
        }
    }

    scrollToOption(option) {
        const dropdownRect = this.dropdown.getBoundingClientRect();
        const optionRect = option.getBoundingClientRect();

        if (optionRect.bottom > dropdownRect.bottom) {
            this.dropdown.scrollTop += optionRect.bottom - dropdownRect.bottom;
        } else if (optionRect.top < dropdownRect.top) {
            this.dropdown.scrollTop -= dropdownRect.top - optionRect.top;
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

        // Disparar evento personalizado
        this.container.dispatchEvent(new CustomEvent('optionSelected', {
            detail: { value, text, option }
        }));
    }

    showDropdown() {
        if (this.isOpen) return;

        // Cerrar otros dropdowns abiertos
        this.closeOtherDropdowns();

        // Marcar contenedor como activo
        this.container.classList.add('active');

        // Mostrar dropdown
        this.dropdown.classList.add('show');
        this.isOpen = true;

        // Reposicionar si es necesario
        this.repositionDropdown();
    }

    hideDropdown() {
        if (!this.isOpen) return;

        // Marcar contenedor como inactivo
        this.container.classList.remove('active');

        // Ocultar dropdown
        this.dropdown.classList.remove('show');
        this.showNoResults(false);
        this.selectedIndex = -1;
        this.removeHighlight();
        this.isOpen = false;
    }

    repositionDropdown() {
        // Verificar si el dropdown se sale de la pantalla
        const rect = this.container.getBoundingClientRect();
        const dropdownHeight = 200; // max-height del dropdown
        const spaceBelow = window.innerHeight - rect.bottom;

        if (spaceBelow < dropdownHeight && rect.top > dropdownHeight) {
            // Mostrar arriba del input
            this.dropdown.style.top = 'auto';
            this.dropdown.style.bottom = '100%';
            this.dropdown.style.borderRadius = '0.375rem 0.375rem 0 0';
        } else {
            // Mostrar debajo del input (normal)
            this.dropdown.style.top = 'calc(100% + 1px)';
            this.dropdown.style.bottom = 'auto';
            this.dropdown.style.borderRadius = '0 0 0.375rem 0.375rem';
        }
    }

    closeOtherDropdowns() {
        // Cerrar otros dropdowns que puedan estar abiertos
        document.querySelectorAll('.filterable-select-container.active').forEach(container => {
            if (container !== this.container) {
                const otherDropdown = container.querySelector('.filterable-dropdown');
                if (otherDropdown) {
                    container.classList.remove('active');
                    otherDropdown.classList.remove('show');
                }
            }
        });
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

    // Método público para obtener el valor actual
    getValue() {
        return this.hiddenSelect.value;
    }

    // Método público para obtener el texto actual
    getText() {
        return this.searchInput.value;
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
        { search: 'evaluador-search', dropdown: 'evaluador-dropdown', select: 'evaluador-select' },
        { search: 'programa-search', dropdown: 'programa-dropdown', select: 'programa-select' }
    ];

    const filterableSelects = {};

    selectConfigs.forEach(config => {
        if (document.getElementById(config.search)) {
            filterableSelects[config.search] = new FilterableSelect(
                config.search,
                config.dropdown,
                config.select
            );
            console.log(`Inicializado select filtrable: ${config.search}`);
        }
    });

    // Hacer disponible globalmente para debugging
    window.filterableSelects = filterableSelects;

    console.log('Selects filtrables inicializados:', Object.keys(filterableSelects));
}

/**
 * Inicializar selects filtrables cuando el DOM esté listo
 */
document.addEventListener('DOMContentLoaded', function() {
    // Agregar un pequeño delay para asegurar que todo esté cargado
    setTimeout(() => {
        initializeFilterableSelects();
    }, 100);
});

// También inicializar si el DOM ya está cargado
if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', initializeFilterableSelects);
} else {
    initializeFilterableSelects();
}