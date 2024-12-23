package fpoly.electroland.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.Employee;
import fpoly.electroland.repository.EmployeeReponsitory;

@Service
public class EmployeeService {

    @Autowired
    EmployeeReponsitory employeeReponsitory;

    public Optional<Employee> getEmployee(String email) {
        Optional<Employee> employee = employeeReponsitory.findByEmail(email);
        return employee;
    }

}
