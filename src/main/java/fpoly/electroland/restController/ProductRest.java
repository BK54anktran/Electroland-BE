package fpoly.electroland.restController;

import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.model.User;
import fpoly.electroland.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin("*")
public class ProductRest {

    @Autowired
    ProductService productService;

    @GetMapping("/product")
    public Object getMethodName(@RequestParam(name = "id", required = false, defaultValue = "0") int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getPrincipal().toString());
        if (id == 0) {
            return productService.getProduct();
        }
        return productService.getProduct(id);
    }

}
