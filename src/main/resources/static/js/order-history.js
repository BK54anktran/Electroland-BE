document.getElementById('completed').style.display = 'block';
// Hiển thị tab "Hoàn thành" mặc định
function showTab(tabId) {
    // Ẩn tất cả các tab nội dung
    const tabContents = document.querySelectorAll('.tab-content');
    tabContents.forEach(tabContent => {
        tabContent.style.display = 'none';
    });
    // Hiển thị tab được chọn
    document.getElementById(tabId).style.display = 'block';

    // Loại bỏ class active từ tất cả các tab
    const tabs = document.querySelectorAll('.tab');
    tabs.forEach(tab => {
        tab.classList.remove('active');
    });

    // Thêm class active cho tab được chọn
    event.currentTarget.classList.add('active');
}

