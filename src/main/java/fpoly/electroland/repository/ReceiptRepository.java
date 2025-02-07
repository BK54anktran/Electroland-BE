package fpoly.electroland.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.Receipt;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Integer> {

    Optional<Receipt> findById(Long receiptId);

}
