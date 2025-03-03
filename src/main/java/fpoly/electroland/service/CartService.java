package fpoly.electroland.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.dto.response.CartDto;
import fpoly.electroland.model.Cart;
import fpoly.electroland.model.CartProductAttribute;
import fpoly.electroland.model.User;
import fpoly.electroland.repository.CartProductAttributeRepository;
import fpoly.electroland.model.Cart;
import fpoly.electroland.model.Customer;
import fpoly.electroland.model.Product;
import fpoly.electroland.repository.CartRepository;
import jakarta.transaction.Transactional;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartProductAttributeRepository cartProductAttributeRepository;

    @Autowired
    UserService userService;

    public List<Cart> getCartByUser(int id){
        List<Cart> list = cartRepository.findByCustomerId(userService.getUser().getId());
        return list;
    }
    public Object getList() {
        List<Cart> list = cartRepository.findByCustomerId(userService.getUser().getId());
        List<CartDto> listDto = new ArrayList<>();
        list.forEach((cart) -> {
            listDto.add(CartToCartDto(cart));
        });
        return listDto;
    }

    CartDto CartToCartDto(Cart cart) {
        Double price = cart.getProduct().getPriceDiscount() != null ? cart.getProduct().getPriceDiscount()
                : cart.getProduct().getPrice();
        for (CartProductAttribute att : cart.getCartProductAttributes()) {
            if(att.getAttribute().getAttributePrice() != null)
            price += att.getAttribute().getAttributePrice();
        }
        ;
        return new CartDto(cart.getId(), cart.getProduct().getId(), cart.getProduct().getName(),
                cart.getProduct().getAvatar(),
                cart.getDescription(), cart.getQuantity(), cart.getStatus(), price);
    }

    public Object UpdateCart(int id, int quantity) {
        Optional<Cart> cart = cartRepository.findByIdAndCustomerId(id, userService.getUser().getId());
        if (cart.isPresent()) {
            Cart cart2 = cart.get();
            cart2.setQuantity(cart2.getQuantity() + quantity > 0 ? cart2.getQuantity() + quantity : 1);
            cartRepository.save(cart2);
            return cart2;
        }
        return null;

    }

    public Object UpdateCart(int id) {
        Optional<Cart> cart = cartRepository.findByIdAndCustomerId(id, userService.getUser().getId());
        if (cart.isPresent()) {
            Cart cart2 = cart.get();
            cart2.setStatus(!cart2.getStatus());
            return cartRepository.save(cart2);
        }
        return null;
    }

    @Transactional
    public Object deleteCart(int id) {
        Optional<Cart> cart = cartRepository.findByIdAndCustomerId(id, userService.getUser().getId());
        if (cart.isPresent()) {
            cartProductAttributeRepository.deleteByCart(cart.get());
            cartRepository.deleteById(id);
            return true;
        }
        return null;
    }

    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart updateCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public Optional<Cart> getCartByProductAndDesAndUser(Product product, String des, Customer customer) {
        return cartRepository.findByProductAndDescriptionAndCustomer(product, des, customer);
    }

    public Object UpdateCartStatusAll(boolean status) {
        List<Cart> list = cartRepository.findByCustomerId(userService.getUser().getId());
        list.forEach((cart) -> {
            cart.setStatus(status);
            this.updateCart(cart);
        });
        return null;
    }
}
