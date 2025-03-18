document.querySelector("#userMenu").addEventListener("click", () => {
    document.getElementById("dropdownMenu").classList.toggle("hidden");
});

document.addEventListener("DOMContentLoaded", function () {
    document.body.classList.remove("opacity-0");
});

document.addEventListener("DOMContentLoaded", function () {
    let departureInput = document.getElementById("dateOfDeparture");
    let returnInput = document.getElementById("dateOfReturn");

    departureInput.addEventListener("change", function () {
        if (departureInput.value) {
            returnInput.min = departureInput.value; // Set return date min to departure date
        }

        // Check if departure date is after return date
        if (returnInput.value && departureInput.value > returnInput.value) {
            alert("Датата на тръгване не може да бъде след датата на връщане!");
            departureInput.value = ""; // Reset invalid departure date
        }
    });

    returnInput.addEventListener("change", function () {
        if (departureInput.value && returnInput.value < departureInput.value) {
            alert("Датата на връщане трябва да бъде след датата на тръгване!");
            returnInput.value = ""; // Reset invalid return date
        }
    });
});