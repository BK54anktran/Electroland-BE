package fpoly.electroland.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.Color;
import fpoly.electroland.model.Product;
import fpoly.electroland.model.ProductColor;
import fpoly.electroland.repository.ProductColorReponsitory;

@Service
public class ProductColorService {
    @Autowired
    ProductColorReponsitory productColorReponsitory;
    
   
      public ProductColor getProductColorById(int id){
        Optional<ProductColor> pc = productColorReponsitory.findById(id);
        return pc.get() != null ? pc.get(): null;
      }
    
}
