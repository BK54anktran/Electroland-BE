const menuItems = document.querySelectorAll('.menu li');
const submenu = document.querySelectorAll('.submenu');

submenu.forEach(item => {
    item.addEventListener('click', () => {
        menuItems.forEach(el => el.classList.remove('active'));
        item.classList.add('active');
    });
});
menuItems.forEach(item => {
    item.addEventListener('click', () => {
        menuItems.forEach(el => el.classList.remove('active'));
        item.classList.add('active');
    });
});