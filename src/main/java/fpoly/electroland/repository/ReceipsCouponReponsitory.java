package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.ReceipsCoupon;

@Repository
public interface ReceipsCouponReponsitory extends JpaRepository<ReceipsCoupon, Integer> {

}
