// Function to toggle the visibility of the notifications dropdown
function showNotifications() {
    let dropdown = document.getElementById('notificationDropdown');
    dropdown.style.display = dropdown.style.display === 'none' ? 'block' : 'none';
}

document.querySelector("#userMenu").addEventListener("click", () => {
    document.getElementById("dropdownMenu").classList.toggle("hidden");
});

document.addEventListener("DOMContentLoaded", function () {
    document.body.classList.remove("opacity-0");
});