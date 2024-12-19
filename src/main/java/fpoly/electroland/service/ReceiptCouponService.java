package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.repository.ReceiptCouponReponsitory;

@Service
public class ReceiptCouponService {

    @Autowired
    ReceiptCouponReponsitory receiptCouponReponsitory;

}
