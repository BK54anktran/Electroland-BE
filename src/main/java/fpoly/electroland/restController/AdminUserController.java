package fpoly.electroland.restController;

import fpoly.electroland.dto.request.AdminUserDTO;
import fpoly.electroland.service.UserService;
import fpoly.electroland.service.EmployeeService;
import fpoly.electroland.model.User;
import fpoly.electroland.model.Employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/admin/info")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

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
    public AdminUserDTO saveUserInfo(@RequestBody AdminUserDTO userDTO) {
        User user = userService.getUser();
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Optional<Employee> employeeOpt = employeeService.getEmployee(user.getEmail());
        if (!employeeOpt.isPresent()) {
            throw new RuntimeException("Employee not found");
        }

        Employee employee = employeeOpt.get();
        employee.setPhoneNumber(userDTO.getPhoneNumber());
        employee.setRole(userDTO.getRole());
        employee.setStatus(userDTO.getStatus());

        employeeService.save(employee);

        return new AdminUserDTO(
            user.getName(),
            user.getEmail(),
            employee.getPhoneNumber(),
            employee.getRole(),
            employee.getStatus()
        );
    }
}
