package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.repository.CustomerCouponReponsitory;

@Service
public class CustomerCouponService {

    @Autowired
    CustomerCouponReponsitory customerCouponReponsitory;

}
