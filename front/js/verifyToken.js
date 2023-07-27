var buttonSair = document.getElementById('button-sair');

buttonSair.addEventListener('click', (e) => {
    const accessToken = localStorage.getItem('token');

    if (accessToken) {
        localStorage.clear();
        window.location.assign('../index.html');
    }

    e.preventDefault();
})