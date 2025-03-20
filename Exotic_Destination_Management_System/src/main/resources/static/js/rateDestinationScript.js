document.querySelector("#userMenu").addEventListener("click", () => {
    document.getElementById("dropdownMenu").classList.toggle("hidden");
});

document.addEventListener("DOMContentLoaded", function () {
    document.body.classList.remove("opacity-0");
});

// Function to show reservation details based on selected option in the dropdown
function showReservationDetails() {
    var select = document.getElementById("reservationSelect");
    var selectedOption = select.options[select.selectedIndex];

    if (select.value === "") {
        document.getElementById("reservationDetails").classList.add("hidden");
        document.getElementById("ratingForm").classList.add("hidden");
        document.getElementById("ratingMessage").classList.add("hidden");
        return;
    }

    document.getElementById("departureDate").textContent = selectedOption.getAttribute("data-departure");
    document.getElementById("returnDate").textContent = selectedOption.getAttribute("data-return");
    document.getElementById("price").textContent = selectedOption.getAttribute("data-price");
    document.getElementById("people").textContent = selectedOption.getAttribute("data-people");

    document.getElementById("reservationDetails").classList.remove("hidden");

    if (selectedOption.getAttribute("data-rated") === "true") {
        document.getElementById("ratingForm").classList.add("hidden");
        document.getElementById("ratingMessage").classList.remove("hidden");
    } else {
        document.getElementById("ratingForm").classList.remove("hidden");
        document.getElementById("ratingMessage").classList.add("hidden");
    }
}

// Function to open the rating modal
function openModal() {
    document.getElementById("ratingModal").classList.remove("hidden"); // Show the rating modal
}

// Function to close the rating modal
function closeModal() {
    document.getElementById("ratingModal").classList.add("hidden"); // Hide the rating modal
}