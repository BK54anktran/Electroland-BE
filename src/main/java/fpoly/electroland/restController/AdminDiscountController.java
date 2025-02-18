package fpoly.electroland.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import fpoly.electroland.model.ReceiptCoupon;
import fpoly.electroland.service.ReceiptCouponService;


@Controller
@RequestMapping("/admin/discount")
public class AdminDiscountController {
    
    @Autowired
    ReceiptCouponService receiptCouponService;

    @GetMapping("/discountOrder")
    public List<ReceiptCoupon> getListDiscountOrder() {
        return receiptCouponService.getAllReceiptCoupon();
    }
    
}
