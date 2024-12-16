document.querySelectorAll('.nav-item').forEach(item => {
    item.addEventListener('click', function() {
        // Loại bỏ lớp 'active' khỏi tất cả các mục
        document.querySelectorAll('.nav-item a').forEach(link => {
            link.classList.remove('active');
        });
        
        // Thêm lớp 'active' vào mục được click
        this.querySelector('a').classList.add('active');
    });
});
