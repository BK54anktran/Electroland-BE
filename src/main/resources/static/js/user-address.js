var diaChiList = [[${ diaChiList }]];

// Đảm bảo rằng hàm fillModal được định nghĩa và có thể sử dụng
function fillModal(id) {

    console.log(diaChiList);

    console.log("ID của địa chỉ cần sửa:", id); // Debugging
    // Lấy thông tin của địa chỉ qua ID
    let diaChi = getDiaChiById(id);

    // Kiểm tra xem địa chỉ có tồn tại không
    if (diaChi) {
        console.log("Thông tin địa chỉ:", diaChi); // Debugging
        console.log("Thông tin địa chỉ:", diaChi.khachHang); // Debugging

        // Điền dữ liệu vào các trường trong modal
        document.getElementById("idAddress").value = diaChi.id;
        document.getElementById("name").value = diaChi.hoTenNN;
        document.getElementById("phone").value = diaChi.sdtNN;
        document.getElementById("addressDetail").value = diaChi.chiTiet;

        // Cập nhật Loại địa chỉ (Văn phòng/Nhà riêng)
        let typeButtons = document.querySelectorAll(".type-btn");
        typeButtons.forEach(function (button) {

            console.log("Loại địa chỉ:", diaChi.loaiDiaChi); // Debugging
            console.log("Loại địa chỉ của button:", button.getAttribute("data-type")); // Debugging
            if (button.getAttribute("data-type") === diaChi.loaiDiaChi) {
                button.classList.add("active");
            } else {
                button.classList.remove("active");
            }
        });

        // Đặt địa chỉ mặc định nếu có
        document.getElementById("defaultAddress").checked = diaChi.macDinh;
    } else {
        console.log("Không tìm thấy địa chỉ với id", id);
    }

}

function getDiaChiById(id) {
    // Tìm địa chỉ trong danh sách (danh sách này được truyền từ server)
    return diaChiList.find(diaChi => diaChi.id === id);
}

function updateAddress() {
    // Lấy giá trị từ các trường trong modal
    const id = $("#idAddress").val();
    const name = $("#name").val();
    const phone = $("#phone").val();
    const addressDetail = $("#addressDetail").val();
    const loaiDiaChi = $(".type-btn.active").attr("data-type");
    const isDefault = $("#defaultAddress").is(":checked");
    console.log("Name:", name);
    // Tạo object chứa dữ liệu cần gửi lên server
    const dataToUpdate = {
        id: id,  // ID của địa chỉ cần cập nhật
        hoTen: name,
        sdt: phone,
        chiTiet: addressDetail,
        loaiDiaChi: loaiDiaChi,
        macDinh: isDefault
    };
  

    // Gửi dữ liệu tới controller thông qua jQuery AJAX
    $.ajax({
        url: "/user_address/update",  // Đảm bảo id địa chỉ được gửi đúng
        type: "POST",  // Phương thức PUT để cập nhật
        contentType: "application/json",  // Định dạng nội dung là JSON
        data: JSON.stringify(dataToUpdate),  // Chuyển object thành JSON
        success: function (response) {
            console.log("Cập nhật thành công:", response);
            // Ẩn modal và thông báo thành công
            $('#updateModal').modal('hide');
            alert("Cập nhật địa chỉ thành công");
            location.reload();


        },
        error: function (error) {
            console.log("Lỗi khi cập nhật:", error);
            alert("Có lỗi xảy ra khi cập nhật địa chỉ.");
        }
    });
}
    
// Hàm để chuyển trạng thái "active" cho loại địa chỉ
function toggleType(button) {
// Loại bỏ class 'active' khỏi tất cả các nút
document.querySelectorAll('.type-btn').forEach(btn => btn.classList.remove('active'));

// Thêm class 'active' vào nút hiện tại
button.classList.add('active');
}
$(document).ready(function() {
// Khi người dùng tick vào checkbox
$('.delete-checkbox').change(function() {
// Cập nhật danh sách ID các mục cần xóa
var selectedIds = [];

// Lấy tất cả các checkbox đã chọn
$('.delete-checkbox:checked').each(function() {
    var id = $(this).data('id');  // Lấy giá trị data-id của checkbox đã chọn
    selectedIds.push(id);  // Thêm ID vào danh sách
});

// Khi nhấn nút "Xóa", gửi yêu cầu xóa
$('#submitDelete').click(function() {
    // Kiểm tra xem có mục nào được chọn không
    if (selectedIds.length > 0) {
        // Gửi yêu cầu xóa các ID đã chọn
        $.ajax({
            url: '/user_address/delete',
            type: 'DELETE',
            contentType: 'application/json',
            data: JSON.stringify({ ids: selectedIds }),  // Gửi danh sách ID cần xóa
            success: function(response) {
                alert("Đã xóa thành công.");
                location.reload();  // Tải lại trang để cập nhật dữ liệu
            },
            error: function(xhr, status, error) {
                alert("Lỗi xảy ra khi xóa.");
            }
        });
    } else {
        alert("Vui lòng chọn ít nhất một địa chỉ để xóa.");
    }
});
});
});

