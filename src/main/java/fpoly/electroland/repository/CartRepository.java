package fpoly.electroland.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Cart> findByCustomerId(int id);

    Optional<Cart> findByIdAndCustomerId(int id, int customerId);

}
