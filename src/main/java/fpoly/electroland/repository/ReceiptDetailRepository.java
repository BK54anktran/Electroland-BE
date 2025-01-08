package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.ReceiptDetail;

@Repository
public interface ReceiptDetailRepository extends JpaRepository<ReceiptDetail, Integer> {

}
