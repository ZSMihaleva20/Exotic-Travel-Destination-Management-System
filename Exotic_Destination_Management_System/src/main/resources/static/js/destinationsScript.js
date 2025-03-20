// Wait for the DOM to be fully loaded
document.addEventListener("DOMContentLoaded", function () {
    let departureInput = document.getElementById("startDate");
    let returnInput = document.getElementById("endDate");

    // Add an event listener to the departure date input to adjust the return date's minimum value
    departureInput.addEventListener("change", function () {
        if (departureInput.value) {
            returnInput.min = departureInput.value; // Set return date min to departure date
        }
        if (returnInput.value && departureInput.value > returnInput.value) {
            alert("Датата на заминаване не може да бъде след датата на връщане!");
            departureInput.value = ""; // Reset invalid departure date
        }
    });

    returnInput.addEventListener("change", function () {
        if (departureInput.value && returnInput.value < departureInput.value) {
            alert("Датата на връщане трябва да бъде след датата на заминаване!");
            returnInput.value = ""; // Reset invalid return date
        }
    });
});

// Toggle the visibility of the dropdown menu when the user menu is clicked
document.querySelector("#userMenu").addEventListener("click", () => {
    document.getElementById("dropdownMenu").classList.toggle("hidden");
});

// Remove the "opacity-0" class once the DOM content is loaded to make the page visible
document.addEventListener("DOMContentLoaded", function () {
    document.body.classList.remove("opacity-0");
});