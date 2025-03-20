// Wait for the DOM to be fully loaded before executing the code
document.addEventListener("DOMContentLoaded", function() {
    const openModalBtn = document.getElementById("openModalBtn");
    const closeModalBtn = document.getElementById("closeModalBtn");
    const modal = document.getElementById("modal");

    // Показване на модала
    openModalBtn.addEventListener("click", function() {
        modal.classList.remove("hidden");
    });

    // Скриване на модала
    closeModalBtn.addEventListener("click", function() {
        modal.classList.add("hidden");
    });

    // Скриване при клик извън модала
    modal.addEventListener("click", function(event) {
        if (event.target === modal) {
            modal.classList.add("hidden");
        }
    });
});

// Wait for the DOM to be fully loaded before executing the code for the delete modal
document.addEventListener("DOMContentLoaded", function() {
    const openDeleteModalBtn = document.getElementById("openDeleteModalBtn");
    const closeDeleteModalBtn = document.getElementById("closeDeleteModalBtn");
    const deleteModal = document.getElementById("deleteModal");

    openDeleteModalBtn.addEventListener("click", function() {
        deleteModal.classList.remove("hidden");
    });

    closeDeleteModalBtn.addEventListener("click", function() {
        deleteModal.classList.add("hidden");
    });

    window.addEventListener("click", function(event) {
        if (event.target === deleteModal) {
            deleteModal.classList.add("hidden");
        }
    });
});

// Toggle the dropdown menu when the user menu is clicked
document.querySelector("#userMenu").addEventListener("click", () => {
    document.getElementById("dropdownMenu").classList.toggle("hidden");
});

// Wait for the DOM to be fully loaded and remove the 'opacity-0' class to make body visible
document.addEventListener("DOMContentLoaded", function () {
    document.body.classList.remove("opacity-0");
});