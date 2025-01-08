package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.TypeCustomer;
import fpoly.electroland.repository.TypeCustomerRepository;

@Service
public class TypeCustomerService {

    @Autowired
    TypeCustomerRepository typeCustomerRepository;

    public TypeCustomer getTypeCustomer(Integer id) {
        return typeCustomerRepository.findById(id).get();
    }

}
