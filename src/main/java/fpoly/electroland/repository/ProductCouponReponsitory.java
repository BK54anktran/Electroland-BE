package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.ProductCoupon;

@Repository
public interface ProductCouponReponsitory extends JpaRepository<ProductCoupon, Integer> {

}
