package fpoly.electroland.restController;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.dto.request.AdminUserDTO;
import fpoly.electroland.model.Employee;
import fpoly.electroland.model.User;
import fpoly.electroland.service.EmployeeService;
import fpoly.electroland.service.UserService;

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
        System.out.println("USER: " + user);
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
