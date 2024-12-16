package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.PaymentType;

@Repository
public interface PaymentTypeReponsitory extends JpaRepository<PaymentType, Integer> {

}
