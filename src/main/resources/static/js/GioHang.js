

// Cập nhật tổng số tiền dựa trên các sản phẩm đã chọn

// Các hàm điều khiển số lượng sản phẩm
function increaseQuantity(button) {
    id = button.getAttribute("data-id")
    const quantityDisplay = button.previousElementSibling;
    let currentQuantity = parseInt(quantityDisplay.textContent);
    quantityDisplay.textContent = currentQuantity + 1;
    updateTotalPrice(button, currentQuantity + 1);
    updateQuantity(id, 1)
    update()

}

function decreaseQuantity(button) {
    id = button.getAttribute("data-id")
    const quantityDisplay = button.nextElementSibling;
    let currentQuantity = parseInt(quantityDisplay.textContent);
    if (currentQuantity > 1) {
        quantityDisplay.textContent = currentQuantity - 1;
        updateTotalPrice(button, currentQuantity - 1);
        updateQuantity(id, 0)
        update()
    }

}

function updateQuantity(id, state) {
    let a = fetch(`http://localhost:8080/rest/giohang/Quantity?id=${id}&state=${state}`)
}




// Cập nhật tổng giá của từng sản phẩm dựa trên số lượng
function updateTotalPrice(button, newQuantity) {
    const cartItem = button.closest('.cart-containerItem');
    const priceText = cartItem.querySelector('.price').textContent;
    const price = parseFloat(priceText.replace(/[^0-9]/g, ''));

    const totalPriceElement = cartItem.querySelector('.total-price');
    const newTotalPrice = price * newQuantity;
    totalPriceElement.textContent = newTotalPrice.toLocaleString() + 'đ';
}




// Chọn hoặc bỏ chọn tất cả các sản phẩm
function toggleSelectAll() {
    const selectAll = document.getElementById("select-all").checked;
    document.querySelectorAll(".select-item").forEach(item => {
        item.checked = selectAll;
        // Lấy thông tin sản phẩm và khách hàng từ các thuộc tính
    });
    update()
}


// Kiểm tra và cập nhật trạng thái checkbox "Chọn tất cả" khi sản phẩm được chọn/bỏ chọn
function checkSelected() {
    document.querySelectorAll(".select-item").forEach(item => {
        const sanPham = item.getAttribute("data-idsp");
        const khachhang = item.getAttribute("data-idkh");
        const isChecked = item.checked;

        if (sanPham && khachhang) {
            updateProductSelection(sanPham, khachhang, isChecked);
        } else {
            console.error('Tham số sanPham hoặc khachhang không hợp lệ:', sanPham, khachhang);
        }
    });
    update()
}

// Xóa tất cả sản phẩm được chọn
function deleteAllSelected() {
    document.querySelectorAll(".select-item:checked").forEach(item => {
        const sanPham = item.getAttribute("data-idsp");
        const khachhang = item.getAttribute("data-idkh");
        removeItemFromDB(sanPham, khachhang); // Gửi yêu cầu xóa sản phẩm khỏi DB
        item.closest('.cart-containerItem').remove(); // Xóa sản phẩm khỏi UI
    });
    update()
}


// Bỏ chọn tất cả sản phẩm
function deselectAll() {
    document.querySelectorAll(".select-item").forEach(item => item.checked = false);
    document.getElementById("select-all").checked = false;
    update()
}



// Xóa sản phẩm cá nhân
function removeItem(link) {
    let id = link.getAttribute("data-id")
    console.log(id)
    let a = fetch("http://localhost:8080/rest/giohang/" + id)
}
// Xóa sản phẩm cá nhân
function addItem(link) {
    let checked = link.getAttribute("data-id")
    let a = fetch("http://localhost:8080/rest/giohang/update" + checked)
    .then(() =>update())
}

async function update() {
    checkDiscount()
}

loadData()
function loadData() {
    return fetch(`http://localhost:8080/rest/giohang/Vouchers`)
        .then(response => response.json())
        .then(data => {
            let DHVC = document.getElementById("DHVC")
            DHVC.innerHTML = "";
            let SPVC = document.getElementById("SPVC")
            SPVC.innerHTML = "";
            data.forEach(item => {
                console.log(item)
                if (item.maGiamSp == null)
                    DHVC.innerHTML += `<div class="col-12 voucher-item mb-3">
                            <div class="d-flex align-items-center">
                                <!-- Hình ảnh -->
                                <img src="img/logoxanh.png" alt="Voucher" class="img-fluid me-3" style="width: 80px; height: 80px;">
                                
                                <!-- Mô tả -->
                                <div class="text-center">
                                    <div class="mb-2">${item.maGiamDh.moTa}</div>
                                </div>
                                <!-- Nút chọn -->
                                <button type="button" class="btn ${item.checked ? 'btn-success' : 'btn-outline-primary'}"
                                data-id="${item.id}"
                                name="MaGiamDh"
                                onclick="addVoucher(this)">
                                ${item.checked ? 'Đã chọn' : 'Chọn'}
                            </button>
                            </div>
                        </div>`
                else SPVC.innerHTML += `<div class="col-12 voucher-item mb-3">
                <div class="d-flex align-items-center">
                    <!-- Hình ảnh -->
                    <img src="img/logoxanh.png" alt="Voucher" class="img-fluid me-3" style="width: 80px; height: 80px;">
                    
                    <!-- Mô tả -->
                    <div class="text-center">
                        <div class="mb-2">${item.maGiamSp.moTa}</div>
                    </div>
                    <!-- Nút chọn -->
                    <button type="button" class="btn ${item.checked ? 'btn-success' : 'btn-outline-primary'}"
                    data-id="${item.id}"
                    name="MaGiamDh"
                    onclick="addVoucher(this)">
                    ${item.checked ? 'Đã chọn' : 'Chọn'}
                </button>
                </div>
            </div>`
            })
            if (DHVC.innerHTML == "") DHVC.innerHTML = `<p style="text-align: center;color: red;">Mã giảm trống</p>`
            if (SPVC.innerHTML == "") DHVC.innerHTML = `<p style="text-align: center;color: red;">Mã giảm trống</p>`
        });
}
update()
function fetchDiscouns() {
    return fetch("http://localhost:8080/rest/giohang/getDiscount")
        .then(response => response.json())
}


function fetchTotal() {
    return fetch("http://localhost:8080/rest/giohang/getTotal")
        .then(response => response.json())
}

async function checkDiscount() {

    fetch(`http://localhost:8080/rest/giohang/checkDiscount?_=${Date.now()}`)
        .then(response => response.text())
        .then(data => {
            if (data != "")
                ThongBao("Thông báo", data, 'error')
            loadData().then(() => {
                fetchDiscouns().then(giam => {
                    document.getElementById("totalGiam").innerHTML = giam.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
                    fetchTotal().then(total => {
                        document.getElementById("totalMoney").innerHTML = total.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
                        document.getElementById("total").innerHTML = (total - giam).toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
                    })
                })
            })
        })

}


// Sử dụng
function addVoucher(link) {
    let id = link.getAttribute("data-id");
    fetch(`http://localhost:8080/rest/giohang/updateVoucher/${id}`)
        .then(sys => update())
}


function ThongBao(title, html, icon) {
    Swal.fire({
        title: title,
        html: html,
        icon: icon,
        confirmButtonText: 'OK'
    });
}








