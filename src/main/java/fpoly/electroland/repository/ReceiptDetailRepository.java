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
           "WHERE r.receiptDate BETWEEN :startDate AND :endDate " +
           "GROUP BY p.name")
    List<Object[]> findSalesDataByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);


}

