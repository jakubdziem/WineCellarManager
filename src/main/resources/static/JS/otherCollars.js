function redirectToSearch(event) {
    event.preventDefault();
    const inputValue = document.getElementById("searchInput").value;
    if (inputValue.trim() !== "") {
        console.log(`/otherCollars/${encodeURIComponent(inputValue)}`)
        window.location.href = `/otherCollars/${encodeURIComponent(inputValue)}`;
    } else {
        alert("Please enter a search term.");
    }
}

document.getElementById("overlay").addEventListener("click", function (event) {
    if (!event.target.closest("#view")) {
        cancelView();
    }
});
document.getElementById("ratingOverlay").addEventListener("click", function (event) {
    if (!event.target.closest("#ratingPopUp")) {
        cancelRating();
    }
});
async function displayRatingPopup(id) {
    const response = await fetch(`/getWineRating?wineId=${id}`); // Adjust the endpoint accordingly

    if (response.ok) {
        const ratingData = await response.json(); // Expect { hasRating: true/false, ratingStars, flavour, aroma, agingTime, suggestedFoodPairings } }
        const overlay = document.getElementById("ratingOverlay");
        const ratingPopUp = document.getElementById("ratingPopUp");

        if (ratingData.hasRating) {
            ratingPopUp.innerHTML = `
                <div id="viewRatingDiv">
                    <p><strong>Stars:</strong> ${ratingData.ratingStars}</p>
                    <p><strong>Flavour:</strong> ${ratingData.flavour}</p>
                    <p><strong>Aroma:</strong> ${ratingData.aroma}</p>
                    <p><strong>Aging Time:</strong> ${ratingData.agingTime} years</p>
                    <p id ="lastInformationParagraph"><strong>Suggested Food Pairings:</strong> ${ratingData.suggestedFoodPairings}</p>
                    <button class = "cancel-button-rating" onclick="cancelRating()">Close</button>
                </div>
            `;
        }

        overlay.classList.add("active");
        ratingPopUp.classList.add("active");
    } else {
        console.error('Error fetching rating data');
    }
}
function cancelRating() {
    const overlay = document.getElementById("ratingOverlay");
    const ratingPopUp = document.getElementById("ratingPopUp");

    // Hide the modal
    overlay.classList.remove("active");
    ratingPopUp.classList.remove("active");
}

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


        console.log(clickedWine);

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