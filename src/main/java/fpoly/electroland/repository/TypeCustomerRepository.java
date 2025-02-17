package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.TypeCustomer;

@Repository
public interface TypeCustomerRepository extends JpaRepository<TypeCustomer, Integer> {

}
