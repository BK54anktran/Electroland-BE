package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.TypeCustomer;
import fpoly.electroland.repository.TypeCustomerReponsitory;

@Service
public class TypeCustomerService {

    @Autowired
    TypeCustomerReponsitory typeCustomerReponsitory;

    public TypeCustomer getTypeCustomer(Integer id) {
        return typeCustomerReponsitory.findById(id).get();
    }

}
