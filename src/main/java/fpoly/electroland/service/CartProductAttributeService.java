package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.CartProductAttribute;

import fpoly.electroland.repository.CartProductAttributeRepository;

@Service
public class CartProductAttributeService {

    @Autowired
    CartProductAttributeRepository cartProductAttributeRepository;

    public CartProductAttribute creatCartPA(CartProductAttribute cartPA){
        return cartProductAttributeRepository.save(cartPA);
    }
}
