package fpoly.electroland.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.Attribute;
import fpoly.electroland.model.ProductAttribute;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Integer> {
    List<Attribute> findByProductAttribute(ProductAttribute productAttribute);
    Optional<Attribute> findByNameAndProductAttributeId(String name, int productAttributeId);
    void deleteByProductAttributeId(int productAttributeId);
}
