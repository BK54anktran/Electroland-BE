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
}
