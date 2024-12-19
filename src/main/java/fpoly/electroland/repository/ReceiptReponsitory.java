package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.Receipt;

@Repository
public interface ReceiptReponsitory extends JpaRepository<Receipt, Integer> {

}
