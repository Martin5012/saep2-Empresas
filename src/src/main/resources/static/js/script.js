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
