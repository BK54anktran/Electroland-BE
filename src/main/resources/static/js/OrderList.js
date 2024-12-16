document.getElementById("dropdownIcon").addEventListener("click", function() {
    var dropdownFilters = document.getElementById("dropdownFilters");
    var icon = document.querySelector("#dropdownIcon i");
    
    // Toggle visibility of dropdown filter
    dropdownFilters.classList.toggle("show");
    icon.classList.toggle("fa-caret-up");
    icon.classList.toggle("fa-caret-down");
});

// Close the dropdown if clicking outside of it
document.addEventListener("click", function(event) {
    var dropdownFilters = document.getElementById("dropdownFilters");
    var icon = document.querySelector("#dropdownIcon i");
    var isClickInside = document.getElementById("filterContainer").contains(event.target);

    if (!isClickInside) {
        dropdownFilters.classList.remove("show");
        icon.classList.add("fa-caret-down");
        icon.classList.remove("fa-caret-up");
    }
});
