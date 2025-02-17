package fpoly.electroland.restController;

import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.model.Attribute;
import fpoly.electroland.model.Cart;
import fpoly.electroland.model.CartProductAttribute;
import fpoly.electroland.model.Customer;
import fpoly.electroland.model.Product;
import fpoly.electroland.model.ProductAttribute;
import fpoly.electroland.service.AttributeService;
import fpoly.electroland.service.CartProductAttributeService;
import fpoly.electroland.service.CartService;
import fpoly.electroland.service.CustomerService;
import fpoly.electroland.service.ProductAttributeService;
import fpoly.electroland.service.ProductService;
import fpoly.electroland.service.UserService;

import java.lang.foreign.Linker.Option;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class CartController {
    @Autowired
    CartService cartService;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    CustomerService customerService;

    @Autowired
    CartProductAttributeService cartProductAttributeService;

    @Autowired
    AttributeService attributeService;

    @Autowired
    ProductAttributeService productAttributeService;

    @PostMapping("/cart")
    public void createCart(@RequestBody Map<String, Object> object) {
        System.out.println("hello");
        Map<String, Integer> values = (Map<String, Integer>) object.get("values");

        Integer idProdct = (Integer) object.get("id");

        Product product = productService.getProduct(idProdct);

        Customer customer = customerService.findCustomerById(userService.getUser().getId()).get();

        StringBuilder desBuilder = new StringBuilder();

        values.forEach((key, value) -> {
            Attribute Attribute = attributeService.getAttributeById(value);
            System.out.println(Attribute);
            desBuilder.append(key).append(": ").append(Attribute.getName()).append(", ");
        });
        
        // Loại bỏ dấu phẩy cuối cùng nếu có
        String des = desBuilder.length() > 0 ? desBuilder.substring(0, desBuilder.length() - 2) : "";
        

        Optional<Cart> cart = cartService.getCartByProductAndDesAndUser(product, des, customer);
        if (!cart.isPresent()) {
            Cart newCart = new Cart();
            newCart.setCustomer(customer);
            newCart.setDescription(des);
            newCart.setProduct(product);
            newCart.setQuantity(1);
            newCart.setStatus(true);
            cartService.createCart(newCart);
            System.out.println(newCart);
            values.forEach((key, value) -> {
                CartProductAttribute newCartProductAttribute = new CartProductAttribute();
                newCartProductAttribute.setAttribute(attributeService.getAttributeById(value)); 
                newCartProductAttribute.setCart(newCart);
                cartProductAttributeService.creatCartPA(newCartProductAttribute);
                System.out.println(newCartProductAttribute);
            });
        }else{
            int quantity = cart.get().getQuantity();
            cart.get().setQuantity(quantity+=1);
            System.out.println(cart.get());
        }
    }

}
