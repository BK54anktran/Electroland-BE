package fpoly.electroland.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.Address;
import fpoly.electroland.model.Customer;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    List<Address> getAddressesByCustomer(Customer customer);

    List<Address> findByCustomerId(int customerId);
}
