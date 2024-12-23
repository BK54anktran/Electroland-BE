package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.CartProductAttribute;
import fpoly.electroland.repository.CartProductAttributeReponsitory;

@Service
public class CartProductAttributeService {

    @Autowired
    CartProductAttributeReponsitory cartProductAttributeReponsitory;

    public CartProductAttribute creatCartPA(CartProductAttribute cartPA){
        return cartProductAttributeReponsitory.save(cartPA);
    }
}
