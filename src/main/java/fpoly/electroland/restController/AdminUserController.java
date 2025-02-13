package fpoly.electroland.restController;

import java.lang.classfile.ClassFile.Option;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import fpoly.electroland.model.Employee;
import fpoly.electroland.service.EmployeeService;

@Controller
@RequestMapping("/admin")
public class AdminUserController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/info/{email}")
    public ResponseEntity<Employee> getUserByEmail(@PathVariable String email) {
        Optional<Employee> employee = employeeService.getEmployeeByEmail(email);

        if (employee.isPresent()) {
            return ResponseEntity.ok(employee.get());
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/info/update/{email}")
    public ResponseEntity<Employee> updateUser(@PathVariable String email, @RequestBody Employee updatedEmployee) {
        Optional<Employee> existingEmployee = employeeService.getEmployeeByEmail(email);

        if (existingEmployee.isPresent()) {
            Employee employee = existingEmployee.get();
            employee.setFullName(updatedEmployee.getFullName());
            employee.setEmail(updatedEmployee.getEmail());
            employee.setPhoneNumber(updatedEmployee.getPhoneNumber());
            employeeService.save(employee); 
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/info/changePassword/{email}")
    public ResponseEntity<String> changePassword(@PathVariable String email, @RequestBody Map<String, String> request) {
        Optional<Employee> existingEmployee = employeeService.getEmployeeByEmail(email);

        if (existingEmployee.isPresent()) {
            Employee emp = existingEmployee.get();
            
            String oldPassword = request.get("oldPassword");
            String newPassword = request.get("newPassword");

            if (!emp.getPassword().equals(oldPassword)) {
                return ResponseEntity.badRequest().body("Mật khẩu cũ không đúng!");
            }

            emp.setPassword(newPassword);
            employeeService.save(emp);
            return ResponseEntity.ok("Đổi mật khẩu thành công!");
        }

        return ResponseEntity.status(404).body("Không tìm thấy tài khoản!");
    }
}
