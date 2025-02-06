package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.ReceiptStatus;

@Repository
public interface ReceiptStatusRepository extends JpaRepository<ReceiptStatus, Integer> {

}
