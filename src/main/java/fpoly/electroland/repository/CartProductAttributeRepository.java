package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.CartProductAttribute;

@Repository
public interface CartProductAttributeRepository extends JpaRepository<CartProductAttribute, Integer> {

}
