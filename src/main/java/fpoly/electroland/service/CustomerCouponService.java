package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.repository.CustomerCouponRepository;

@Service
public class CustomerCouponService {

    @Autowired
    CustomerCouponRepository customerCouponRepository;

}
