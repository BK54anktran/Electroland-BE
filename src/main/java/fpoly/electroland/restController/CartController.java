package fpoly.electroland.restController;

import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class CartController {

    @Autowired
    CartService cartService;

    @GetMapping("/cart")
    public Object getListCart() {
        return cartService.getList();
    }

    @PutMapping("/cart")
    public Object updateCart(@RequestParam("id") int id,
            @RequestParam(name = "quantity", required = false, defaultValue = "0") int quantity) {
        if (quantity != 0) {
            return cartService.UpdateCart(id, quantity);
        }
        return cartService.UpdateCart(id);
    }

    @DeleteMapping("/cart")
    public Object DeleteCart(@RequestParam("id") int id) {
        return cartService.deleteCart(id);
    }

}
