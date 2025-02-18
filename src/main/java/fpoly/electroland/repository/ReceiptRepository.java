package fpoly.electroland.repository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.Receipt;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Integer> {

    Optional<Receipt> findById(Long receiptId);

    @Query("SELECT MIN(r.receiptDate) FROM Receipt r")
    Optional<LocalDateTime> findEarliestDate();

    @Query("SELECT r FROM Receipt r WHERE " +
            "(:startDate IS NULL OR r.receiptDate >= :startDate) " +
            "AND (:endDate IS NULL OR r.receiptDate <= :endDate)")
    List<Receipt> findReceiptsByDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT r FROM Receipt r WHERE " +
            "CAST(r.id AS string) LIKE %:searchKey% OR " +
            "LOWER(r.address) LIKE LOWER(CONCAT('%', :searchKey, '%')) OR " +
            "LOWER(r.nameReciver) LIKE LOWER(CONCAT('%', :searchKey, '%')) OR " +
            "r.phoneReciver LIKE %:searchKey%")
    List<Receipt> searchReceipts(@Param("searchKey") String searchKey);

    // 🔹 1. Đếm tổng số đơn hàng
    @Query("SELECT COUNT(r) FROM Receipt r")
    long countTotalOrders();

    // 🔹 2. Đếm số đơn hàng theo trạng thái
    @Query("SELECT r.receiptStatus.name, COUNT(r) FROM Receipt r GROUP BY r.receiptStatus.name")
    List<Object[]> countOrdersByStatus();

    // 🔹 3. Tổng doanh thu từ đơn hàng
    @Query("SELECT COALESCE(SUM(rd.price * rd.quantity), 0) FROM ReceiptDetail rd")
    Double totalRevenue();    

    // 🔹 4. Doanh thu theo tháng
    @Query("SELECT MONTH(r.receiptDate), SUM(rd.price * rd.quantity) FROM ReceiptDetail rd JOIN rd.receipt r GROUP BY MONTH(r.receiptDate)")
    List<Object[]> revenueByMonth();

    // 🔹 5. Đếm số đơn hàng theo phương thức thanh toán
    @Query("SELECT pt.name, COUNT(r) FROM Receipt r JOIN r.payment p JOIN p.paymentType pt GROUP BY pt.name")
    List<Object[]> countOrdersByPaymentMethod();

    // 🔹 7. Tính tỷ lệ đơn hoàn trả
    @Query("SELECT (COUNT(r) * 1.0 / (SELECT COUNT(r2) FROM Receipt r2)) FROM Receipt r WHERE r.receiptStatus.name = 'Hoàn hàng'")
    Double refundRate();

}
