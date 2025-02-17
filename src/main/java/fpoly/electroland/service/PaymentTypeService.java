package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.repository.PaymentTypeRepository;

@Service
public class PaymentTypeService {

    @Autowired
    PaymentTypeRepository paymentTypeRepository;

}
