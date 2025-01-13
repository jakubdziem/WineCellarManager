function redirectToSearch(event) {
    event.preventDefault(); // Prevent the default form submission
    const inputValue = document.getElementById("searchInput").value; // Get the value of the input field
    if (inputValue.trim() !== "") {
        console.log(`/otherCollars/${encodeURIComponent(inputValue)}`)
        window.location.href = `/otherCollars/${encodeURIComponent(inputValue)}`; // Redirect to the new URL with the input value
    } else {
        alert("Please enter a search term."); // Optional: Alert if the input is empty
    }
}