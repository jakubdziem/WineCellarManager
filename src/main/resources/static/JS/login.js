document.querySelector('form').addEventListener('submit', async (e) => {
    e.preventDefault();

    const nickname = document.getElementById('nickname').value.trim();
    const password = document.getElementById('password').value.trim();

    if (!nickname || !password) {
        alert('Please fill in both fields.');
        return;
    }

    const loginData = {
        nickname,
        password,
    };

    try {
        const response = await fetch('/api/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(loginData),
        });

        if (response.ok) {
            const data = await response.json();
            const token = `${data.tokenType}${data.accessToken}`;
            localStorage.setItem('token', token);
            localStorage.setItem('nickname', loginData.nickname);
            alert('Login successful!');
            window.location.href = '/home';
        } else {
            const error = await response.text();
            alert(`Login failed: ${error}`);
        }
    } catch (error) {
        console.error('Login error:', error);
        alert('An error occurred while logging in. Please try again.');
    }
});
