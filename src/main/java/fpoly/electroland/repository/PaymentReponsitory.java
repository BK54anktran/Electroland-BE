package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.Payment;

@Repository
public interface PaymentReponsitory extends JpaRepository<Payment, Integer> {

}
