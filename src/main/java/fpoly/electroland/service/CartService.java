package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.repository.CartRepository;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

}
