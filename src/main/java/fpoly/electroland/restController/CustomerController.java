package fpoly.electroland.restController;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.model.Customer;
import fpoly.electroland.model.Product;
import fpoly.electroland.service.CustomerService;
import fpoly.electroland.service.UserService;
import fpoly.electroland.util.DateUtil;

@RestController
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @Autowired
    UserService userService;

    @GetMapping("/admin/customer")
    public List<Customer> GetAllList() {
        return customerService.getAll();
    }
    
    @PostMapping("/customer/save")
    public Customer addCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @PutMapping("/customer/update/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Integer id, @RequestParam boolean status) {

        Customer updatedCustomer = customerService.updateCustomer(id, status);

        if (updatedCustomer != null) {
            return ResponseEntity.ok(updatedCustomer);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    // Tìm kiếm khách hàng
    @GetMapping("/customer/search")
    public List<Customer> searchCustomers(@RequestParam String keyword) {
        return customerService.searchCustomers(keyword);
    }

    // Lọc khách hàng theo trạng thái
    @GetMapping("/customer/filter")
    public List<Customer> filterCustomers(@RequestParam boolean status) {
        return customerService.filterCustomersByStatus(status);
    }

    // Kết hợp tìm kiếm và lọc
    @GetMapping("/customer/search-filter")
    public List<Customer> searchAndFilterCustomers(@RequestParam String keyword, @RequestParam boolean status) {
        return customerService.searchAndFilterCustomers(keyword, status);
    }

    @GetMapping("/userinfor")
    public Customer getUser() {
        System.out.println(userService.getUser());
        Customer customer = customerService.getCustomer(userService.getUser().getId()).get();
        return customer;
    }

    @PostMapping("/userAvatar")
    public void saveUserAva(@RequestBody String avaUrl) {
        avaUrl = avaUrl.replace("\"", "");
        System.out.println(userService.getUser());
        int id = userService.getUser().getId();
        Customer customer = customerService.getCustomer(userService.getUser().getId()).get();
        customer.setAvatar(avaUrl);
        customerService.updateCustomer(id, customer);
        System.out.println(avaUrl);
    }

    @PostMapping("/userUpdate")
    public void updateUser(@RequestBody Map<String, Object> object) {
        String dateOfBirthStr = (String) object.get("dateOfBirth");  // Lấy ngày dưới dạng chuỗi
        Date dateOfBirth = (Date) DateUtil.formatDate(dateOfBirthStr);
        String fullName =(String)object.get("fullName");
        Boolean gender = (Boolean)object.get("gender");
        String phoneNumber = (String)object.get("phoneNumber");
        
        int id = userService.getUser().getId();
        Customer customer = customerService.getCustomer(userService.getUser().getId()).get();
        customer.setDateOfBirth(dateOfBirth);
        customer.setGender(gender);
        customer.setPhoneNumber(phoneNumber);
        customer.setFullName(fullName);
        if(object.get("newPassword")!=null){
            String newPassword = (String)object.get("newPassword");
            customer.setPassword(newPassword);
        }
        System.out.println(customer);
        customerService.updateCustomer(id, customer);
    }
}
