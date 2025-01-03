document.addEventListener('DOMContentLoaded', () => {
    // Check if token exists in localStorage
    const token = localStorage.getItem('token');

    if (token) {
        // Decode the token to extract user information
        const nickname = localStorage.getItem('nickname');

        // Update navbar for logged-in user
        document.querySelectorAll('.guest-options').forEach(el => el.classList.add('d-none')); // Hide sign-in/register
        document.querySelectorAll('.user-options').forEach(el => el.classList.remove('d-none'));
        document.getElementById('userName').textContent = nickname;

        // Add event listener for logout
        document.getElementById('logoutBtn').addEventListener('click', () => {
            localStorage.removeItem('token'); // Remove the token from storage
            localStorage.removeItem('nickname');
            alert('Logged out successfully.');
            location.reload(); // Reload the page to update the navbar
        });

        if (window.location.pathname === '/home') {
            // Perform any actions specific to the /home page
            console.log('User is on the /home page and logged in.');
            const createAccountButton = document.getElementById("createAccountButton")
            createAccountButton.classList.add('d-none')
        }
    }
});
