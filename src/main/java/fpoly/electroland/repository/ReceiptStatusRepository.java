package fpoly.electroland.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.Receipt;
import fpoly.electroland.model.ReceiptStatus;

@Repository
public interface ReceiptStatusRepository extends JpaRepository<ReceiptStatus, Integer> {
   Optional<ReceiptStatus> findById(Long receiptId);

Optional<ReceiptStatus> findByName(String newStatusName);
}
