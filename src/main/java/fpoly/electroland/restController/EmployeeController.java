package fpoly.electroland.restController;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.model.Employee;
import fpoly.electroland.service.EmployeeService;
import fpoly.electroland.service.UserService;

@RestController
@RequestMapping("/admin")
public class EmployeeController {
     @Autowired
    UserService userService;
    @Autowired
    EmployeeService employeeService;

    @GetMapping("/employees")
    public List<Employee> GetAllList() {
        return employeeService.getAll();
    }

    // API: Thêm mới nhân viên
    @PostMapping("/employees/save")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Integer userId = userService.getUser().getId();
        Employee createdEmployee = employeeService.createEmployee(employee, userId);
        return ResponseEntity.ok(createdEmployee); // Trả về đối tượng được tạo
    }

    @PutMapping("/employees/update/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        try {
            Employee updatedEmployee = employeeService.updateEmployee(id, employee);
            return ResponseEntity.ok(updatedEmployee);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy nhân viên với ID: " + id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi cập nhật nhân viên: " + e.getMessage());
        }
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.ok("Đã xóa thành công nhân viên với ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi xóa nhân viên: " + e.getMessage());
        }
    }

}
