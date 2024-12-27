package fpoly.electroland.restController;

import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.service.ProductService;
import fpoly.electroland.service.UserService;
import fpoly.electroland.util.ResponseEntityUtil;

import org.springframework.beans.factory.annotation.Autowired;
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
        userService.getUser();
        if (id == 0) {
            return ResponseEntityUtil.ok(productService.getProduct());
        }
        return ResponseEntityUtil.ok(productService.getProduct(id));
    }

}
