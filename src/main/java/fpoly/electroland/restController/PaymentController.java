package fpoly.electroland.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.model.Payment;
import fpoly.electroland.service.PaymentService;

@RestController
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping("/payment")
    public Object CreatePayment(@RequestBody Payment payment) {

        return paymentService.create(payment);
    }
}
