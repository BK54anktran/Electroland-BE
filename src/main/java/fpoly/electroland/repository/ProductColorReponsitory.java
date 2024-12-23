package fpoly.electroland.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.Color;
import fpoly.electroland.model.Product;
import fpoly.electroland.model.ProductColor;

@Repository
public interface ProductColorReponsitory extends JpaRepository<ProductColor, Integer> {
    Optional<ProductColor> findByProductIdAndColorId(Product Product, Color color);
    
}
