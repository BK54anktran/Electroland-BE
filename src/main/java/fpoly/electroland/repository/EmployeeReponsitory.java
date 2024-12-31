package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.Employee;
import java.util.Optional;
import java.util.List;


@Repository
public interface EmployeeReponsitory extends JpaRepository<Employee, Integer> {

    Optional<Employee> findByEmail(String email);
    Optional<Employee> findById(Long id);
    void deleteById(Long id) ;
    Boolean existsById(Long id);
}
