package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.Cart;
import fpoly.electroland.repository.CartReponsitory;

@Service
public class CartService {

    @Autowired
    CartReponsitory cartReponsitory;

    public void createCart(Cart cart){
        cartReponsitory.save(cart);
    }

}
