function displayView() {
    let overlay = document.getElementById("overlay");
    let viewPopUp = document.getElementById("view");
    overlay.classList.add("active");
    viewPopUp.classList.add("active");
}

function cancelView() {
    let overlay = document.getElementById("overlay");
    let viewPopUp = document.getElementById("view");
    overlay.classList.remove("active");
    viewPopUp.classList.remove("active");
}

function saveView() {
    console.log("Save clicked");
    cancelView();
}
