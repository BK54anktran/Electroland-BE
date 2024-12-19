package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.ReceiptCoupon;

@Repository
public interface ReceiptCouponReponsitory extends JpaRepository<ReceiptCoupon, Integer> {

}
