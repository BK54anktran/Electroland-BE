package fpoly.electroland.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.ReceiptCoupon;
import fpoly.electroland.repository.ReceiptCouponRepository;

@Service
public class ReceiptCouponService {

    @Autowired
    ReceiptCouponRepository receiptCouponRepository;

    public List<ReceiptCoupon> getAllReceiptCoupon() {
        return receiptCouponRepository.findAll();
    }

}
