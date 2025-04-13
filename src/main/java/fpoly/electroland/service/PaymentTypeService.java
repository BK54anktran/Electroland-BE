package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.PaymentType;
import fpoly.electroland.repository.PaymentTypeRepository;

@Service
public class PaymentTypeService {

    @Autowired
    PaymentTypeRepository paymentTypeRepository;

    public Object getList() {
        return paymentTypeRepository.findAll();
    }

    public PaymentType getPaymentById(int id) {
        return paymentTypeRepository.findById(id).get();
    }
}
