package fpoly.electroland.restController;

import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.model.Customer;
import fpoly.electroland.model.User;
import fpoly.electroland.service.ProductService;
import fpoly.electroland.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class ProductRest {

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @GetMapping("/product")
    public Object getMethodName(@RequestParam(name = "id", required = false, defaultValue = "0") int id) {
        System.out.println(userService.getUser().toString());
        if (id == 0) {
            return productService.getProduct();
        }
        return productService.getProduct(id);
    }

}
