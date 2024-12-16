// Lấy tất cả các icon xóa
document.querySelectorAll('.fa-trash-can').forEach((icon) => {
    icon.addEventListener('click', (event) => {
        // Xác nhận người dùng trước khi xóa
        if (confirm('Bạn có chắc chắn muốn xóa sản phẩm này?')) {
            // Tìm đến hàng chứa icon được click
            const row = event.target.closest('tr');
            // Xóa hàng khỏi bảng
            row.remove();
            alert('Sản phẩm đã được xóa.');
        }
    });
});
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