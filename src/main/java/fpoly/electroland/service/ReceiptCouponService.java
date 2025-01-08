package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.repository.ReceiptCouponRepository;

@Service
public class ReceiptCouponService {

    @Autowired
    ReceiptCouponRepository receiptCouponRepository;

}
