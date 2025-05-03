package fpoly.electroland.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.ReceiptDetail;

@Repository
public interface ReceiptDetailRepository extends JpaRepository<ReceiptDetail, Integer> {

        Optional findById(Long receiptId);

        List<ReceiptDetail> findByReceiptId(Long receiptId);

        @Query("SELECT p.name, SUM(rd.quantity) " +
                        "FROM ReceiptDetail rd " +
                        "JOIN rd.receipt r " +
                        "JOIN rd.product p " +
                        "WHERE r.receiptStatus.id = 3 AND r.receiptDate BETWEEN :startDate AND :endDate " +
                        "GROUP BY p.name " +
                        "ORDER BY SUM(rd.quantity) DESC " +
                        "LIMIT 10")

        List<Object[]> findSalesDataByDateRange(@Param("startDate") LocalDateTime startDate,
                        @Param("endDate") LocalDateTime endDate);

        // Truy vấn doanh thu từ bảng Payment, từ ngày đầu tháng đến ngày truyền vào
        @Query("SELECT SUM(p.amount) FROM Receipt r " +
                        "JOIN r.payment p " +
                        "WHERE r.receiptStatus.id = 3 AND r.receiptDate >= :startDate AND r.receiptDate <= :endDate")
        Long getRevenueByDateRange(@Param("startDate") LocalDateTime startDate,
                        @Param("endDate") LocalDateTime endDate);

        // Truy vấn số lượng đơn hàng từ bảng Receipt, từ ngày đầu tháng đến ngày truyền
        // vào
        @Query("SELECT COUNT(r) FROM Receipt r " +
                        "WHERE r.receiptDate >= :startDate AND r.receiptDate <= :endDate")
        Long getTotalOrders(@Param("startDate") LocalDateTime startOfMonth, @Param("endDate") LocalDateTime endDate);

        // Truy vấn số lượng đơn thành công từ đầu tháng đến ngày truyền vào
        @Query("SELECT COUNT(r) FROM Receipt r " +
                        "WHERE r.receiptStatus.id = 3 AND r.receiptDate >= :startDate AND r.receiptDate <= :endDate")
        Long getSuccessfulOrders(@Param("startDate") LocalDateTime startOfMonth,
                        @Param("endDate") LocalDateTime endDate);

        // Truy vấn số lượng đơn hàng thất bại từ đầu tháng đến ngày truyền vào
        @Query("SELECT COUNT(r) FROM Receipt r " +
                        "WHERE (r.receiptStatus.id = 4) " +
                        "AND r.receiptDate >= :startDate AND r.receiptDate <= :endDate")
        Long getFailedOrders(@Param("startDate") LocalDateTime startOfMonth, @Param("endDate") LocalDateTime endDate);

        // Truy vấn số lượng đơn hàng đang xử lí từ đầu tháng đến ngày truyền vào
        @Query("SELECT COUNT(r) FROM Receipt r " +
                        "WHERE (r.receiptStatus.id = 1 OR r.receiptStatus.id = 2) " +
                        "AND r.receiptDate >= :startDate AND r.receiptDate <= :endDate")
        Long getProcessingOrders(@Param("startDate") LocalDateTime startOfMonth, @Param("endDate") LocalDateTime endDate);

        // Truy vấn số lượng khách hàng từ đầu tháng đến ngày truyền vào
        @Query("SELECT COUNT(DISTINCT r.customer) FROM Receipt r " +
                        "WHERE r.receiptDate >= :startDate AND r.receiptDate <= :endDate")
        Long getCustomerCount(@Param("startDate") LocalDateTime startOfMonth, @Param("endDate") LocalDateTime endDate);

        // Truy vấn doanh thu theo tháng từ đầu năm đến ngày truyền vào
        @Query(value = "SELECT MONTH(r.receiptDate) AS month, COALESCE(SUM(p.amount), 0) AS value " +
                        "FROM receipt r " +
                        "JOIN receiptStatus rs ON r.idReceiptStatus = rs.id " +
                        "JOIN payment p ON r.idPayment = p.id " +
                        "WHERE rs.id = 3 AND YEAR(r.receiptDate) = :year " +
                        "GROUP BY MONTH(r.receiptDate) " +
                        "ORDER BY month", nativeQuery = true)
        List<Object[]> getRevenueByMonth(@Param("year") int year);

        // truy vấn tỉ lệ phương thức thanh toán theo tháng từ đầu tháng đến ngày truyền
        // vào
        @Query("SELECT pt.name AS paymentMethod, COUNT(r.id) AS totalOrders " +
                        "FROM Receipt r " +
                        "JOIN r.payment p " +
                        "JOIN p.paymentType pt " +
                        "WHERE r.receiptDate BETWEEN :startDate AND :endDate " +
                        "GROUP BY pt.name " +
                        "ORDER BY totalOrders DESC")
        List<Object[]> getPaymentMethodStats(@Param("startDate") LocalDateTime startDate,
                        @Param("endDate") LocalDateTime endDate);

        boolean existsByProduct_IdAndReceipt_Customer_IdAndReceipt_ReceiptStatus_Id(Integer productId,
                        Integer customerId, Integer statusId);

}
