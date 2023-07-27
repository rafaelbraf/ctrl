getIntegrationsOfUser();

async function getIntegrationsOfUser() {
    const accessToken = localStorage.getItem('token');
    let userId = await getUserId(accessToken);

    const urlToGetIntegrationsOfUser = `http://localhost:8000/api/integrations/userid/${userId}`;

    const paramsToResquest = {
        headers: {
            "Content-Type": "application/json; charset=utf-8",
        },
        method: "GET"
    };

    let integrations;

    await fetch(urlToGetIntegrationsOfUser, paramsToResquest)
        .then(response => { return response.json() })
        .then(data => { integrations = data })
        .catch(error => { console.log(error) });

    const tBodyIntegrations = document.getElementById('tbody-integracoes');

    integrations.forEach(element => {
        tBodyIntegrations.innerHTML += `<tr data-bs-toggle="modal" data-bs-target="#modalDetalhesIntegracao" data-detalhe-titulo="${element.title}"`
            + `data-detalhe-url="${element.url}" data-detalhe-porta="${element.port}" data-detalhe-intervalo="${element.interval}" data-detalhe-descricao="${element.description}">`
            + `<td>${element.title}</td>`
            + `<td>${element.url}</td>`
            + `<td>${element.port}</td>`
            + `<td>${element.interval}</td>`
            + `<td>${element.description}</td>`
            + `<td>${element.createdAt}</td>`
            + `<td>${element.updatedAt}</td>`
            + `<td>${element.monitoringAt}</td>`
            + `<td>${element.nextMonitoringAt}</td>`
            + `</tr>`;
    });
}

async function adicionarIntegracao() {
    if (titulo && url) {        
        const accessToken = localStorage.getItem('token');
        let userId = await getUserId(accessToken);

        createIntegration(userId);

        // Fechar o modal após adicionar a integração (opcional)
        const modal = bootstrap.Modal.getInstance(document.getElementById('modalNovaIntegracao'));
        modal.hide();

        window.location.reload();
    } else {
        alert("Para salvar integração é obrigatório que Título e Url estejam preenchidos.\n\nPor favor, preencha esses dois campos e tente novamente.");
    }
}

async function getUserId(accessToken) {
    let userId;

    const paramsToGetUser = {
        headers: {
            "Authorization": `Bearer ${accessToken}`
        },
        method: "GET"
    };

    const urlToGetUser = "http://localhost:8080/api/users/me";

    await fetch(urlToGetUser, paramsToGetUser)
        .then(response => { 
            return response.json();
        })
        .then(data => { 
            userId = data.data.user._id;            
        })
        .catch(error => { console.log(error) });
    
    return userId;
}

async function createIntegration(userId) {
    const titulo = document.getElementById('titulo').value;
    const url = document.getElementById('url').value;
    const porta = document.getElementById('porta').value;
    const intervalo = document.getElementById('intervalSelect');
    const intervaloValue = intervalo.options[intervalo.selectedIndex].text;
    const descricao = document.getElementById('descricao').value;

    const urlToSaveIntegration = "http://localhost:8000/api/integrations/create";

    const integrationData = {
        title: titulo,
        url: url,
        port: porta,
        interval: intervaloValue,
        description: descricao,
        userId: userId,
    };

    const paramsToRequest = {
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        },
        method: "POST",
        body: JSON.stringify(integrationData)
    };

    let integrationSaved = true;

    fetch(urlToSaveIntegration, paramsToRequest)
        .then(response => { return response.json() })
        .then(data => { console.log(data) })
        .catch(error => { console.log(error) });
}