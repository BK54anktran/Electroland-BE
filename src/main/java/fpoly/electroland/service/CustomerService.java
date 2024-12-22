package fpoly.electroland.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.Customer;
import fpoly.electroland.repository.CustomerReponsitory;

@Service
public class CustomerService {

    @Autowired
    CustomerReponsitory customerReponsitory;

    public Optional<Customer> getCustomer(String email) {
        return customerReponsitory.findByEmail(email);
    }

}
