package fpoly.electroland.restController;

import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import fpoly.electroland.model.Cart;
import fpoly.electroland.model.Customer;
import fpoly.electroland.model.User;
import fpoly.electroland.service.CartService;
import fpoly.electroland.service.CustomerService;
import fpoly.electroland.service.ProductService;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin("*")
public class ProductRest {

    @Autowired
    ProductService productService;

    @Autowired
    CartService cartService;

    @Autowired
    CustomerService customerService;

    @GetMapping("/product")
    public Object getMethodName(@RequestParam(name = "id", required = false, defaultValue = "0") int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        System.out.println(user.getId());
        if (id == 0) {
            return productService.getProduct();
        }
        return productService.getProduct(id);
    }

    @PostMapping("/cart")
    public String postMethodName(@RequestBody String entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        try {
            // Chuyển chuỗi JSON thành Map
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> cartData = objectMapper.readValue(entity, Map.class);

            // In ra từng giá trị trong Map
            Integer productId = (Integer) cartData.get("productId");
            Map<String, String> values = (Map<String, String>) cartData.get("values");

            String description = values.entrySet().stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .collect(Collectors.joining(", "));

            Optional<Customer> cs = customerService.findCustomerById(user.getId());
            Cart cart = new Cart();
            if (cs.isPresent()) {
                cart.setProduct(productService.getProduct(productId));
                cart.setDescription(description);
                cart.setQuantity(1);
                cart.setStatus(true);
                cart.setCustomer(cs.get());
                cartService.createCart(cart);
            }
           
            // System.out.println(user.toString());
            // Xử lý thêm nếu cần

            return "Dữ liệu nhận được: " + cartData;
        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi khi xử lý dữ liệu!";
        }
    }

}
