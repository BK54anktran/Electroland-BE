document.addEventListener("DOMContentLoaded", function() {
    function createDayOptions(maxDays) {
        const dayDropdownMenu = $('#dayDropdownMenu');
        dayDropdownMenu.empty(); 
        for (let i = 1; i <= maxDays; i++) {
            let day = (i < 10) ? '0' + i : i;
            dayDropdownMenu.append(`<a class="dropdown-item" href="#" onclick="selectValue('dayDropdown', ${i})">${day}</a>`);
        }
    }
    const monthDropdownMenu = $('#monthDropdownMenu');
    for (let i = 1; i <= 12; i++) {
        let month = (i < 10) ? '0' + i : i; 
        monthDropdownMenu.append(`<a class="dropdown-item" href="#" onclick="selectValue('monthDropdown', ${i}); updateDays()">${month}</a>`);
    }

   
    const currentYear = new Date().getFullYear();
    const yearDropdownMenu = $('#yearDropdownMenu');
    for (let i = currentYear; i >= 1900; i--) {
        yearDropdownMenu.append(`<a class="dropdown-item" href="#" onclick="selectValue('yearDropdown', ${i}); updateDays()">${i}</a>`);
    }

    createDayOptions(31);

  
    function updateDays() {
        let selectedMonth = $('#monthDropdown').text();
        let selectedYear = $('#yearDropdown').text();

        if (selectedMonth === 'Tháng' || selectedYear === 'Năm') {
            return;
        }

        selectedMonth = parseInt(selectedMonth);
        selectedYear = parseInt(selectedYear);

        let maxDays = 31;

        if (selectedMonth === 2) {
            // Tháng 2: Kiểm tra năm nhuận
            if ((selectedYear % 4 === 0 && selectedYear % 100 !== 0) || (selectedYear % 400 === 0)) {
                maxDays = 29; // Tháng 2 có 29 ngày nếu là năm nhuận
            } else {
                maxDays = 28; // Tháng 2 có 28 ngày nếu không phải là năm nhuận
            }
        } else if ([4, 6, 9, 11].includes(selectedMonth)) {
            maxDays = 30; // Các tháng 4, 6, 9, 11 có 30 ngày
        } else {
            maxDays = 31; // Các tháng còn lại có 31 ngày
        }
        createDayOptions(maxDays);
    }

    window.selectValue = function (dropdownId, value) {
        let displayValue = (value < 10) ? '0' + value : value; 
        $('#' + dropdownId).text(displayValue);

        if (dropdownId === 'monthDropdown' || dropdownId === 'yearDropdown') {
            updateDays();
        }
    };
});
