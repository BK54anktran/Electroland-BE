package fpoly.electroland.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.Product;
import fpoly.electroland.repository.ProductReponsitory;

@Service
public class ProductService {

    @Autowired
    ProductReponsitory productReponsitory;

    public List<Product> getProduct() {
        return productReponsitory.findAll();
    }

    public Product getProduct(int id) {
        Optional<Product> product = productReponsitory.findById(id);
        if (product.isPresent()) {
            return product.get();
        }
        return null;
    }

}
