package fpoly.electroland.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import fpoly.electroland.model.ProductCoupon;

@Repository
public interface ProductCouponRepository extends JpaRepository<ProductCoupon, Integer> {
    Optional<ProductCoupon> findById(Long id);
    List<ProductCoupon> findByPointOrProductNameContainingOrValue(Integer point, String productName, Double value);
}
