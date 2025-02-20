package fpoly.electroland.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.repository.CustomerCouponRepository;
import fpoly.electroland.service.CustomerCouponService;
import fpoly.electroland.service.ProductCouponService;
import fpoly.electroland.service.ReceiptCouponService;

@RestController
public class CouponController {

    @Autowired
    ProductCouponService productCouponService;

    @Autowired
    ReceiptCouponService receiptCouponService;

    @Autowired
    CustomerCouponService customerCouponService;


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
}
