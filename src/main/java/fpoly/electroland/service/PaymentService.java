package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.repository.PaymentReponsitory;

@Service
public class PaymentService {

    @Autowired
    PaymentReponsitory paymentReponsitory;

}
