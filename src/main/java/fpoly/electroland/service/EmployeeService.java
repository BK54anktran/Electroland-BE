package fpoly.electroland.service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.Action;
import fpoly.electroland.model.Employee;
import fpoly.electroland.repository.ActionRepository;
import fpoly.electroland.repository.EmployeeRepository;
import fpoly.electroland.util.CreateAction;


@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ActionRepository actionRepository;

    @Autowired
    CreateAction createAction; // Sử dụng dependency injection
    @Autowired
    EmployeePasswordService employeePasswordService;
    // Lấy tất cả nhân viên
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    // Lấy nhân viên theo email
    public Optional<Employee> getEmployee(String email) {
        return employeeRepository.findByEmail(email);
    }

     // Thêm nhân viên mới
     public Employee createEmployee(Employee employee, int userId) throws Exception {
         // Tạo mật khẩu ngẫu nhiên
        String randomPassword = generateRandomPassword(8);  // Mật khẩu dài 8 ký tự

        // Mã hóa mật khẩu
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(randomPassword);

        // Cập nhật mật khẩu đã mã hóa vào đối tượng employee
        employee.setPassword(encodedPassword);

        // Lưu nhân viên mới vào cơ sở dữ liệu
        Employee savedEmployee = employeeRepository.save(employee);

        // Lấy nhân viên thực hiện hành động
        Employee creatorEmployee = employeeRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Ghi hành động vào bảng Action
        createAction.createAction("Employee", "CREATE", savedEmployee.getId(), null,
                savedEmployee.toString(), creatorEmployee);

        // Gửi email thông báo mật khẩu cho nhân viên mới
        employeePasswordService.sendEmployeePasswordEmail(savedEmployee.getEmail(), savedEmployee.getFullName(), randomPassword);

        return savedEmployee;
    }

    // Tạo mật khẩu ngẫu nhiên
    private String generateRandomPassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";
        SecureRandom random = new SecureRandom();

        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }
        return password.toString();
    }

    // Sửa nhân viên
    public Employee updateEmployee(Long id, Employee updatedEmployee, int userId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee existingEmployee = optionalEmployee.get();

            // Lưu giá trị cũ để ghi vào Action
            String oldValue = existingEmployee.toString();

            // Cập nhật thông tin nhân viên
            existingEmployee.setFullName(updatedEmployee.getFullName());
            existingEmployee.setEmail(updatedEmployee.getEmail());
            existingEmployee.setPhoneNumber(updatedEmployee.getPhoneNumber());
            existingEmployee.setRole(updatedEmployee.getRole());
            existingEmployee.setStatus(updatedEmployee.getStatus());

            Employee savedEmployee = employeeRepository.save(existingEmployee);

            // Lấy nhân viên thực hiện hành động
            Employee creatorEmployee = employeeRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Ghi hành động vào bảng Action
            createAction.createAction("Employee", "UPDATE", savedEmployee.getId(), oldValue,
                    savedEmployee.toString(), creatorEmployee);

            return savedEmployee;
        } else {
            throw new RuntimeException("Employee not found with id: " + id);
        }
    }
    public List<Employee> searchEmployees(String key) {
        // Tìm kiếm nhân viên có fullName hoặc email chứa key
        return employeeRepository.findByFullNameContainingOrEmailContainingOrPhoneNumberContaining(key, key,key);
    }

    public Employee save(Employee employee){
        return employeeRepository.save(employee);
    }

    public boolean existsByEmail(String email) {
        return employeeRepository.findByEmail(email).isPresent();
    }

}
