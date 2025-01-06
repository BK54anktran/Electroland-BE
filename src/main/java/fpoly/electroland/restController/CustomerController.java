package fpoly.electroland.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public Customer updateCustomer(@PathVariable Integer id, @RequestBody Customer customer) {
        System.out.println(customer);
        return customerService.updateCustomer(id, customer);

    }

}
