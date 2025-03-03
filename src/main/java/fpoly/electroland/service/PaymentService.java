package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.Payment;
import fpoly.electroland.repository.PaymentRepository;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    public Payment create(Payment payment) {
        return paymentRepository.save(payment);
    }

}
