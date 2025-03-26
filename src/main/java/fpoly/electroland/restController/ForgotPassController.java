package fpoly.electroland.restController;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.dto.response.ResetPasswordRequest;
import fpoly.electroland.model.Customer;
import fpoly.electroland.model.Employee;
import fpoly.electroland.service.CustomerService;
import fpoly.electroland.service.EmployeeService;
import fpoly.electroland.service.MailerService;
import fpoly.electroland.util.JwtUtil;
import fpoly.electroland.util.ResponseEntityUtil;
import jakarta.mail.MessagingException;

@RestController
public class ForgotPassController {
    @Autowired
    CustomerService customerService;
    
    @Autowired
    EmployeeService employeeService;

    @Autowired
    MailerService mailerService;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    JwtUtil jwtUtil = new JwtUtil();

    @PostMapping("/forgotpass") //
    public Object forgot(@RequestBody Map<String, String> request) throws MessagingException {
        String email = request.get("email"); //
        if (!customerService.getCustomer(email).isPresent()){
            return ResponseEntityUtil.unauthorizedError("Tài khoản không tồn tại");
        }
        return ResponseEntityUtil.ok(mailerService.sendOtpEmail(email));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");
        String storedOtp = redisTemplate.opsForValue().get("otp:" + email);

        if (storedOtp == null || !storedOtp.equals(otp)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("OTP không hợp lệ hoặc đã hết hạn.");
        }
        return ResponseEntity.ok(jwtUtil.generateTokenResetPassword(email));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        String token = request.getToken();
        String newPassword = request.getNewPassword();

        String email = jwtUtil.extractEmailTokenResetPasseowd(token);
        Customer customer = customerService.getCustomer(email)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));
        // emp.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        customer.setPassword(newPassword);
        customerService.updateCustomer(customer);

        return ResponseEntity.ok("Mật khẩu đã được cập nhật thành công.");
    }

    @PostMapping("/updatePassword")
    public Object updatePassword(@RequestParam String email, @RequestParam String password) throws MessagingException {
        if (!customerService.getCustomer(email).isPresent())
            return ResponseEntityUtil.unauthorizedError("Tài khoản không tồn tại");
        return ResponseEntityUtil.ok(mailerService.sendOtpEmail(email));
    }

    @PostMapping("/admin/forgotpass")
    public Object forgotADM(@RequestBody Map<String, String> request) throws MessagingException {
        String email = request.get("email");
        if (!employeeService.getEmployee(email).isPresent()){
            return ResponseEntityUtil.unauthorizedError("Tài khoản không tồn tại");
        }
        return ResponseEntityUtil.ok(mailerService.sendOtpEmail(email));
    }
    @PostMapping("/admin/verify-otp")
    public ResponseEntity<String> verifyOtpADM(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");
        String storedOtp = redisTemplate.opsForValue().get("otp:" + email);

        if (storedOtp == null || !storedOtp.equals(otp)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("OTP không hợp lệ hoặc đã hết hạn.");
        }
        return ResponseEntity.ok(jwtUtil.generateTokenResetPassword(email));
    }

    @PostMapping("/admin/reset-password")
    public ResponseEntity<String> resetPasswordADM(@RequestBody ResetPasswordRequest request) {
        String token = request.getToken();
        String newPassword = request.getNewPassword();

        String email = jwtUtil.extractEmailTokenResetPasseowd(token);
        Employee employee = employeeService.getEmployee(email)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));
        // emp.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        employee.setPassword(newPassword);
        employeeService.updateEmployee((long)employee.getId(), employee, employee.getId());

        return ResponseEntity.ok("Mật khẩu đã được cập nhật thành công.");
    }
    
    @PostMapping("admin/updatePassword")
    public Object updatePasswordADM(@RequestParam String email, @RequestParam String password) throws MessagingException {
        if (!employeeService.getEmployee(email).isPresent())
            return ResponseEntityUtil.unauthorizedError("Tài khoản không tồn tại");
        return ResponseEntityUtil.ok(mailerService.sendOtpEmail(email));
    }


}
