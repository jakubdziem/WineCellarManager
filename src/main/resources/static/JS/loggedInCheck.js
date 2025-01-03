document.addEventListener('DOMContentLoaded', () => {
    // Check if token exists in localStorage
    const token = localStorage.getItem('token');

    if (token) {
        // Decode the token to extract user information
        const nickname = localStorage.getItem('nickname')

        // Update navbar for logged-in user
        document.querySelectorAll('.guest-options').forEach(el => el.classList.add('d-none')); // Hide sign-in/register
        const userOptions = document.querySelector('.user-options');
        userOptions.classList.remove('d-none'); // Show user dropdown
        document.getElementById('userName').textContent = nickname; // Set user name

        // Add event listener for logout
        document.getElementById('logoutBtn').addEventListener('click', () => {
            localStorage.removeItem('token'); // Remove the token from storage
            localStorage.removeItem('nickname');
            alert('Logged out successfully.');
            location.reload(); // Reload the page to update the navbar
        });
    }
});
