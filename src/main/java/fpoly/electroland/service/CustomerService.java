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
    public Customer updateCustomer(Integer id, boolean status) {
        Optional<Customer> customerOptional = customerReponsitory.findById(id);
        if (customerOptional.isPresent()) {
            Customer existingCustomer = customerOptional.get();
            // Cập nhật thông tin khách hàng;
            existingCustomer.setStatus(status);
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
      // Tìm kiếm khách hàng theo từ khóa
      public List<Customer> searchCustomers(String keyword) {
        return customerReponsitory.searchByKeyword(keyword);
    }

    // Lọc khách hàng theo trạng thái
    public List<Customer> filterCustomersByStatus(boolean status) {
        return customerReponsitory.findByStatus(status);
    }

    // Kết hợp tìm kiếm và lọc
    public List<Customer> searchAndFilterCustomers(String keyword, boolean status) {
        return customerReponsitory.searchAndFilter(keyword, status);
    }

}
