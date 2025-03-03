package fpoly.electroland.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.service.CustomerCouponService;
import fpoly.electroland.service.PaymentTypeService;
import fpoly.electroland.service.ReceiptCouponService;

@RestController
public class PaymentTypeController {

    @Autowired
    PaymentTypeService paymentTypeService;

    @Autowired
    ReceiptCouponService receiptCouponService;

    @Autowired
    CustomerCouponService customerCouponService;

    @GetMapping("/paymentType")
    public Object getpaymentType() {
        return paymentTypeService.getList();
    }
}
