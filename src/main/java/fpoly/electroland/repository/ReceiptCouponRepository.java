package fpoly.electroland.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import fpoly.electroland.model.ReceiptCoupon;

@Repository
public interface ReceiptCouponRepository extends JpaRepository<ReceiptCoupon, Integer> {
    
    Optional<ReceiptCoupon> findById(Long id);

    @Query("SELECT r FROM ReceiptCoupon r WHERE CAST(r.discountMoney AS string) LIKE %:discountMoney%")
    List<ReceiptCoupon> findByDiscountMoney(@Param("discountMoney") String discountMoney);
    
    @Query("SELECT r FROM ReceiptCoupon r WHERE CAST(r.discountPercent AS string) LIKE %:discountPercent%")
    List<ReceiptCoupon> findByDiscountPercent(@Param("discountPercent") String discountPercent);

    @Query("SELECT r FROM ReceiptCoupon r WHERE CAST(r.discountPercent AS string) LIKE %:discountPercent% OR CAST(r.discountMoney AS string) LIKE %:discountMoney%")
    List<ReceiptCoupon> findByDiscountPercentAndDiscountMoney(@Param("discountPercent") String discountPercent, @Param("discountMoney") String discountMoney);
}
