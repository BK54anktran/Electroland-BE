package fpoly.electroland.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import fpoly.electroland.model.Customer;
import fpoly.electroland.model.User;
import fpoly.electroland.service.CustomerService;
import fpoly.electroland.service.EmployeeService;
import fpoly.electroland.service.UserService;

@RestController
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    CustomerService customerService;

    @Autowired
    EmployeeService employeeService;

    @PostMapping("admin/login")
    public Object authenticateAdmin(@RequestBody User user) throws AuthenticationException {
        return employeeService.getEmployee(user.getEmail()).isPresent()
                ? userService.authentication_getData(user.getEmail(), user.getPassword())
                : "Not Found";
    }

    @PostMapping("/login")
    public Object authenticate(@RequestBody User user) throws AuthenticationException {
        return customerService.getCustomer(user.getEmail()).isPresent()
                ? userService.authentication_getData(user.getEmail(), user.getPassword())
                : "Not Found";
    }

    @PostMapping("/register")
    public Object register(@RequestBody Customer customer) throws AuthenticationException {
        if (!customerService.getCustomer(customer.getEmail()).isPresent()) {
            customerService.createCustomer(customer);
            return userService.authentication_getData(customer.getEmail(), customer.getPassword());
        }
        return "Exist";
    }
}
