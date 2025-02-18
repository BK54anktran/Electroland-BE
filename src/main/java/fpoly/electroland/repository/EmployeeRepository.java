package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.Employee;
import java.util.Optional;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @EntityGraph(attributePaths = "employeeAuthority")
    Optional<Employee> findByEmail(String email);

    Optional<Employee> findById(Long id);

    Boolean existsById(Long id);

    List<Employee> findByFullNameOrEmail(String key, String key2);

    // Tìm kiếm nhân viên theo tên hoặc email hoặc số điện thoại
    List<Employee> findByFullNameContainingOrEmailContainingOrPhoneNumberContaining(String key, String key2,
            String key3);

}
