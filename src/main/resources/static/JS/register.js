document.querySelector('form').addEventListener('submit', async (e) => {
    e.preventDefault();

    const nickname = document.getElementById('nickname').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const repeatPassword = document.getElementById('repeatPassword').value;

    if (password !== repeatPassword) {
        alert('Passwords do not match!');
        return;
    }

    const registerData = {
        nickname,
        email,
        password,
    };

    try {
        const response = await fetch('/api/auth/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(registerData),
        });

        if (response.ok) {
            alert('Registration successful! Please log in.');
            window.location.href = '/login';
        } else {
            const error = await response.text();
            alert(`Error: ${error}`);
        }
    } catch (error) {
        console.error('Registration error:', error);
        alert('An error occurred while registering. Please try again.');
    }
});