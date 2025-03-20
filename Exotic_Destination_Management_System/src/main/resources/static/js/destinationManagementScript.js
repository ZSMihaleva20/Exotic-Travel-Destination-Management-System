// Toggle the visibility of the dropdown menu when the user menu is clicked
document.querySelector("#userMenu").addEventListener("click", () => {
    document.getElementById("dropdownMenu").classList.toggle("hidden");
});

// Remove the "opacity-0" class from the body once the DOM content has loaded (to make the page visible)
document.addEventListener("DOMContentLoaded", function () {
    document.body.classList.remove("opacity-0");
});
