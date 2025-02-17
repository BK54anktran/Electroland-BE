package fpoly.electroland.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.Cart;
import fpoly.electroland.model.Customer;
import fpoly.electroland.model.Product;
import fpoly.electroland.repository.CartRepository;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    public Cart createCart(Cart cart){
        return cartRepository.save(cart);
    }

    public Cart updateCart(Cart cart){
        return cartRepository.save(cart);
    }

    public Optional<Cart> getCartByProductAndDesAndUser(Product product, String des, Customer customer){
        return cartRepository.findByProductAndDescriptionAndCustomer(product, des, customer);
    }
}
