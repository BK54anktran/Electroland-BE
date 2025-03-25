package fpoly.electroland.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import fpoly.electroland.model.Customer;
import fpoly.electroland.model.User;
import fpoly.electroland.restController.AuthController;

@ExtendWith(MockitoExtension.class) // Thêm annotation này để tích hợp Mockito với JUnit 5
public class AuthControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private CustomerService customerService;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private AuthController authController;

    // Dữ liệu mẫu cho User và Customer
    private User existingUser = new User("existinguser@example.com", "password123");
    private User nonExistingUser = new User("nonexistinguser@example.com", "password456");

    private Customer existingCustomer = new Customer("existingcustomer@example.com", "password123");
    private Customer newCustomer = new Customer("newcustomer@example.com", "password456");
    @Test
    void testAuthenticateAdmin_UserNotFound() {
        // Mô phỏng người dùng không tồn tại trong hệ thống
        when(employeeService.getEmployee(existingUser.getEmail())).thenReturn(java.util.Optional.empty());
    
        ResponseEntity<Object> response = (ResponseEntity<Object>) authController.authenticateAdmin(existingUser);
    
        assertEquals(401, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Tài khoản không tồn tại"));
        
        // Thông báo sau khi kiểm tra thành công
        System.out.println("testAuthenticateAdmin_UserNotFound passed!");
    }
    
    @Test
    void testAuthenticate_UserNotFound() {
        // Mô phỏng người dùng không tồn tại trong hệ thống
        when(customerService.getCustomer(nonExistingUser.getEmail())).thenReturn(java.util.Optional.empty());
    
        ResponseEntity<Object> response = (ResponseEntity<Object>) authController.authenticate(nonExistingUser);
    
        assertEquals(401, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Tài khoản không tồn tại"));
        
        // Thông báo sau khi kiểm tra thành công
        System.out.println("testAuthenticate_UserNotFound passed!");
    }
    
    @Test
    void testRegister_UserAlreadyExists() {
        // Mô phỏng khách hàng đã tồn tại
        when(customerService.getCustomer(existingCustomer.getEmail())).thenReturn(java.util.Optional.of(existingCustomer));
    
        ResponseEntity<Object> response = (ResponseEntity<Object>) authController.register(existingCustomer);
    
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Tài khoản đã tồn tại"));
        
        // Thông báo sau khi kiểm tra thành công
        System.out.println("testRegister_UserAlreadyExists passed!");
    }
    
    @Test
    void testRegister_Success() {
        Customer customer = new Customer("newcustomer@example.com", "password456");
    
        // Mô phỏng khách hàng mới chưa tồn tại
        when(customerService.getCustomer(customer.getEmail())).thenReturn(java.util.Optional.empty());
        
        // Trả về ResponseEntity thay vì String
        when(userService.authentication_getData(customer.getEmail(), customer.getPassword()))
            .thenReturn(new ResponseEntity<>("someData", HttpStatus.OK));  // Trả về ResponseEntity thực sự
    
        // Gọi phương thức controller
        ResponseEntity<Object> response = (ResponseEntity<Object>) authController.register(customer);
    
        // Kiểm tra kết quả trả về
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("someData", response.getBody());
        
        // Thông báo sau khi kiểm tra thành công
        System.out.println("testRegister_Success passed!");
    }
    

}
