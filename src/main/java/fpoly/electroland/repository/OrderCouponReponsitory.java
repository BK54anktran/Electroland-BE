package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.OrderCoupon;

@Repository
public interface OrderCouponReponsitory extends JpaRepository<OrderCoupon, Integer> {

}
