package fpoly.electroland.repository;

import java.util.List;

import fpoly.electroland.model.Employee;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query("SELECT nv FROM Employee nv WHERE str(nv.id) LIKE %:id%")
    List<Employee> findByIdLike(String id);

    Optional<Employee> findByEmail(String email);

    List<Employee> findByChucVu(String cv);

    List<Employee> findByTrangthai(Boolean tt);
}
