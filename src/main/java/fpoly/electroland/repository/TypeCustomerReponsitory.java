package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.TypeCustomer;

@Repository
public interface TypeCustomerReponsitory extends JpaRepository<TypeCustomer, Integer> {

}
