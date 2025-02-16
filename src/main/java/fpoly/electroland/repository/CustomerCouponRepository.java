package fpoly.electroland.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.CustomerCoupon;

@Repository
public interface CustomerCouponRepository extends JpaRepository<CustomerCoupon, Integer> {

    List<CustomerCoupon> findByCustomerId(int id);

}
