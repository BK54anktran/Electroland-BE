function updateTotal(row) {
  const quantity = parseInt(row.querySelector('.quantity').value);
  const price = parseFloat(row.querySelector('.price').textContent.replace('đ', '').replace(',', '').trim());
  const total = quantity * price;
  row.querySelector('.total').textContent = total;
}
function reloadPage() {
  location.reload();  // Tải lại trang hiện tại
}
function updateOrder() {
  // Lấy tất cả các input có class 'quantity'
  let quantities = document.querySelectorAll('.quantity');

  // Tạo một đối tượng để chứa thông tin order
  let orderData = {
    id: document.getElementById('id').value,
    hoTen: document.getElementById('hotenKhachHang').value,
    sdt: document.getElementById('phone').value,
    diaChi: document.getElementById('address').value,
    phuongThucTT: document.getElementById('phuongThucTT').value,
    ngayDH: document.getElementById('ngayDH').value,
    ngayGH: document.getElementById('ngayGH').value,
    trangThai: document.getElementById('status').value,
    chiTietDhs: []
  };

  console.log(orderData);

  // Lặp qua các input quantity để thu thập dữ liệu sản phẩm
  quantities.forEach(function(input) {
    let productId = input.id.split('-')[1];  // Lấy ID sản phẩm từ id của input (ví dụ 'quantity-1')
    let quantity = input.value;

    orderData.chiTietDhs.push({
      id: productId,
      soLuong: quantity
    });
  });

  fetch(`/admin/order/detail/${orderData.id}/update`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(orderData),
  })
    .then((response) => {
      console.log('Response:', response);
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      location.reload(true);
      return response.json();
    })
    .then((data) => {
      console.log('Data received:', data);
      alert('Cập nhật đơn hàng thành công!');
    })
    .catch((error) => {
      console.error('Caught error:', error);
      console.error('Error Message:', error.message);
      console.error('Error Stack:', error.stack);
      reloadPage();
      // alert('Có lỗi xảy ra khi cập nhật đơn hàng!');
    }); 
}
