package fpoly.electroland.restController;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import fpoly.electroland.model.Customer;
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
        return employeeService.getEmployee(user.getEmail()).isPresent()
                ? userService.authentication_getData(user.getEmail(), user.getPassword())
                : "Not Found";
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
