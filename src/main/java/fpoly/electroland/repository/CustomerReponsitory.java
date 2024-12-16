package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.Customer;

@Repository
public interface CustomerReponsitory extends JpaRepository<Customer, Integer> {

}