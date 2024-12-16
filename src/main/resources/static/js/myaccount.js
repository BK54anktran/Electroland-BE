
function showContent(contentType) {
    const contentDisplay = document.getElementById("content-display");
   
    // Loại bỏ class "active" khỏi tất cả các nút trong danh sách
    const buttons = document.querySelectorAll(".list-group-item");
    buttons.forEach(button => button.classList.remove("active"));

    // Thêm class "active" cho nút hiện tại
    const activeButton = document.querySelector(`.list-group-item[onclick="showContent('${contentType}')"]`);
    if (activeButton) {
        activeButton.classList.add("active");
    }
    switch(contentType) {
        case 'accountInfo':
            contentDisplay.innerHTML = `<div class="card p-4">
                    <form>
                        <div class="form-group">
                            <label for="name">Họ tên</label>
                            <input type="text" class="form-control" id="name" th:text="*{hoTen}>
                        </div>
                        <div class="form-group">
                            <label for="phone">Số điện thoại</label>
                            <input type="text" class="form-control" id="phone" value="0899494520">
                        </div>
                        <div class="form-group">
                            <label for="email">Email</label>
                            <input type="email" class="form-control" id="email" value="minhngoc9499@gmail.com">
                        </div>
                        <div class="form-group">
                            <label>Giới tính</label><br>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="gender" id="male" value="male">
                                <label class="form-check-label" for="male">Nam</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="gender" id="female" value="female" checked>
                                <label class="form-check-label" for="female">Nữ</label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label  class="mb-3">Ngày sinh</label>
                            <div class="form-row">
                                    <div class="d-flex">
                                        <div class="dropdown">
                                            <button class="btn btn-secondary dropdown-toggle" type="button" id="dayDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                Ngày
                                            </button>
                                            <div class="dropdown-menu" aria-labelledby="dayDropdown" id="dayDropdownMenu">
                                              
                                            </div>
                                        </div>
                              
                                        <div class="dropdown">
                                            <button class="btn btn-secondary dropdown-toggle" type="button" id="monthDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                Tháng
                                            </button>
                                            <div class="dropdown-menu" aria-labelledby="monthDropdown" id="monthDropdownMenu">
                                                
                                            </div>
                                        </div>
                                        
                                        <!-- Dropdown Năm -->
                                        <div class="dropdown">
                                            <button class="btn btn-secondary dropdown-toggle" type="button" id="yearDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                Năm
                                            </button>
                                            <div class="dropdown-menu" aria-labelledby="yearDropdown" id="yearDropdownMenu">
                                               
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                </div>
                                <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                        </div>
                    </div>
                    </div>`;
            break;
        case 'shoppingHistory':
            contentDisplay.innerHTML = `
                 <div class="card p-4">
                    <div class="tab-container">
                        <div class="tabs">
                            <button class="tab active" onclick="showTab('completed')">Hoàn thành</button>
                            <button class="tab" onclick="showTab('processing')">Đang xử lý</button>
                            <button class="tab" onclick="showTab('cancelled')">Đã hủy</button>
                        </div>
                    </div>
                    <div class="search-box1 mt-5">
                        <input type="text" placeholder="Tìm đơn hàng theo mã đơn hàng..." />
                        <button class="search-btn">Tìm kiếm</button>
                    </div>

                    <div class="tab-content" id="completed">
                        <table class="order-table">
                            <thead>
                                <tr>
                                    <th>Mã đơn hàng</th>
                                    <th>Ngày</th>
                                    <th>Trạng thái</th>
                                    <th>Giá</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>#3456_768</td>
                                    <td>October 17, 2023</td>
                                    <td>Hoàn thành</td>
                                    <td>5.350.000 đ</td>
                                    <td><a href="#" class="details-link" onclick="showContent('shoppingHistoryDetail')">Chi tiết</a></td>
                                </tr>
                                <tr>
                                    <td>#3456_768</td>
                                    <td>October 17, 2023</td>
                                    <td>Hoàn thành</td>
                                    <td>5.350.000 đ</td>
                                    <td><a href="#" class="details-link">Chi tiết</a></td>
                                </tr>
                                <tr>
                                    <td>#3456_768</td>
                                    <td>October 17, 2023</td>
                                    <td>Hoàn thành</td>
                                    <td>5.350.000 đ</td>
                                    <td><a href="#" class="details-link">Chi tiết</a></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="tab-content" id="processing" style="display: none;">
                        <table class="order-table">
                            <thead>
                                <tr>
                                    <th>Mã đơn hàng</th>
                                    <th>Ngày</th>
                                    <th>Trạng thái</th>
                                    <th>Giá</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>#1234_567</td>
                                    <td>October 18, 2023</td>
                                    <td>Đang xử lý</td>
                                    <td>2.150.000 đ</td>
                                    <td><a href="#" class="details-link">Chi tiết</a></td>
                                </tr>
                                <tr>
                                    <td>#1234_567</td>
                                    <td>October 18, 2023</td>
                                    <td>Đang xử lý</td>
                                    <td>2.150.000 đ</td>
                                    <td><a href="#" class="details-link">Chi tiết</a></td>
                                </tr>
                                <tr>
                                    <td>#1234_567</td>
                                    <td>October 18, 2023</td>
                                    <td>Đang xử lý</td>
                                    <td>2.150.000 đ</td>
                                    <td><a href="#" class="details-link">Chi tiết</a></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="tab-content" id="cancelled" style="display: none;">
                        <table class="order-table">
                            <thead>
                                <tr>
                                    <th>Mã đơn hàng</th>
                                    <th>Ngày</th>
                                    <th>Trạng thái</th>
                                    <th>Giá</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>#7890_123</td>
                                    <td>October 19, 2023</td>
                                    <td>Đã hủy</td>
                                    <td>1.000.000 đ</td>
                                    <td><a href="#" class="details-link">Chi tiết</a></td>
                                </tr>
                                <tr>
                                    <td>#7890_123</td>
                                    <td>October 19, 2023</td>
                                    <td>Đã hủy</td>
                                    <td>1.000.000 đ</td>
                                    <td><a href="#" class="details-link">Chi tiết</a></td>
                                </tr>
                                <tr>
                                    <td>#7890_123</td>
                                    <td>October 19, 2023</td>
                                    <td>Đã hủy</td>
                                    <td>1.000.000 đ</td>
                                    <td><a href="#" class="details-link">Chi tiết</a></td>
                                </tr>
                                <tr>
                                    <td>#7890_123</td>
                                    <td>October 19, 2023</td>
                                    <td>Đã hủy</td>
                                    <td>1.000.000 đ</td>
                                    <td><a href="#" class="details-link">Chi tiết</a></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>    `;
            break;
        case 'address':
            contentDisplay.innerHTML = ` <div class="container">
                    <div class="address-header">
                        <h4>Danh sách địa chỉ</h4>
                        <button class="add-address-btn" data-bs-toggle="modal" data-bs-target="#updateModal" onclick="clearModal()">
                            <i class="bi bi-plus-circle"></i> Thêm địa chỉ mới
                        </button>
                    </div>
                    <div class="address-list" id="addressList">
                        <div class="address-card">
                            <div class="form-check">
                                <input class="form-check-input delete-checkbox" type="checkbox">
                            </div>
                            <h6 class="address-type">Văn Phòng</h6>
                            <p class="address-name">Nguyễn Thị Minh Ngọc</p>
                            <p class="address-phone">089 234 567 890</p>
                            <p class="address-detail">696/4 Âu Cơ, Phường 14, Quận Tân Bình, Thành Phố Hồ Chí Minh</p>
                            <a href="#" class="edit-btn" data-bs-toggle="modal" data-bs-target="#updateModal" onclick="fillModal(this)">
                                <i class="bi bi-pencil"></i> Sửa
                            </a>
                        </div>
                        <div class="address-card active">
                            <div class="form-check">
                                <input class="form-check-input delete-checkbox" type="checkbox">
                            </div>
                            <h6 class="address-type">Nhà Riêng</h6>
                            <p class="address-name">Nguyễn Thị Minh Ngọc</p>
                            <p class="address-phone">089 234 567 890</p>
                            <p class="address-detail">696/4 Âu Cơ, Phường 14, Quận Tân Bình, Thành Phố Hồ Chí Minh</p>
                            <a href="#" class="edit-btn" data-bs-toggle="modal" data-bs-target="#updateModal" onclick="fillModal(this)">
                                <i class="bi bi-pencil"></i> Sửa
                            </a>
                        </div>
                        <div class="address-card ">
                            <div class="form-check">
                                <input class="form-check-input delete-checkbox" type="checkbox">
                            </div>
                            <h6 class="address-type">Nhà Riêng</h6>
                            <p class="address-name">Nguyễn Thị Minh Ngọc</p>
                            <p class="address-phone">089 234 567 890</p>
                            <p class="address-detail">696/4 Âu Cơ, Phường 14, Quận Tân Bình, Thành Phố Hồ Chí Minh</p>
                            <a href="#" class="edit-btn" data-bs-toggle="modal" data-bs-target="#updateModal" onclick="fillModal(this)">
                                <i class="bi bi-pencil"></i> Sửa
                            </a>
                        </div>
                        <div class="address-card ">
                            <div class="form-check">
                                <input class="form-check-input delete-checkbox" type="checkbox">
                            </div>
                            <h6 class="address-type">Nhà Riêng</h6>
                            <p class="address-name">Nguyễn Thị Minh Ngọc</p>
                            <p class="address-phone">089 234 567 890</p>
                            <p class="address-detail">696/4 Âu Cơ, Phường 14, Quận Tân Bình, Thành Phố Hồ Chí Minh</p>
                            <a href="#" class="edit-btn" data-bs-toggle="modal" data-bs-target="#updateModal" onclick="fillModal(this)">
                                <i class="bi bi-pencil"></i> Sửa
                            </a>
                        </div>
                    </div>
                    <div class="btn-delete-container d-flex">
                        <button class="delete-btn" onclick="deleteSelectedCards()">
                            <i class="bi bi-trash"></i> Xóa
                        </button>
                    </div>
                    
                </div>
                
                <!-- Modal cập nhật địa chỉ -->
                <div class="modal fade" id="updateModal" tabindex="-1" aria-labelledby="updateModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="updateModalLabel">Cập nhật địa chỉ</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <hr class="modal-divider">
                            <div class="modal-body">
                                <form>
                                    <div class="mb-3">
                                        <label for="name" class="form-label">Họ tên</label>
                                        <input type="text" class="form-control" id="name">
                                    </div>
                
                                    <div class="mb-3">
                                        <label for="phone" class="form-label">Số điện thoại</label>
                                        <input type="text" class="form-control" id="phone">
                                    </div>
                
                                    <div class="mb-3">
                                        <label for="address" class="form-label">Địa chỉ</label>
                                        <div class="address-fields">
                                            <select class="form-select" id="city">
                                                <option value="">Chọn tỉnh thành</option>
                                            </select>
                                            <select class="form-select" id="district">
                                                <option value="">Chọn quận huyện</option>
                                            </select>
                                        </div>
                                        <div class="address-fields">
                                            <select class="form-select" id="ward">
                                                <option value="">Chọn phường xã</option>
                                            </select>
                                            <input type="text" class="form-control" id="addressDetail" placeholder="Nhập địa chỉ chi tiết">
                                        </div>
                                    </div>
                                    
                
                                    <div class="mb-3">
                                        <label class="form-label">Loại địa chỉ</label>
                                        <div class="type-btn-group">
                                            <button type="button" class="type-btn mr-4" data-type="văn phòng" onclick="toggleType(this)">Văn phòng</button>
                                            <button type="button" class="type-btn" data-type="nhà riêng" onclick="toggleType(this)">Nhà riêng</button>
                                            
                                        </div>
                                    </div>
                
                                    <div class="custom-toggle">
                                        <label class="form-check-label" for="defaultAddress">
                                            Đặt làm địa chỉ mặc định
                                        </label>
                                        <label class="switch">
                                            <input type="checkbox" id="defaultAddress">
                                            <span class="slider"></span>
                                        </label>
                                    </div>
                                    
                                    
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-primary">Cập nhật</button>
                            </div>
                        </div>
                    </div>
                </div>`;
            break;
        case 'shoppingHistoryDetail':
            contentDisplay.innerHTML = `
            <div class="container">
                <div class="card p-5">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Sản phẩm</th>
                                <th>Giá</th>
                                <th>Số lượng</th>
                                <th>Tổng</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>
                                    <div class="d-flex align-items-center">
                                        <img src="/IMG/iPhone-16-Pro-Series-Desert1.jpg" alt="iPhone 16 128GB" class="img-thumbnail m-3" style="width: 60px;">
                                        <div>
                                            <div>iPhone 16 128GB</div>
                                            <div>Chính hãng</div>
                                            <div>VNA</div>
                                        </div>
                                    </div>
                                </td>
                                <td>22.490.000 đ</td>
                                <td>1</td>
                                <td>22.490.000 đ</td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="d-flex align-items-center">
                                        <img src="/IMG/iphone-15-pro-max-blue-thumbnew-600x600.jpg" alt="iPhone 15 128GB" class="img-thumbnail m-3" style="width: 60px;">
                                        <div>
                                            <div>iPhone 15 128GB</div>
                                            <div>Chính hãng</div>
                                            <div>VNA</div>
                                        </div>
                                    </div>
                                </td>
                                <td>18.490.000 đ</td>
                                <td>1</td>
                                <td>18.490.000 đ</td>
                            </tr>
                        </tbody>
                    </table>
                    
                    <div class="card p-4">
                        <h5 class="card-title"><strong>Thông tin đơn hàng</strong></h5>
                        <hr>
                        <div class="row mt-3">
                            <div class="col-md-4">
                                <p><strong>Chi tiết</strong></p>
                                <div class="d-flex justify-content-between">
                                    <p><strong>Tổng:</strong></p>
                                    <p>40.980.000 đ</p>
                                </div>
                                <div class="d-flex justify-content-between">
                                    <p><strong>Giảm:</strong></p>
                                    <p>-409.800 đ</p>
                                </div>
                                <div class="d-flex justify-content-between">
                                    <p><strong>Phí vận chuyển:</strong></p>
                                    <p>0.00 đ</p>
                                </div>
                                <div class="d-flex justify-content-between">
                                    <p><strong>Thanh toán:</strong></p>
                                    <p>40.570.200 đ</p>
                                </div>
                            </div>
                            
                            <div class="col-md-4">
                                <p><strong>Phương thức thanh toán</strong></p>
                                <p>Thẻ tín dụng</p>
                            </div>
                            
                            <div class="col-md-4">
                                <p><strong>Địa chỉ</strong></p>
                                <p>Minh Ngọc</p>
                                <p>0899494520</p>
                                <p>696/4 Âu Cơ, Phường 14, Tân Bình, TP Hồ Chí Minh</p>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Nút Đặt Lại và Đánh Giá -->
                    <div class="d-flex justify-content-end mt-4">
                        <button type="button" class="btn btn-outline-primary">Đặt lại</button>
                        <button type="button" class="btn btn-outline-primary ml-4">Đánh giá</button>
                    </div>
                </div>
            </div>`;
        break;
               
        default:
            contentDisplay.innerHTML = "<h3>Chọn một mục để xem nội dung</h3>";
            break;
    }
}
const completedElement = document.getElementById('completed');
if (completedElement) {
    completedElement.style.display = 'block';
}

function showTab(tabId) {
    const tabContents = document.querySelectorAll('.tab-content');
    tabContents.forEach(tabContent => {
        tabContent.style.display = 'none';
    });
    document.getElementById(tabId).style.display = 'block';

    const tabs = document.querySelectorAll('.tab');
    tabs.forEach(tab => {
        tab.classList.remove('active');
    });
    event.currentTarget.classList.add('active');
}