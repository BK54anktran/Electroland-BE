package fpoly.electroland.restController;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class CartController {
    @PostMapping("/cart")
    public String postMethodName(@RequestBody Object object) {
        System.out.println(object);
        return null;
    }
    
}
