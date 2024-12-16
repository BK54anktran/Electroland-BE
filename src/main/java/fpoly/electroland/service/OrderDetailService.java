package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.repository.OrderDetailReponsitory;

@Service
public class OrderDetailService {

    @Autowired
    OrderDetailReponsitory orderDetailReponsitory;

}
