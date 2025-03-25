package fpoly.electroland.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.dto.request.ReceiptRequest;
import fpoly.electroland.model.Cart;
import fpoly.electroland.model.CartProductAttribute;
import fpoly.electroland.model.Customer;
import fpoly.electroland.model.CustomerCoupon;
import fpoly.electroland.model.Employee;
import fpoly.electroland.model.Payment;
import fpoly.electroland.model.ProductCoupon;
import fpoly.electroland.model.Receipt;
import fpoly.electroland.model.ReceiptCoupon;
import fpoly.electroland.model.ReceiptDetail;
import fpoly.electroland.model.ReceiptStatus;
import fpoly.electroland.repository.ActionRepository;
import fpoly.electroland.repository.CartProductAttributeRepository;
import fpoly.electroland.repository.CartRepository;
import fpoly.electroland.repository.CustomerCouponRepository;
import fpoly.electroland.repository.EmployeeRepository;
import fpoly.electroland.repository.PaymentRepository;
import fpoly.electroland.repository.PaymentStatusRepository;
import fpoly.electroland.repository.PaymentTypeRepository;
import fpoly.electroland.repository.ProductCouponRepository;
import fpoly.electroland.repository.ReceiptCouponRepository;
import fpoly.electroland.repository.ReceiptDetailRepository;
import fpoly.electroland.repository.ReceiptRepository;
import fpoly.electroland.repository.ReceiptStatusRepository;
import fpoly.electroland.repository.ProductRepository;

import fpoly.electroland.util.CreateAction;

@Service
public class ReceiptService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ActionRepository actionRepository;

    @Autowired
    CreateAction createAction;
    @Autowired
    ReceiptRepository receiptRepository;
    @Autowired
    ReceiptStatusRepository receiptStatusRepository;
    @Autowired
    private ReceiptDetailRepository receiptDetailRepository;

    @Autowired
    private ReceiptCouponRepository receiptCouponRepository;

    @Autowired
    private PaymentTypeRepository paymentTypeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductCouponRepository productCouponRepository;
    @Autowired
    private ActionService actionService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentStatusRepository paymentStatusRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerCouponRepository customerCouponRepository;

    @Autowired
    private CartProductAttributeRepository cartProductAttributeRepository;

    public List<Receipt> getAll() {
        return receiptRepository.findAll();
    }

    // Sửa phương thức getReceiptById để nhận tham số kiểu Long
    public Optional<Receipt> getReceiptById(Long receiptId) {
        return receiptRepository.findById(receiptId); // Phương thức này đã trả về Optional<Receipt>
    }

    // Sửa phương thức getReceiptDetailsByReceiptId để nhận tham số kiểu Long
    public List<ReceiptDetail> getReceiptDetailsByReceiptId(Long receiptId) {
        return receiptDetailRepository.findByReceiptId(receiptId);
    }

    public List<Receipt> getReceiptsByUser(Customer customer) {
        List<Receipt> list = receiptRepository.findByCustomer(customer);
        return list;
    }

    public List<Receipt> getReceiptsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null) {
            startDate = receiptRepository.findEarliestDate().orElse(LocalDateTime.of(2000, 1, 1, 0, 0, 0));
        }
        if (endDate == null) {
            endDate = LocalDateTime.now();
        }
        return receiptRepository.findReceiptsByDateRange(startDate, endDate);
    }

    public List<Receipt> searchReceipts(String searchKey) {
        List<Receipt> results = receiptRepository.searchReceipts(searchKey);
        return results;
    }

    public Receipt updateReceiptStatus(Long id, Integer statusId, int userId) {
        // Tìm hóa đơn theo ID
        Receipt existingReceipt = receiptRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy hóa đơn với ID: " + id));

        // Lưu trạng thái cũ trước khi cập nhật
        Integer oldStatusId = existingReceipt.getReceiptStatus().getId(); // Lưu ID cũ

        // Tìm trạng thái mới theo ID
        ReceiptStatus newStatus = receiptStatusRepository.findById(statusId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy trạng thái hóa đơn với ID: " + statusId));

        // Cập nhật trạng thái mới
        existingReceipt.setReceiptStatus(newStatus);

        // Lưu vào DB
        Receipt savedReceipt = receiptRepository.save(existingReceipt);

    return savedReceipt;
}
public boolean updateReadStatus(int id) {
    // Tìm receipt theo ID
    Receipt receipt = receiptRepository.findById(id).orElse(null);
    if (receipt != null) {
        receipt.setIsRead(true); // Đánh dấu là đã đọc
        receiptRepository.save(receipt); // Lưu lại thay đổi
        return true;
    }
    return false;
}

// 🔹 1. Tổng số đơn hàng
    public long countTotalOrders() {
        return receiptRepository.countTotalOrders();
    }

    // 🔹 2. Đếm đơn hàng theo trạng thái
    public Map<String, Long> countOrdersByStatus() {
        List<Object[]> results = receiptRepository.countOrdersByStatus();
        Map<String, Long> stats = new HashMap<>();
        
        for (Object[] row : results) {
            String status = (String) row[0];
            Long count = (Long) row[1];
            stats.put(status, count);
        }
        return stats;
    }

    // 🔹 3. Tổng doanh thu từ đơn hàng
    public double getTotalRevenue() {
        Double result = receiptRepository.totalRevenue();
        return result != null ? result : 0.0;
    }

    // 🔹 4. Doanh thu theo tháng
    public List<Object[]> getRevenueByMonth() {
        return receiptRepository.revenueByMonth();
    }

    // 🔹 5. Số đơn hàng theo phương thức thanh toán
    public List<Object[]> countOrdersByPaymentMethod() {
        return receiptRepository.countOrdersByPaymentMethod();
    }


    // 🔹 7. Tỷ lệ hoàn đơn
    public double getRefundRate() {
        Double result = receiptRepository.refundRate();
        return result != null ? result : 0.0;
    }

    public Receipt createCart(ReceiptRequest receiptRequest) {

        // Tạo giao dịch
        Payment payment = paymentRepository.save(new Payment(0, receiptRequest.getCreateTime(), new Date(),
                receiptRequest.getTotalAmount(), receiptRequest.getContent(),
                paymentTypeRepository.findById(receiptRequest.getPaymentType()).get(),
                paymentStatusRepository.findById(receiptRequest.getPaymentType()).get()));

        // Tạo hóa đơn
        Receipt receipt = receiptRepository.save(receiptRequestToReceipt(receiptRequest, payment));

        // Lấy danh danh sách sản phẩm đang được chọn
        List<Cart> cartList = cartRepository.findByCustomerIdAndStatus(userService.getUser().getId(), true);

        // Lấy danh sách coupon san phẩm đưuọc chọn
        List<Integer> listCouponProduct = receiptRequest.getListCouponProduct();

        // LẶp qua danh sách sản phẩm được chọn
        for (Cart cart : cartList) {

            // Tìm sản coupon ứng với sản phẩm
            int i = 0;
            int removei = -1;
            ProductCoupon productCoupon = null;

            for (Integer integer : listCouponProduct) {
                Optional<CustomerCoupon> customerCoupon = customerCouponRepository.findById(integer);
                if (cart.getProduct() == customerCoupon.get().getProductCoupon().getProduct()) {
                    productCoupon = customerCoupon.get().getProductCoupon();
                    removei = i;
                }
                customerCouponRepository.delete(customerCoupon.get());
                i++;
            }

            // xóa coupon nếu tìm thấy
            if (removei >= 0)
                listCouponProduct.remove(removei);

            // Khởi tạo giá sản phẩm
            Double price = cart.getProduct().getPriceDiscount() != null ? cart.getProduct().getPriceDiscount()
                    : cart.getProduct().getPrice();

            // Tính giá sản phẩm cuối cùng the các thuộc tính được chọn
            for (CartProductAttribute att : cart.getCartProductAttributes()) {
                price += att.getAttribute().getAttributePrice();

                // Xóa thuộc tính các sản phẩm ra khỏi chi tiết giỏ hàng
                cartProductAttributeRepository.delete(att);
            }

            // Tạo hóa đơn chi tiết
            receiptDetailRepository.save(new ReceiptDetail(0, cart.getQuantity(),
                    price, cart.getDescription(), productCoupon,
                    cart.getProduct(), receipt));

            // Xóa sản phẩm ra khỏi giỏ hàng
            cartRepository.delete(cart);
        }

        return receipt;
    }

    public Receipt receiptRequestToReceipt(ReceiptRequest receiptRequest, Payment payment) {
        // Lấy trạng thái thanh toán
        ReceiptStatus receiptStatus = receiptStatusRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("ReceiptStatus not found"));

        // Lấy thông tin voucher (ReceiptCoupon)
        Optional<CustomerCoupon> customerCoupon = customerCouponRepository
                .findById(receiptRequest.getIdReceiptCoupon());

        ReceiptCoupon receiptCoupon = null;

        if (customerCoupon.isPresent()) {

            // lấy ReceiptCoupon được chọn
            receiptCoupon = customerCoupon.get().getReceiptCoupon();

            // Xóa Coupon ra khỏi kho của khách hàng
            customerCouponRepository.delete(customerCoupon.get());
        }

        // Lấy thông tin khách hàng
        Customer customer = customerService.findCustomerById(userService.getUser().getId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Khởi tạo đơn hàng mới
        return new Receipt(
                0,
                receiptRequest.getAddress(),
                receiptRequest.getNameReciver(),
                receiptRequest.getPhoneReciever(),
                new Date(),
                null,
                receiptRequest.getNote(),
                receiptStatus, // Đã kiểm tra tồn tại
                payment,
                receiptCoupon, // Có thể null nếu không tìm thấy
                customer // Đã kiểm tra tồn tại
                , false);
    }

}
