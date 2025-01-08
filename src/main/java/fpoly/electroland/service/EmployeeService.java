package fpoly.electroland.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.Employee;
import fpoly.electroland.repository.EmployeeRepository;


@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployee(String email) {
        Optional<Employee> employee = employeeRepository.findByEmail(email);
        return employee;
    }

    // Thêm mới nhân viên
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        if (employeeRepository.existsById(id)) { // Kiểm tra xem ID có tồn tại không
            employeeRepository.deleteById(id); // Xóa nhân viên theo ID

        } else {
            throw new RuntimeException("Nhân viên với ID " + id + " không tồn tại!");
        }
    }

    // Sửa nhân viên
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee existingEmployee = optionalEmployee.get();
            existingEmployee.setFullName(updatedEmployee.getFullName());
            existingEmployee.setEmail(updatedEmployee.getEmail());
            existingEmployee.setPhoneNumber(updatedEmployee.getPhoneNumber());
            existingEmployee.setRole(updatedEmployee.getRole());
            existingEmployee.setStatus(updatedEmployee.getStatus());
            return employeeRepository.save(existingEmployee);
        } else {
            throw new RuntimeException("Employee not found with id: " + id);
        }
    }
}
