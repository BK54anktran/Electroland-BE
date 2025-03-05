package fpoly.electroland.restController;

import fpoly.electroland.dto.request.AdminUserDTO;
import fpoly.electroland.service.UserService;
import fpoly.electroland.service.EmployeeService;
import fpoly.electroland.model.User;
import fpoly.electroland.model.Employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/admin/info")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/changePassword")
    public String changePassword(@RequestBody Map<String, Object> object) {
        String email = (String) object.get("email");
        String oldPassword = (String) object.get("oldPassword");
        String newPassword = (String) object.get("newPassword");

        Optional<Employee> employeeOpt = employeeService.getEmployee(email);
        if (!employeeOpt.isPresent()) {
            throw new RuntimeException("Employee không tồn tại");
        }

        Employee employee = employeeOpt.get();
        String dbPassword = employee.getPassword();

        if (!dbPassword.startsWith("$2a$")) { 
            if (!dbPassword.equals(oldPassword)) {
                throw new RuntimeException("Mật khẩu cũ không đúng");
            }
        } else {
            if (!passwordEncoder.matches(oldPassword, dbPassword)) {
                throw new RuntimeException("Mật khẩu cũ không đúng");
            }
        }

        employee.setPassword(passwordEncoder.encode(newPassword));
        employeeService.save(employee);

        return "Đổi mật khẩu thành công";
    }


    @GetMapping("/getUserInfo")
    public AdminUserDTO getUserInfo() {
        User user = userService.getUser();
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Optional<Employee> employee = employeeService.getEmployee(user.getEmail());
        if (!employee.isPresent()) {
            throw new RuntimeException("Employee not found");
        }

        return new AdminUserDTO(
            user.getName(),
            user.getEmail(),
            employee.get().getPhoneNumber(),
            employee.get().getRole(),
            employee.get().getStatus()
        );
    }

    @PostMapping("/save")
    public void updateUser(@RequestBody Map<String, Object> object) {
        String fullName = (String) object.getOrDefault("fullName", null);
        String phoneNumber = (String) object.getOrDefault("phoneNumber", null);
        String email = (String) object.getOrDefault("email", null);
        String role = (String) object.getOrDefault("role", null);
        User user = userService.getUser();
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Optional<Employee> employeeOpt = employeeService.getEmployee(user.getEmail());
        if (!employeeOpt.isPresent()) {
            throw new RuntimeException("Employee not found");
        }

        Employee employee = employeeOpt.get();

        if (fullName != null) {
            employee.setFullName(fullName);
        }
        if (phoneNumber != null) {
            employee.setPhoneNumber(phoneNumber);
        }
        if (email != null) {
            employee.setEmail(email);
        }
        if (role != null) {
            employee.setRole(role);
        }

        employeeService.save(employee);
    }
}
