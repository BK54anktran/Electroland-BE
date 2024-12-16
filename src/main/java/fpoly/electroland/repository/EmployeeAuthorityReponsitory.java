package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.EmployeeAuthority;

@Repository
public interface EmployeeAuthorityReponsitory extends JpaRepository<EmployeeAuthority, Integer> {

}
