package fpoly.electroland.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.Attribute;
import fpoly.electroland.model.Cart;
import fpoly.electroland.model.Customer;
import fpoly.electroland.model.Product;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Cart> findByCustomerId(int id);

    Optional<Cart> findByIdAndCustomerId(int id, int customerId);

    Optional<Cart> findByProductAndDescriptionAndCustomer(Product product, String description, Customer customer);

    

}
