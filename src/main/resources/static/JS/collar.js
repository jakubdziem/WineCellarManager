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
            <p class="card-text">Price: ${clickedWine.price}</p>
           
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
document.getElementById("deleteDecisionOverlay").addEventListener("click", function (event) {
    if (!event.target.closest("#deleteDecision")) {
        cancelDelete();
    }
});

function restoreScrollPosition() {
    const savedScrollPosition = sessionStorage.getItem('scrollPosition');
    if (savedScrollPosition !== null) {
        // Restore the scroll position
        window.scrollTo(0, savedScrollPosition);
        sessionStorage.removeItem('scrollPosition');  // Clear saved position
    }
}

// Call this function when the page is loaded
window.onload = function() {
    restoreScrollPosition();
};
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
    const token = localStorage.getItem('token');
    const response = await fetch('/updateWine', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': token
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

async function rateWine(id, stars) {
    // Send POST request to save the rating
    const token = localStorage.getItem('token');
    const response = await fetch('/rateWine', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': token
        },
        body: JSON.stringify({ wineId: id, ratingStars: stars })
    });

    if (response.ok) {
        console.log(`Wine ${id} rated with ${stars} stars`);
        location.reload(); // Refresh the page to reflect the updated rating
    } else {
        console.error('Error saving rating');
    }
}

async function displayRatingPopup(id) {
    // Fetch the wine details and its rating
    const response = await fetch(`/getWineRating?wineId=${id}`); // Adjust the endpoint accordingly

    if (response.ok) {
        const ratingData = await response.json(); // Expect { hasRating: true/false, ratingStars, flavour, aroma, agingTime, suggestedFoodPairings } }
        const overlay = document.getElementById("ratingOverlay");
        const ratingPopUp = document.getElementById("ratingPopUp");

        if (ratingData.hasRating) {
            // Display the existing rating (non-editable)
            ratingPopUp.innerHTML = `
                <div id="viewRatingDiv">
                    <p><strong>Stars:</strong> ${ratingData.ratingStars}</p>
                    <p><strong>Flavour:</strong> ${ratingData.flavour}</p>
                    <p><strong>Aroma:</strong> ${ratingData.aroma}</p>
                    <p><strong>Aging Time:</strong> ${ratingData.agingTime} years</p>
                    <p id="lastInformationParagraph"><strong>Suggested Food Pairings:</strong> ${ratingData.suggestedFoodPairings}</p>
                    <button class = "cancel-button-rating" onclick="cancelRating()">Close</button>
                    <button type="button" class="delete-button" onclick="deleteRating(${ratingData.id})">
                    🗑️
                    </button>
                </div>
            `;
        } else {
            // Display the form for submitting a rating
            ratingPopUp.innerHTML = `
                <div>
                    <h3>Submit Your Rating</h3>
                    <form id="ratingForm">
                        <div class="form-group">
                            <label for="ratingStars">Stars (1-5):</label>
                            <input type="number" id="ratingStars" name="ratingStars" min="1" max="5" required />
                        </div>
            
                        <div class="form-group">
                            <label for="flavour">Flavour:</label>
                            <select id="flavour" name="flavour">
                                <option value="FRUITY" selected >Rich in fruit flavors</option>
                                <option value="OAKY">Aged in oak barrels</option>
                                <option value="SPICY">Hints of spices</option>
                                <option value="EARTHY">Rich in earthy notes</option>
                                <option value="FLORAL">Floral aroma</option>
                                <option value="CITRUS">Citrus notes</option>
                                <option value="MINERAL">Mineral-rich</option>
                                <option value="NUTTY">Nutty aroma</option>
                            </select>
                        </div>
            
                        <div class="form-group">
                            <label for="aroma">Aroma:</label>
                            <input type="text" id="aroma" name="aroma" placeholder="E.g., Rich, Floral" required />
                        </div>
            
                        <div class="form-group">
                            <label for="agingTime">Aging Time (years):</label>
                            <input type="number" id="agingTime" name="agingTime" min="0" required />
                        </div>
            
                        <div class="form-group">
                            <label for="suggestedFoodPairings">Suggested Food Pairings:</label>
                            <input
                                type="text"
                                id="suggestedFoodPairings"
                                name="suggestedFoodPairings"
                                placeholder="E.g., Cheese, Red Meat"
                                required
                            />
                        </div>
            
                        <div class="form-group">
                            <button type="submit">Submit</button>
                            <button type="button" onclick="cancelRating()">Cancel</button>
                        </div>
                    </form>
                </div>
            `;

            // Add form submission logic
            const ratingForm = document.getElementById('ratingForm');
            ratingForm.addEventListener('submit', async function (event) {
                event.preventDefault();

                // Extract form values
                const ratingStars = document.getElementById('ratingStars').value.trim();
                const flavour = document.getElementById('flavour').value.trim();
                const aroma = document.getElementById('aroma').value.trim();
                const agingTime = document.getElementById('agingTime').value.trim();
                const suggestedFoodPairings = document.getElementById('suggestedFoodPairings').value.trim();

                const errors = [];

                if (ratingStars < 1 || ratingStars > 5) {
                    errors.push("Stars must be between 1 and 5.");
                }
                if (aroma.length > 100) {
                    errors.push("Aroma description must not exceed 100 characters.");
                }
                if (aroma.length <= 3) {
                    errors.push("Aroma description must be longer than 3 characters.");
                }
                if (isNaN(agingTime) || agingTime < 0 || agingTime > 1000) {
                    errors.push("Aging Time must be a number between 0 and 1000.");
                }
                if (suggestedFoodPairings.length > 100) {
                    errors.push("Suggested Food Pairings must not exceed 100 characters.");
                }
                if (suggestedFoodPairings.length <= 3) {
                    errors.push("Suggested Food Pairings must be longer than 3 characters.");
                }
                if (isNaN(agingTime) || agingTime < 0 || agingTime > 1000) {
                    errors.push("Aging Time must be a number between 0 and 1000.");
                }

                if (errors.length > 0) {
                    alert(errors.join("\n")); // Display validation errors
                    return;
                }

                // Send the rating to the server
                const token = localStorage.getItem('token');
                const response = await fetch('/submitWineRating', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': token
                    },
                    body: JSON.stringify({
                        wineId: id,
                        ratingStars: parseInt(ratingStars),
                        flavour: flavour,
                        aroma: aroma,
                        agingTime: parseInt(agingTime),
                        suggestedFoodPairings: suggestedFoodPairings,
                    }),
                });

                if (response.ok) {
                    console.log('Rating submitted successfully!');
                    cancelRating(); // Close the modal
                    location.reload(); // Reload the page to reflect changes
                } else {
                    console.error('Error submitting rating');
                }
            });
        }

        // Show the modal
        overlay.classList.add("active");
        ratingPopUp.classList.add("active");
    } else {
        console.error('Error fetching rating data');
    }
}

async function deleteRating(id) {
    const token = localStorage.getItem('token');
    const response = await fetch('/deleteRating?id=' + id, {
        headers:
            {
                'Authorization': token
            },
        method: 'DELETE'
    });

    if (response.ok) {
        // Successfully deleted the wine, close the modal
        console.log("Rating deleted successfully");
        cancelRating();

        // Optionally, refresh the page or remove the deleted wine from the DOM
        location.reload();
    } else {
        console.error("Error deleting rating");
    }
}
function cancelRating() {
    const overlay = document.getElementById("ratingOverlay");
    const ratingPopUp = document.getElementById("ratingPopUp");

    // Hide the modal
    overlay.classList.remove("active");
    ratingPopUp.classList.remove("active");
}

// Close the popup when clicking outside of it
document.getElementById("ratingOverlay").addEventListener("click", function (event) {
    if (!event.target.closest("#ratingPopUp")) {
        cancelRating();
    }
});


function deleteWine(id) {
    // Get the overlay and delete confirmation modal elements
    const overlay = document.getElementById("deleteDecisionOverlay");
    const deletePopUp = document.getElementById("deleteDecision");

    // Populate the delete confirmation modal
    deletePopUp.innerHTML = `
        <h5>Are you sure you want to delete this wine?</h5>
        <div class="buttons">
            <button type="button" onclick="confirmDelete(${id})">Yes, Delete</button>
            <button type="button" onclick="cancelDelete()">Cancel</button>
        </div>
    `;

    // Show the modal by adding the 'active' class
    overlay.classList.add("active");
    deletePopUp.classList.add("active");
}

// Cancel Delete Action
function cancelDelete() {
    const overlay = document.getElementById("deleteDecisionOverlay");
    const deletePopUp = document.getElementById("deleteDecision");

    // Hide the modal by removing the 'active' class
    overlay.classList.remove("active");
    deletePopUp.classList.remove("active");
}

// Confirm Delete Action
async function confirmDelete(id) {
    const token = localStorage.getItem('token');
    // Send a delete request to the server
    const response = await fetch('/deleteWine?id=' + id, {
        headers: {
            'Authorization': token
        },
        method: 'DELETE'
    });

    if (response.ok) {
        // Successfully deleted the wine, close the modal
        console.log("Wine deleted successfully");
        cancelDelete();

        // Optionally, refresh the page or remove the deleted wine from the DOM
        location.reload();
    } else {
        console.error("Error deleting wine");
    }
}
async function displayAddWineForm(id) {
    // Get the overlay and add modal elements
    const overlay = document.getElementById("addOverlay");
    const addPopUp = document.getElementById("add");

    // Populate the modal with a form for adding a new wine
    addPopUp.innerHTML = `
        <h5>Add Wine</h5>
        <form id="addWineForm">
            <div class="mb-3">
                <label for="name" class="form-label">Name</label>
                <input type="text" id="name" name="name" class="form-control" placeholder="Enter wine name" required>
            </div>
            <div class="mb-3">
                <label for="vintage" class="form-label">Vintage</label>
                <input type="text" id="vintage" name="vintage" class="form-control" placeholder="Enter vintage year" required>
            </div>
            <div class="mb-3">
                <label for="imageUrl" class="form-label">Image URL</label>
                <input type="text" id="imageUrl" name="imageUrl" class="form-control" placeholder="Enter image URL" required>
            </div>
            <div class="mb-3">
                <label for="country" class="form-label">Country</label>
                <input type="text" id="country" name="country" class="form-control" placeholder="Enter country" required>
            </div>
            <div class="mb-3">
                <label for="region" class="form-label">Region</label>
                <input type="text" id="region" name="region" class="form-control" placeholder="Enter region" required>
            </div>
            <div class="mb-3">
                <label for="winery" class="form-label">Winery</label>
                <input type="text" id="winery" name="winery" class="form-control" placeholder="Enter winery name" required>
            </div>
            <div class="mb-3">
                <label for="price" class="form-label">Price</label>
                <input type="text" id="price" name="price" class="form-control" placeholder="Enter price" required>
            </div>
            <div class="mb-3">
                <label for="wineType" class="form-label">Wine Type</label>
                <select id="wineType" name="wineType" class="form-control" required>
                    <option value="RED" selected>RED</option>
                    <option value="WHITE">WHITE</option>
                    <option value="SPARKLING">SPARKLING</option>
                    <option value="ROSE">ROSE</option>
                    <option value="DESSERT">DESSERT</option>
                    <option value="PORT">PORT</option>
                </select>
            </div>
            <div class="buttons">
                <button type="submit">Add Wine</button>
                <button type="button" onclick="cancelAdd()">Cancel</button>
            </div>
        </form>
    `;

    // Show the modal
    overlay.classList.add("active");
    addPopUp.classList.add("active");

    // Add form submission logic
    const addWineForm = document.getElementById("addWineForm");
    addWineForm.addEventListener("submit", async function (event) {
        event.preventDefault();

        const name = document.getElementById('name').value.trim();
        const vintage = parseInt(document.getElementById('vintage').value.trim());
        const imageUrl = document.getElementById('imageUrl').value.trim();
        const country = document.getElementById('country').value.trim();
        const region = document.getElementById('region').value.trim();
        const winery = document.getElementById('winery').value.trim();
        const price = document.getElementById('price').value.trim();


        const currentYear = new Date().getFullYear();

        const errors = [];

        if (name.length > 50) {
            errors.push("Name must not exceed 50 characters.");
        }
        if (name.length < 3) {
            errors.push("Name must be at least 3 characters long.");
        }

        if (isNaN(vintage) || vintage < 1 || vintage > currentYear) {
            errors.push(`Vintage must be a number between 1 and ${currentYear}.`);
        }

        const urlPattern = /^(https?:\/\/[^\s$.?#].[^\s]*)$/;
        if (!urlPattern.test(imageUrl)) {
            errors.push("Image URL must be a valid URL.");
        }

        if (country.length > 100) {
            errors.push("Country must not exceed 100 characters.");
        }
        if (!/^[A-Z]/.test(country)) {
            errors.push("Country must start with a capital letter.");
        }
        if (country.length < 3) {
            errors.push("Country must be at least 3 characters long.");
        }

        if (region.length > 100) {
            errors.push("Region must not exceed 100 characters.");
        }
        if (region.length < 3) {
            errors.push("Region must be at least 3 characters long.");
        }

        if (winery.length > 100) {
            errors.push("Winery must not exceed 100 characters.");
        }
        if (winery.length < 3) {
            errors.push("Winery must be at least 3 characters long.");
        }

        const pricePattern = /^\$\d+(\.\d{1,2})?$/;
        if (!pricePattern.test(price)) {
            errors.push("Price must be in the format $8, $8.00, or $8.35.");
        }

        if (errors.length > 0) {
            alert(errors.join("\n"));
            return;
        }

        // Extract form values
        const newWine = {
            customerId: id,
            name,
            vintage,
            imageUrl,
            country,
            region,
            winery,
            price,
            wineType: document.getElementById('wineType').value,
        };

        // Send the data to the server
        const token = localStorage.getItem('token');
        const response = await fetch('/addWine', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': token
            },
            body: JSON.stringify(newWine)
        });

        if (response.ok) {
            console.log("Wine added successfully!");

            cancelAdd(); // Close the modal
            location.reload(); // Refresh the page to reflect changes
        } else {
            console.error("Error adding wine");
        }
    });
}

function cancelAdd() {
    const overlay = document.getElementById("addOverlay");
    const addPopUp = document.getElementById("add");
    overlay.classList.remove("active");
    addPopUp.classList.remove("active");
}



