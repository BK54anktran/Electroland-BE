package fpoly.electroland.restController;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.model.Customer;
import fpoly.electroland.service.CustomerService;
import fpoly.electroland.service.UserService;
import fpoly.electroland.util.DateUtil;
import fpoly.electroland.util.ResponseEntityUtil;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/admin/customer")
    public List<Customer> GetAllList() {
        return customerService.getAll();
    }

    @GetMapping("/customer/search")
    public List<Customer> searchCustomers(@RequestParam(required = false) String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return customerService.getAll(); // Nếu không có từ khóa, trả về danh sách đầy đủ
        }
        return customerService.searchCustomers(keyword);
    }

    @GetMapping("/customer/filter")
    public List<Customer> filterCustomers(@RequestParam String status) {
        boolean statusBoolean = Boolean.parseBoolean(status); // Chuyển đổi sang Boolean
        return customerService.filterCustomersByStatus(statusBoolean);
    }

    @PostMapping("/customer/save")
    public Customer addCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @PutMapping("/customer/update/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Integer id, @RequestParam String status) {
        boolean statusBoolean = Boolean.parseBoolean(status); // Chuyển chuỗi thành boolean

        Customer updatedCustomer = customerService.updateCustomer(id, statusBoolean);

        if (updatedCustomer != null) {
            return ResponseEntity.ok(updatedCustomer);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Kết hợp tìm kiếm và lọc
    @GetMapping("/customer/search-filter")
    public List<Customer> searchAndFilterCustomers(@RequestParam String keyword, @RequestParam boolean status) {
        return customerService.searchAndFilterCustomers(keyword, status);
    }

    @GetMapping("/userinfor")
    public Customer getUser() {
        // System.out.println(userService.getUser());
        Customer customer = customerService.getCustomer(userService.getUser().getId()).get();
        return customer;
    }

    @PostMapping("/userAvatar")
    public void saveUserAva(@RequestBody String avaUrl) {
        avaUrl = avaUrl.replace("\"", "");
        int id = userService.getUser().getId();
        Customer customer = customerService.getCustomer(userService.getUser().getId()).get();
        customer.setAvatar(avaUrl);
        customerService.updateCustomer(id, customer);
        // System.out.println(avaUrl);
    }

    @PostMapping("/userUpdate")
    public void updateUser(@RequestBody Map<String, Object> object) {
        String dateOfBirthStr = (String) object.get("dateOfBirth"); // Lấy ngày dưới dạng chuỗi
        Date dateOfBirth = (Date) DateUtil.formatDate(dateOfBirthStr);
        String fullName = (String) object.get("fullName");
        Boolean gender = (Boolean) object.get("gender");
        String phoneNumber = (String) object.get("phoneNumber");

        int id = userService.getUser().getId();
        Customer customer = customerService.getCustomer(userService.getUser().getId()).get();
        customer.setDateOfBirth(dateOfBirth);
        customer.setGender(gender);
        customer.setPhoneNumber(phoneNumber);
        customer.setFullName(fullName);
        customerService.updateCustomer(id, customer);
    }

    @PostMapping("/createUser")
    public void createUser(@RequestBody Map<String, Object> object) {
        String dateOfBirthStr = (String) object.get("dateOfBirth");
        Date dob = (Date) DateUtil.formatDate(dateOfBirthStr);
        String fullName = (String) object.get("fullName");
        Boolean gender = (Boolean) object.get("gender");
        String phoneNumber = (String) object.get("phoneNumber");
        String email = (String) object.get("email");
        String password = (String) object.get("password");

        Customer customer = new Customer();
        customer.setDateOfBirth(dob);
        customer.setGender(gender);
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setFullName(fullName);
        customer.setPhoneNumber(phoneNumber);
        customerService.createCustomer(customer);
    }
    @PostMapping("/customerPassword")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> request) {
        String oldPassword = request.get("oldPassword");
        String newPassword = request.get("newPassword");
        
        Customer customer = customerService.getCustomer(userService.getUser().getId()).get();
        if (passwordEncoder.matches(oldPassword, customer.getPassword())) {
            customer.setPassword(passwordEncoder.encode(newPassword));
            Customer updatedCustomer= customerService.updateCustomer(customer.getId(), customer);
            return ResponseEntity.ok(updatedCustomer);
        } 
        else {
            return ResponseEntityUtil.badRequest("Mật khẩu hiện tại không chinh xác");
        }
    }
}
