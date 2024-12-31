package fpoly.electroland.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

}
