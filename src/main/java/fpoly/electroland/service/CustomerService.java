package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.repository.CustomerReponsitory;

@Service
public class CustomerService {

    @Autowired
    CustomerReponsitory customerReponsitory;

}
