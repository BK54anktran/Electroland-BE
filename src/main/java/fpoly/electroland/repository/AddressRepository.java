package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

}
