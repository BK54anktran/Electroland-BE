package fpoly.electroland.restController;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import fpoly.electroland.dto.response.ProductCouponDto;
import fpoly.electroland.dto.response.ReceiptCouponDto;
import fpoly.electroland.model.CustomerCoupon;
import fpoly.electroland.model.Product;
import fpoly.electroland.model.ProductCoupon;
import fpoly.electroland.model.ReceiptCoupon;
import fpoly.electroland.repository.CustomerCouponRepository;
import fpoly.electroland.service.CustomerCouponService;
import fpoly.electroland.service.CustomerService;
import fpoly.electroland.service.ProductCouponService;
import fpoly.electroland.service.ProductService;
import fpoly.electroland.service.ReceiptCouponService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class CouponController {

    @Autowired
    ProductCouponService productCouponService;

    @Autowired
    ReceiptCouponService receiptCouponService;

    @Autowired
    CustomerCouponService customerCouponService;

    @Autowired
    CustomerService customerService;

    @Autowired
    ProductService productService;

    @GetMapping("/coupon")
    public Object getProductCoupon() {
        return productCouponService.getList();
    }

    @GetMapping("/user/coupon")
    public Object getUserProductCoupon() {
        return customerCouponService.getList();
    }

    @GetMapping("/coupontrue")
    public Object getCouponByUserTrue() {
        return customerCouponService.getListTrue();
    }

    @GetMapping("/allCoupon")
    public Object getListAll() {
        List<ProductCoupon> listProductCoupon = productCouponService.getAll();
        List<ReceiptCoupon> listReceiptCoupon = receiptCouponService.getListReceiptCoupons();

        Map<String, Object> couponUser = new HashMap<>();
        couponUser.put("productCoupon", listProductCoupon);
        couponUser.put("receiptCoupon", listReceiptCoupon);
        return couponUser;
    }

    @GetMapping("/userpoint")
    public Integer getUserPoint() {
        return customerCouponService.getUserPoint();
    }

    @PostMapping("/addToUserCoupon")
    public void addToUserCoupon(@RequestBody Map<String, Object> entity) {
        ObjectMapper objectMapper = new ObjectMapper();
        if (entity.containsKey("receiptCoupon")) {
            ReceiptCoupon receiptCoupon = objectMapper.convertValue(entity.get("receiptCoupon"), ReceiptCoupon.class);
            customerCouponService.addCustomerReciptCouponrRC(receiptCoupon);
            System.out.println("Nhận được ReceiptCoupon: " + receiptCoupon);
        } else if (entity.containsKey("productCoupon")) {
            Map<String, Object> productCouponMap = (Map<String, Object>) entity.get("productCoupon");
            Integer productId = objectMapper.convertValue(productCouponMap.get("product"), Product.class).getId();
            ProductCoupon productCoupon = objectMapper.convertValue(entity.get("productCoupon"), ProductCoupon.class);
            productCoupon.setProduct(productService.getProduct(productId));
            customerCouponService.addCustomerProductCouponrRC(productCoupon);
            System.out.println("Nhận được ProductCoupon: " + productCoupon);
        } else {
            System.out.println("sai");
        }
    }

}
