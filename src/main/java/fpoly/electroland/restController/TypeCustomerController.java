package fpoly.electroland.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.service.CustomerService;
import fpoly.electroland.service.TypeCustomerService;
import fpoly.electroland.service.UserService;

@RestController
public class TypeCustomerController {
    @Autowired
    TypeCustomerService typeCustomerService;

    @Autowired
    UserService userService;

}
