package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.Supplier;

@Repository
public interface SupplierReponsitory extends JpaRepository<Supplier, Integer> {

}
