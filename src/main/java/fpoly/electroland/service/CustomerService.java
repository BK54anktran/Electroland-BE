package fpoly.electroland.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.Customer;
import fpoly.electroland.model.User;
import fpoly.electroland.repository.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    TypeCustomerService typeCustomerService;

    public Optional<Customer> findCustomerById(Integer id){
        return customerRepository.findById(id);
    }
    public Optional<Customer> getCustomer(String email) {
        return customerRepository.findByEmail(email);
    }

    public Optional<Customer> getCustomer(Integer id) {
        return customerRepository.findById(id);
    }

    public Customer updateCustomer(Integer id, Customer updatedCustomer) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
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
            return customerRepository.save(existingCustomer);
        } else {
            throw new RuntimeException("Customer not found with id " + id); // Ném ngoại lệ nếu không tìm thấy khách
                                                                            // hàng
        }
    }
    public Customer updateCustomer(Integer id, boolean status) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isPresent()) {
            Customer existingCustomer = customerOptional.get();
            // Cập nhật thông tin khách hàng;
            existingCustomer.setStatus(status);
            return customerRepository.save(existingCustomer);
        } else {
            throw new RuntimeException("Customer not found with id " + id);  // Ném ngoại lệ nếu không tìm thấy khách hàng
        }
    }
    
    public Customer createCustomer(Customer customer) {
        customer.setTypeCustomer(typeCustomerService.getTypeCustomer(1));
        customer.setAvatar("");
        customer.setStatus(true);
        return customerRepository.save(customer);
    }

    public Customer createCustomerGoogle(Customer customer){
        customer.setStatus(true);
        customer.setTypeCustomer(typeCustomerService.getTypeCustomer(1));
        return customerRepository.save(customer);
    }

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }
      // Tìm kiếm khách hàng theo từ khóa
      public List<Customer> searchCustomers(String keyword) {
        return customerRepository.searchByKeyword(keyword);
    }

    // Lọc khách hàng theo trạng thái
    public List<Customer> filterCustomersByStatus(boolean status) {
        return customerRepository.findByStatus(status);
    }

    // Kết hợp tìm kiếm và lọc
    public List<Customer> searchAndFilterCustomers(String keyword, boolean status) {
        return customerRepository.searchAndFilter(keyword, status);
    }

}
