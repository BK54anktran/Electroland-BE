package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.Employee;

@Repository
public interface EmployeeReponsitory extends JpaRepository<Employee, Integer> {

}
