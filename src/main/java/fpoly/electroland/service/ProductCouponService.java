package fpoly.electroland.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.ProductCoupon;
import fpoly.electroland.repository.ProductCouponRepository;

@Service
public class ProductCouponService {

    @Autowired
    ProductCouponRepository productCouponRepository;

    public Object getList() {
        return productCouponRepository.findAll();
    }

    public List<ProductCoupon> getAll(){
        return productCouponRepository.findAll();
    }
}   
