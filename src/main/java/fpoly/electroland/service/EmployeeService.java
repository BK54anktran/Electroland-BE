package fpoly.electroland.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.Employee;
import fpoly.electroland.repository.EmployeeReponsitory;
import fpoly.electroland.util.JwtUtil;
import fpoly.electroland.util.ResponseEntityUtil;

@Service
public class EmployeeService {

    private final JwtUtil jwtUtil;
    private final EmployeeReponsitory employeeReponsitory;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public EmployeeService(JwtUtil jwtUtil, EmployeeReponsitory employeeReponsitory, @Lazy AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.employeeReponsitory = employeeReponsitory;
        this.authenticationManager = authenticationManager;
    }

    public List<Employee> getAll(){
        return employeeReponsitory.findAll();
   }

   
    public Optional<Employee> getEmployee(String email) {
        Optional<Employee> employee = employeeReponsitory.findByEmail(email);
        return employee;
    }
    // Thêm mới nhân viên
    public Employee createEmployee(Employee employee) {
        return employeeReponsitory.save(employee);
    }

    public void deleteEmployee(Long id) {
        if (employeeReponsitory.existsById(id)) { // Kiểm tra xem ID có tồn tại không
            employeeReponsitory.deleteById(id);  // Xóa nhân viên theo ID
        } else {
            throw new RuntimeException("Nhân viên với ID " + id + " không tồn tại!");
        }
    }
    
    // Sửa nhân viên
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Optional<Employee> optionalEmployee = employeeReponsitory.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee existingEmployee = optionalEmployee.get();
            existingEmployee.setFullName(updatedEmployee.getFullName());
            existingEmployee.setEmail(updatedEmployee.getEmail());
            existingEmployee.setPhoneNumber(updatedEmployee.getPhoneNumber());
            existingEmployee.setRole(updatedEmployee.getRole());
            existingEmployee.setStatus(updatedEmployee.getStatus());
            return employeeReponsitory.save(existingEmployee);
        } else {
            throw new RuntimeException("Employee not found with id: " + id);
        }
    }

    public Object authentication_getData(String email, String password) {
        try {
            // Sử dụng biến cục bộ thay vì thuộc tính toàn cục
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));
            Employee employee = (Employee) authentication.getPrincipal();
            
            Map<String, String> data = new HashMap<>();
            data.put("token", jwtUtil.generateToken(employee.getEmail()));
            data.put("userName", employee.getFullName());
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntityUtil.unauthorizedError("Mật khẩu không chính xác");
        }
    }
}
