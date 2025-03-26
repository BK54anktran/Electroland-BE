package fpoly.electroland.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import fpoly.electroland.dto.response.ProductCouponDto;
import fpoly.electroland.dto.response.ReceiptCouponDto;
import fpoly.electroland.model.Customer;
import fpoly.electroland.model.CustomerCoupon;
import fpoly.electroland.model.ProductCoupon;
import fpoly.electroland.model.ReceiptCoupon;
import fpoly.electroland.repository.CustomerCouponRepository;
import fpoly.electroland.repository.ReceiptCouponRepository;

@Service
public class CustomerCouponService {

    @Autowired
    CustomerCouponRepository customerCouponRepository;
    @Autowired
    private ReceiptCouponRepository receiptCouponRepository;
    @Autowired
    private CustomerService customerService;


    @Autowired
    UserService userService;

    public Object getList() {
        List<CustomerCoupon> list = customerCouponRepository.findByCustomerId(userService.getUser().getId());
        List<ProductCouponDto> listProductCoupon = new ArrayList<>();
        List<ReceiptCouponDto> listReceiptCouponDto = new ArrayList<>();
        for (CustomerCoupon customerCoupon : list) {
            if (customerCoupon.getProductCoupon() != null) {
                listProductCoupon.add(ProductCouponToDto(customerCoupon.getId(), customerCoupon.getProductCoupon()));
            } else
                listReceiptCouponDto.add(ReceiptCouponToDto(customerCoupon.getId(), customerCoupon.getReceiptCoupon()));
        }
        Map<String, Object> couponUser = new HashMap<>();
        couponUser.put("productCoupon", listProductCoupon);
        couponUser.put("receiptCoupon", listReceiptCouponDto);
        ;
        return couponUser;
    }

    ProductCouponDto ProductCouponToDto(int id, ProductCoupon productCoupon) {
        return new ProductCouponDto(id, productCoupon.getProduct().getId(), productCoupon.getProduct().getAvatar(),
                productCoupon.getDescription(), productCoupon.getValue());
    }

    ReceiptCouponDto ReceiptCouponToDto(int id, ReceiptCoupon receiptCoupon) {
        return new ReceiptCouponDto(id, receiptCoupon.getId(), receiptCoupon.getDiscountMoney(),
                receiptCoupon.getDiscountPercent(),
                receiptCoupon.getMaxDiscount(), receiptCoupon.getMinReceiptPrice(), receiptCoupon.getDescription());
    }

    public Object getListTrue() {
        List<CustomerCoupon> list = customerCouponRepository.findByCustomerIdAndStatusTrue(userService.getUser().getId());
        return list;
    }
       // Tạo mới ReceiptCoupon và lưu vào cơ sở dữ liệu
       public ReceiptCoupon createReceiptCoupon(ReceiptCoupon coupon) {
        // Log để kiểm tra coupon trước khi lưu
        System.out.println("Creating coupon with discount: " + coupon.getDiscountMoney());
        return receiptCouponRepository.save(coupon);
    }
    

    public void addReceiptCouponToCustomer(int customerId, ReceiptCoupon coupon) {
        // Log để kiểm tra
        System.out.println("Adding coupon for customer ID: " + customerId);
    
        // Lấy đối tượng Customer từ CustomerService dựa trên customerId
        Customer customer = customerService.getCustomer(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
    
        // Tạo CustomerCoupon mới và gán các giá trị
        CustomerCoupon customerCoupon = new CustomerCoupon();
        customerCoupon.setCustomer(customer); // Gán đối tượng Customer vào customerCoupon
        customerCoupon.setReceiptCoupon(coupon); // Gán ReceiptCoupon vào customerCoupon
        customerCoupon.setStatus(true); // Đảm bảo coupon có trạng thái đúng
    
        // Lưu CustomerCoupon vào cơ sở dữ liệu
        customerCouponRepository.save(customerCoupon);
        System.out.println("Coupon successfully added for customer ID: " + customerId);
    }
    
}
