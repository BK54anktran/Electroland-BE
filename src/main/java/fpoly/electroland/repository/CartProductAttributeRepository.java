package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.CartProductAttribute;
import fpoly.electroland.model.Cart;

@Repository
public interface CartProductAttributeRepository extends JpaRepository<CartProductAttribute, Integer> {
    void deleteByCart(Cart cart);
}
