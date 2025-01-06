package fpoly.electroland.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.Customer;
import fpoly.electroland.repository.CustomerReponsitory;

@Service
public class CustomerService {

    @Autowired
    CustomerReponsitory customerReponsitory;

    @Autowired
    TypeCustomerService typeCustomerService;

    public Optional<Customer> getCustomer(String email) {
        return customerReponsitory.findByEmail(email);
    }

    public Optional<Customer> getCustomer(Integer id) {
        return customerReponsitory.findById(id);
    }
    public Customer updateCustomer(Integer id, Customer updatedCustomer) {
        Optional<Customer> customerOptional = customerReponsitory.findById(id);
        if (customerOptional.isPresent()) {
            Customer existingCustomer = customerOptional.get();
            // Cập nhật thông tin khách hàng
            existingCustomer.setFullName(updatedCustomer.getFullName());
            existingCustomer.setPhoneNumber(updatedCustomer.getPhoneNumber());
            existingCustomer.setDateOfBirth(updatedCustomer.getDateOfBirth());
            existingCustomer.setGender(updatedCustomer.getGender());
            existingCustomer.setEmail(updatedCustomer.getEmail());
            existingCustomer.setPassword(updatedCustomer.getPassword());
            existingCustomer.setStatus(updatedCustomer.getStatus());
            existingCustomer.setTypeCustomer(updatedCustomer.getTypeCustomer());
            return customerReponsitory.save(existingCustomer);
        } else {
            throw new RuntimeException("Customer not found with id " + id);  // Ném ngoại lệ nếu không tìm thấy khách hàng
        }
    }
    
    public Customer createCustomer(Customer customer) {
        customer.setTypeCustomer(typeCustomerService.getTypeCustomer(1));
        customer.setAvatar("");
        customer.setStatus(true);
        return customerReponsitory.save(customer);
    }
    public List<Customer> getAll(){
        return customerReponsitory.findAll();
    }

}
