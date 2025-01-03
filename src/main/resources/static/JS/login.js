document.querySelector('form').addEventListener('submit', async (e) => {
    e.preventDefault();

    // Get the nickname and password input values
    const nickname = document.getElementById('nickname').value.trim();
    const password = document.getElementById('password').value.trim();

    // Ensure inputs are not empty
    if (!nickname || !password) {
        alert('Please fill in both fields.');
        return;
    }

    // Prepare login data
    const loginData = {
        nickname,
        password,
    };

    try {
        // Make the login API request
        const response = await fetch('/api/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(loginData),
        });

        // Handle the API response
        if (response.ok) {
            const data = await response.json(); // Extract the response data
            const token = `${data.tokenType}${data.accessToken}`; // Combine token type and access token
            localStorage.setItem('token', token); // Store the full token in localStorage
            localStorage.setItem('nickname', loginData.nickname);
            alert('Login successful!');
            window.location.href = '/home'; // Redirect to the home page
        } else {
            // Parse and display error message from the server
            const error = await response.text();
            alert(`Login failed: ${error}`);
        }
    } catch (error) {
        // Handle network or unexpected errors
        console.error('Login error:', error);
        alert('An error occurred while logging in. Please try again.');
    }
});
