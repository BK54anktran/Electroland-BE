document.addEventListener('DOMContentLoaded', function () {
    const colorOptions = document.querySelectorAll('.color-option');
    
    colorOptions.forEach(option => {
        option.addEventListener('click', function () {
            // Xóa lớp 'selected' khỏi tất cả các nút
            colorOptions.forEach(opt => opt.classList.remove('selected'));
            // Thêm lớp 'selected' vào nút được chọn
            this.classList.add('selected');
        });
    });
});
