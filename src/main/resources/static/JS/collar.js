async function displayView(id) {
    // Fetch the clicked wine from the server
    const response = await fetch('/viewWine?id=' + id);

    if (response.ok) {
        const clickedWine = await response.json(); // Parse the JSON response

        // Get the overlay and view modal elements
        const overlay = document.getElementById("overlay");
        const viewPopUp = document.getElementById("view");

        viewPopUp.innerHTML = `
            <img src="${clickedWine.imageUrl}" alt="Image of ${clickedWine.name}" />
            <h5 class="card-title">${clickedWine.name} ${clickedWine.vintage}</h5>
            <p class="card-text">Localization: ${clickedWine.country}, ${clickedWine.region}</p>
            <p class="card-text">Winery: ${clickedWine.winery}</p>
            <div class="buttons">
                <button onclick="cancelView()">Cancel</button>
           </div>
`;


        // Log the clicked wine to the console for debugging
        console.log(clickedWine);

        // Show the modal by adding the 'active' class
        overlay.classList.add("active");
        viewPopUp.classList.add("active");
    } else {
        console.error("Error fetching wine data");
    }
}


function cancelView() {
    const overlay = document.getElementById("overlay");
    const viewPopUp = document.getElementById("view");
    console.log("Closing modal...");

    overlay.classList.remove("active");
    viewPopUp.classList.remove("active");
}

// Close popup when clicking outside of it
document.getElementById("overlay").addEventListener("click", function (event) {
    if (!event.target.closest("#view")) {
        cancelView();
    }
});
document.getElementById("editOverlay").addEventListener("click", function (event) {
    if (!event.target.closest("#edit")) {
        cancelEdit();
    }
});

async function displayEdit(id) {
    // Fetch the clicked wine details
    const response = await fetch('/viewWine?id=' + id);

    if (response.ok) {
        const clickedWine = await response.json();

        // Get the overlay and edit modal elements
        const overlay = document.getElementById("editOverlay");
        const editPopUp = document.getElementById("edit");

        // Populate the modal with a form
        editPopUp.innerHTML = `
            <h5>Edit Wine</h5>
            <form id="editForm">
                <div class="mb-3">
                    <label for="name" class="form-label">Name</label>
                    <input type="text" id="name" name="name" class="form-control" value="${clickedWine.name}">
                </div>
                <div class="mb-3">
                    <label for="vintage" class="form-label">Vintage</label>
                    <input type="text" id="vintage" name="vintage" class="form-control" value="${clickedWine.vintage}">
                </div>
                <div class="mb-3">
                    <label for="imageUrl" class="form-label">Image URL</label>
                    <input type="text" id="imageUrl" name="imageUrl" class="form-control" value="${clickedWine.imageUrl}">
                </div>
                <div class="mb-3">
                    <label for="country" class="form-label">Country</label>
                    <input type="text" id="country" name="country" class="form-control" value="${clickedWine.country}">
                </div>
                <div class="mb-3">
                    <label for="region" class="form-label">Region</label>
                    <input type="text" id="region" name="region" class="form-control" value="${clickedWine.region}">
                </div>
                <div class="mb-3">
                    <label for="winery" class="form-label">Winery</label>
                    <input type="text" id="winery" name="winery" class="form-control" value="${clickedWine.winery}">
                </div>
                <div class="mb-3">
                    <label for="price" class="form-label">Price</label>
                    <input type="text" id="price" name="price" class="form-control" value="${clickedWine.price}">
                </div>
                <div class="mb-3">
                    <label for="wineType" class="form-label">Wine Type</label>
                    <select id="wineType" name="wineType" class="form-control">
                        <option value="${clickedWine.wineType}" selected>${clickedWine.wineType}</option>
                        <option value="RED">RED</option>
                        <option value="WHITE">WHITE</option>
                        <option value="SPARKLING">SPARKLING</option>
                        <option value="ROSE">ROSE</option>
                        <option value="DESSERT">DESSERT</option>
                        <option value="PORT">PORT</option>
                    </select>
                </div>
                <div class="buttons">
                    <button type="button" onclick="saveEdit(${clickedWine.id})">Save</button>
                    <button type="button" onclick="cancelEdit()">Cancel</button>
                </div>
            </form>

        `;

        // Show the modal
        overlay.classList.add("active");
        editPopUp.classList.add("active");
    } else {
        console.error("Error fetching wine data for edit");
    }
}

function cancelEdit() {
    const overlay = document.getElementById("editOverlay");
    const editPopUp = document.getElementById("edit");
    overlay.classList.remove("active");
    editPopUp.classList.remove("active");
}

// Save the Edited Data
async function saveEdit(id) {
    const form = document.getElementById("editForm");
    const updatedWine = {
        name: form.name.value,
        vintage: form.vintage.value,
        imageUrl: form.imageUrl.value,
        country: form.country.value,
        region: form.region.value,
        winery: form.winery.value,
        price: form.price.value,
        wineType: form.wineType.value
    };

    const response = await fetch('/updateWine', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ id, ...updatedWine })
    });

    if (response.ok) {
        console.log("Wine updated successfully");
        cancelEdit();
        location.reload()
    } else {
        console.error("Error updating wine");
    }
}
