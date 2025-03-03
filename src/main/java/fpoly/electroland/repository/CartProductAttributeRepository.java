package fpoly.electroland.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.CartProductAttribute;
import fpoly.electroland.model.Attribute;
import fpoly.electroland.model.Cart;
import java.util.List;


@Repository
public interface CartProductAttributeRepository extends JpaRepository<CartProductAttribute, Integer> {
    void deleteByCart(Cart cart);
    List<CartProductAttribute> findByAttribute(Attribute attribute);
    void deleteByAttribute(Attribute attribute);
}
