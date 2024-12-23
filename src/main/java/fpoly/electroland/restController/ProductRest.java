package fpoly.electroland.restController;

import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import fpoly.electroland.model.Attribute;
import fpoly.electroland.model.Cart;
import fpoly.electroland.model.CartProductAttribute;
import fpoly.electroland.model.Color;
import fpoly.electroland.model.Customer;
import fpoly.electroland.model.Product;
import fpoly.electroland.model.ProductColor;
import fpoly.electroland.model.User;
import fpoly.electroland.repository.ProductColorReponsitory;
import fpoly.electroland.service.AttributeService;
import fpoly.electroland.service.CartProductAttributeService;
import fpoly.electroland.service.CartService;
import fpoly.electroland.service.ColorService;
import fpoly.electroland.service.CustomerService;
import fpoly.electroland.service.ProductColorService;
import fpoly.electroland.service.ProductService;

import java.util.HashMap;
import java.util.Iterator;
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

    @Autowired
    ColorService colorService;

    @Autowired
    AttributeService attributeService;

    @Autowired
    CartProductAttributeService cartProductAttributeService;

    @Autowired
    ProductColorService productColorService;

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
    public String postToCart(@RequestBody Object entity) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.valueToTree(entity);

        String descriptoin = "";
        Integer productId = jsonNode.get("id").asInt();

        JsonNode valuesNode = jsonNode.get("values");
        Iterator<Map.Entry<String, JsonNode>> fields = valuesNode.fields();

        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            String key = field.getKey();
            int value = field.getValue().asInt();
            if (key.equals("color")) {
                
                descriptoin += "Màu: " + colorService.getColorById(value).get().getNameColor();
            } else {
                
                descriptoin += ", " + key + ": " + attributeService.getAttributeById(value).getName();
            }
        }

        Product product = productService.getProduct(productId);

        Optional<Customer> cs = customerService.findCustomerById(user.getId());
        if (cs.isPresent()) {
            Optional<Cart> cart = cartService.getCartByProductAndDesAndUser(product, descriptoin, cs.get());
            if(cart.isPresent()){
                cart.get().setQuantity(cart.get().getQuantity() + 1);
                cartService.updateCart(cart.get());
            } else {
				Cart newCart = new Cart(0, 1, descriptoin, true, product, cs.get());
				newCart = cartService.createCart(newCart);
                Iterator<Map.Entry<String, JsonNode>> values = valuesNode.fields();
                while (values.hasNext()) {
                    Map.Entry<String, JsonNode> value = values.next();
                    String key = value.getKey();
                    int valueAtt = value.getValue().asInt();
                    System.out.println("Helllo !!!!!!");
                    if (key.equals("color")) {
                        CartProductAttribute cartPA = new CartProductAttribute();
						cartPA.setCart(newCart);
						cartPA.setProductColor(productColorService.getProductColorById(valueAtt)); 
						cartProductAttributeService.creatCartPA(cartPA);
                        
                    } else {
                        Attribute attribute = attributeService.getAttributeById(valueAtt);
                        CartProductAttribute cartPA = new CartProductAttribute();
						cartPA.setCart(newCart);
						cartPA.setAttribute(attribute);
						cartProductAttributeService.creatCartPA(cartPA);
                        
                    }
                }
				
			}
        }

        return "String";
    }

}
