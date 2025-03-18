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

document.querySelector("#userMenu").addEventListener("click", () => {
    document.getElementById("dropdownMenu").classList.toggle("hidden");
});

document.addEventListener("DOMContentLoaded", function () {
    document.body.classList.remove("opacity-0");
});