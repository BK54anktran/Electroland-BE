package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.ReceiptStatus;

@Repository
public interface ReceiptStatusReponsitory extends JpaRepository<ReceiptStatus, Integer> {

}
