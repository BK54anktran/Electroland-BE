package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.repository.ProductAttributeReponsitory;

@Service
public class ProductAttributeService {

    @Autowired
    ProductAttributeReponsitory productAttributeReponsitory;

}
