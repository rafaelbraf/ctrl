let buttonSignIn = document.getElementById('button-signin');

if (buttonSignIn) {
    buttonSignIn.addEventListener('click', (e) => {
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;

        if (email && password) {
            const urlLogin = "http://localhost:8080/api/auth/login";

            const userData = {
                email: email,
                password: password
            };

            const paramsToRequest = {
                headers: {
                    "Content-Type": "application/json; charset=utf-8"
                },
                body: JSON.stringify(userData),
                method: "POST"
            }

            fetch(urlLogin, paramsToRequest)
                .then(res => { return res.json() })
                .then(data => { 
                    const accessToken = data.accessToken;

                    if (accessToken) {
                        localStorage.setItem('token', accessToken);

                        window.location.assign('./pages/home.html');
                    }
                })
                .catch(err => { console.log(err) });
        }

        e.preventDefault();
    });
}