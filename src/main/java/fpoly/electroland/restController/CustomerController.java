package fpoly.electroland.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.model.Customer;
import fpoly.electroland.service.CustomerService;

@RestController
@RequestMapping("/admin")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping("/customer")
    public List<Customer> GetAllList() {
        return customerService.getAll();
    }
      @PostMapping("/customer/save")
    public Customer addCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @PutMapping("/customer/update/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Integer id,@RequestParam boolean status) {
     
        Customer updatedCustomer = customerService.updateCustomer(id, status);
        
        if (updatedCustomer != null) {
            return ResponseEntity.ok(updatedCustomer);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    // Tìm kiếm khách hàng
    @GetMapping("/customer/search")
    public List<Customer> searchCustomers(@RequestParam String keyword) {
        return customerService.searchCustomers(keyword);
    }

    // Lọc khách hàng theo trạng thái
    @GetMapping("/customer/filter")
    public List<Customer> filterCustomers(@RequestParam boolean status) {
        return customerService.filterCustomersByStatus(status);
    }

    // Kết hợp tìm kiếm và lọc
    @GetMapping("/customer/search-filter")
    public List<Customer> searchAndFilterCustomers(@RequestParam String keyword, @RequestParam boolean status) {
        return customerService.searchAndFilterCustomers(keyword, status);
    }

}
