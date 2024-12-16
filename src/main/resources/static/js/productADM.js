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

document.addEventListener('DOMContentLoaded', function () {
    const productTable = document.querySelector('tbody');
    const productRows = productTable.querySelectorAll('tr');
    const categorySelect = document.getElementById('loai-hang');
    const priceSelect = document.getElementById('loc-gia');

    // Lọc theo loại hàng
    categorySelect.addEventListener('change', function () {
        const selectedCategory = categorySelect.value.toLowerCase();
        productRows.forEach(row => {
            const categoryCell = row.cells[1].textContent.toLowerCase();
            if (selectedCategory === 'chọn loại hàng' || categoryCell.includes(selectedCategory)) {
                row.style.display = '';
            } else {
                row.style.display = 'none';
            }
        });
    });

    // Lọc theo giá
    priceSelect.addEventListener('change', function () {
        const rowsArray = Array.from(productRows);
        const sortedRows = rowsArray.sort((a, b) => {
            const priceA = parseFloat(a.cells[3].textContent.replace(/[^0-9]/g, ''));
            const priceB = parseFloat(b.cells[3].textContent.replace(/[^0-9]/g, ''));
            return priceSelect.value === 'Giảm dần' ? priceB - priceA : priceA - priceB;
        });
        
        sortedRows.forEach(row => productTable.appendChild(row));
    });
});


function inBaoCao() {
    const printContents = document.querySelector('.invoice-details').innerHTML;
    const originalContents = document.body.innerHTML;

    document.body.innerHTML = printContents;
    window.print();
    document.body.innerHTML = originalContents;
}
