package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.ProductAttribute;

@Repository
public interface ProductAttributeReponsitory extends JpaRepository<ProductAttribute, Integer> {

}
