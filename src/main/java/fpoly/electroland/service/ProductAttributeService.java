package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.ProductAttribute;
import fpoly.electroland.repository.ProductAttributeRepository;

@Service
public class ProductAttributeService {

    @Autowired
    ProductAttributeRepository productAttributeRepository;

    public ProductAttribute findProductAttributeById(int id){
        return productAttributeRepository.findById(id).get();
    }

}
