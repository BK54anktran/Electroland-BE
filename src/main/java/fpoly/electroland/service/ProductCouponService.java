package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.repository.ProductCouponReponsitory;

@Service
public class ProductCouponService {

    @Autowired
    ProductCouponReponsitory productCouponReponsitory;

}
