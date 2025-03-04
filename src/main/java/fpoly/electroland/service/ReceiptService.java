package fpoly.electroland.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
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

        // Tìm nhân viên thực hiện hành động
        Employee creatorEmployee = employeeRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên với ID: " + userId));

        // Ghi lại hành động vào bảng Action (Chỉ log ID để tránh StackOverflow)
        createAction.createAction(
                "Receipt",
                "UPDATE",
                savedReceipt.getId(),
                "Old Status: " + oldStatusId, // Sử dụng biến đã lưu
                "New Status: " + savedReceipt.getReceiptStatus().getId(), // Lấy trạng thái sau khi update
                creatorEmployee);

        return savedReceipt;
    }

    public Receipt createCart(ReceiptRequest receiptRequest) {
        Payment payment = paymentRepository.save(new Payment(0, receiptRequest.getCreateTime(), new Date(),
                receiptRequest.getTotalAmount(), receiptRequest.getContent(),
                paymentTypeRepository.findById(receiptRequest.getPaymentType()).get(),
                paymentStatusRepository.findById(receiptRequest.getPaymentType()).get()));

        System.out.println(receiptRequest.getAddress());
        Receipt receipt = receiptRepository.save(receiptRequestToReceipt(receiptRequest, payment));
        List<Cart> cartList = cartRepository.findByCustomerIdAndStatus(userService.getUser().getId(), true);
        List<Integer> listCouponProduct = receiptRequest.getListCouponProduct();
        for (Cart cart : cartList) {
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
            if (removei >= 0)
                listCouponProduct.remove(removei);
            Double price = cart.getProduct().getPriceDiscount() != null ? cart.getProduct().getPriceDiscount()
                    : cart.getProduct().getPrice();
            for (CartProductAttribute att : cart.getCartProductAttributes()) {
                price += att.getAttribute().getAttributePrice();
                cartProductAttributeRepository.delete(att);
            }
            receiptDetailRepository.save(new ReceiptDetail(0, cart.getQuantity(),
                    price, cart.getDescription(), productCoupon,
                    cart.getProduct(), receipt));
            cartRepository.delete(cart);
        }
        if (receiptRequest.getIdReceiptCoupon() != 0)
            customerCouponRepository.deleteById(receiptRequest.getIdReceiptCoupon());
        return receipt;
    }

    public Receipt receiptRequestToReceipt(ReceiptRequest receiptRequest, Payment payment) {
        // Lấy trạng thái thanh toán
        ReceiptStatus receiptStatus = receiptStatusRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("ReceiptStatus not found"));

        // Lấy thông tin voucher (ReceiptCoupon)
        ReceiptCoupon receiptCoupon = receiptCouponRepository.findById(receiptRequest.getIdReceiptCoupon())
                .orElse(null); // Trả về null nếu không tìm thấy (có thể cần xử lý tùy logic)

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
        );
    }

}
