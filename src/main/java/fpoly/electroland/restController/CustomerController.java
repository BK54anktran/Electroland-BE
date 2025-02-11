package fpoly.electroland.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.model.Customer;
import fpoly.electroland.model.Product;
import fpoly.electroland.service.CustomerService;
import fpoly.electroland.service.UserService;
import fpoly.electroland.util.ResponseEntityUtil;

@RestController
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @Autowired
    UserService userService;

    @GetMapping("/admin/customer")
    public List<Customer> GetAllList() {
        return customerService.getAll();
    }

    @GetMapping("/userinfor")
    public Customer getUser() {
        System.out.println(userService.getUser());
        Customer customer = customerService.getCustomer(userService.getUser().getId()).get();
        return customer;
    }

    @PostMapping("/userAvatar")
    public void saveUserAva(@RequestBody String avaUrl) {
        avaUrl = avaUrl.replace("\"", "");
        System.out.println(userService.getUser());
        int id = userService.getUser().getId();
        Customer customer = customerService.getCustomer(userService.getUser().getId()).get();
        customer.setAvatar(avaUrl);
        customerService.updateCustomer(id, customer);
        System.out.println(avaUrl);
    }
}
