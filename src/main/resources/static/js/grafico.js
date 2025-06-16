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