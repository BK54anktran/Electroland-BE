package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.CustomerCoupon;

@Repository
public interface CustomerCouponRepository extends JpaRepository<CustomerCoupon, Integer> {

}
