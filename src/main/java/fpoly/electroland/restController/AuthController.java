package fpoly.electroland.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import fpoly.electroland.model.Customer;
import fpoly.electroland.model.Employee;
import fpoly.electroland.model.User;
import fpoly.electroland.service.CustomerService;
import fpoly.electroland.service.EmployeeService;
import fpoly.electroland.service.UserService;
import fpoly.electroland.util.ResponseEntityUtil;

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
        if (!employeeService.getEmployee(user.getEmail()).isPresent())
            return ResponseEntityUtil.unauthorizedError("Tài khoản không tồn tại");
        return userService.authentication_getData(user.getEmail(), user.getPassword());
    }

    @PostMapping("/login")
    public Object authenticate(@RequestBody User user) throws AuthenticationException {
        if (!customerService.getCustomer(user.getEmail()).isPresent())
            return ResponseEntityUtil.unauthorizedError("Tài khoản không tồn tại");
        return userService.authentication_getData(user.getEmail(), user.getPassword());
    }

    @PostMapping("/register")
    public Object register(@RequestBody Customer user) throws AuthenticationException {
        if (customerService.getCustomer(user.getEmail()).isPresent())
            return ResponseEntityUtil.badRequest("Tài khoản đã tồn tại");
        else {
            customerService.createCustomer(user);
            return userService.authentication_getData(user.getEmail(), user.getPassword());
        }
    }
}
