// Toggle the visibility of the dropdown menu when the user menu is clicked
document.querySelector("#userMenu").addEventListener("click", () => {
    document.getElementById("dropdownMenu").classList.toggle("hidden");
});

// Remove opacity class once the DOM is fully loaded
document.addEventListener("DOMContentLoaded", function () {
    document.body.classList.remove("opacity-0");
});

// Handle changes to the departure and return date inputs
document.addEventListener("DOMContentLoaded", function () {
    let departureInput = document.getElementById("dateOfDeparture");
    let returnInput = document.getElementById("dateOfReturn");

    // Set return date minimum value to departure date
    departureInput.addEventListener("change", function () {
        if (departureInput.value) {
            returnInput.min = departureInput.value; // Set return date min to departure date
        }

        // Check if departure date is after return date
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

// Add a new input field for description when the "Add Description" button is clicked
document.getElementById("addDescription").addEventListener("click", function() {
    // Create a new input field for description
    var newInput = document.createElement("input");
    newInput.type = "text";
    newInput.name = "description[]";
    newInput.classList.add("w-full", "px-4", "py-3", "rounded-lg", "border", "border-gray-300", "focus:ring-2", "focus:ring-yellow-500", "text-gray-700", "mb-2");
    newInput.placeholder = "Добавете описание...";

    // Append the new input field to the description container
    document.getElementById("descriptionContainer").appendChild(newInput);
});

// Handle form submission and process descriptions before submitting the form
document.getElementById("destinationForm").addEventListener("submit", function(e) {
    // Prevent default form submission to handle description
    e.preventDefault();

    // Get all the description inputs
    var descriptionInputs = document.getElementsByName("description[]");
    var descriptions = [];

    // Collect the values of the description inputs
    for (var i = 0; i < descriptionInputs.length; i++) {
        if (descriptionInputs[i].value.trim() !== "") {
            descriptions.push(descriptionInputs[i].value.trim());
        }
    }

    // Join the descriptions with a semicolon and space
    var finalDescription = descriptions.join("; ");

    // Add the final description as a hidden field to the form before submitting
    var hiddenDescriptionInput = document.createElement("input");
    hiddenDescriptionInput.type = "hidden";
    hiddenDescriptionInput.name = "description";
    hiddenDescriptionInput.value = finalDescription;
    document.getElementById("destinationForm").appendChild(hiddenDescriptionInput);

    // Submit the form
    this.submit();
});
