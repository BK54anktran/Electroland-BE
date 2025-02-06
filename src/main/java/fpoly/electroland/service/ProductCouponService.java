package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.repository.ProductCouponRepository;

@Service
public class ProductCouponService {

    @Autowired
    ProductCouponRepository productCouponRepository;

}
