package fpoly.electroland.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.Color;
import fpoly.electroland.model.Product;
import fpoly.electroland.model.ProductColor;
import fpoly.electroland.repository.ProductColorRepository;

@Service
public class ProductColorService {
    @Autowired
    ProductColorRepository productColorRepository;
    
   
      public ProductColor getProductColorById(int id){
        Optional<ProductColor> pc = productColorRepository.findById(id);
        return pc.get() != null ? pc.get(): null;
      }
    
}
