package fpoly.electroland.restController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.checkerframework.checker.units.qual.m;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import fpoly.electroland.model.Customer;
import fpoly.electroland.model.Mail;
import fpoly.electroland.model.User;
import fpoly.electroland.repository.ActionRepository;
import fpoly.electroland.service.*;
import fpoly.electroland.util.DateUtil;
import fpoly.electroland.util.JwtUtil;
import fpoly.electroland.util.ResponseEntityUtil;
import jakarta.mail.MessagingException;

import java.util.Optional;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

@RestController
public class AuthController {

    private final ActionService actionService;

    private final ActionRepository actionRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @Autowired
    CustomerService customerService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    MailerService mailerService;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    JwtUtil jwtUtil = new JwtUtil();

    AuthController(ActionRepository actionRepository, ActionService actionService) {
        this.actionRepository = actionRepository;
        this.actionService = actionService;
    }

    @PostMapping("admin/login")
    public Object authenticateAdmin(@RequestBody User user) throws AuthenticationException {
        if (!employeeService.getEmployee(user.getEmail()).isPresent())
            return ResponseEntityUtil.unauthorizedError("Tài khoản không tồn tại");
        return userService.authentication_getData(user.getEmail(), user.getPassword());
    }

    @PostMapping("/login")
    public Object authenticate(@RequestBody User user) throws AuthenticationException {
        if (!customerService.getCustomer(user.getEmail()).isPresent()) {
            return ResponseEntityUtil.unauthorizedError("Tài khoản không tồn tại");
        } else if (customerService.getCustomer(user.getEmail()).isPresent()
                && customerService.getCustomer(user.getEmail()).get().getStatus() == false) {
            return ResponseEntityUtil.unauthorizedError("Tài khoản đã bị khóa");
        }
        return userService.authentication_getData(user.getEmail(), user.getPassword());
    }

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        if (customerService.getCustomer(email).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tài khoản đã tồn tại.");
        }

        try {
            mailerService.sendOtpCodeToVerifyEmail(email);
            return ResponseEntity.ok("OTP đã được gửi thành công. Vui lòng kiểm tra email để xác nhận.");
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Có lỗi khi gửi OTP. Vui lòng thử lại.");
        }
    }

    @PostMapping("/register")
    public Object register(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");

        String storedOtp = redisTemplate.opsForValue().get("otp:" + email);
        if (storedOtp == null || !storedOtp.equals(otp)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("OTP không hợp lệ hoặc đã hết hạn.");
        }

        if (customerService.getCustomer(email).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tài khoản đã tồn tại.");
        }

        Customer newCustomer = new Customer();
        newCustomer.setEmail(email);
        newCustomer.setAvatar(request.get("avatar"));
        newCustomer.setFullName(request.get("fullName"));
        newCustomer.setPassword(passwordEncoder.encode(request.get("password")));
        newCustomer.setPhoneNumber(request.get("phoneNumber"));
        newCustomer.setStatus(true);
        String dob = request.get("dateOfBirth");
        Date dateOfBirth = null;
        try {
            dateOfBirth = DateUtil.formatDate(dob);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ngày sinh không hợp lệ.");
        }
        newCustomer.setDateOfBirth(dateOfBirth);

        Boolean gender = Boolean.parseBoolean(request.get("gender"));
        newCustomer.setGender(gender);
        newCustomer.setUserPoint(0);
        customerService.createCustomer(newCustomer);
        return userService.authentication_getData(email, request.get("password"));
    }

    @PostMapping("/google-login")
    public Object authenticateWithGoogle(@RequestParam String token)
            throws GeneralSecurityException, IOException, MessagingException {
        @SuppressWarnings("deprecation")
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder( // Tạo 1 đối tượng dùng để xác thực Google
                                                                            // Id
                new NetHttpTransport(),
                new JacksonFactory() // Dùng để kết nối với phân tích cú pháp JSON của Google API
        )
                .setAudience(Collections
                        .singletonList("975818315662-aamqq1dhn3adae3640jrc8902oedicfn.apps.googleusercontent.com"))
                .build(); // Dùng để xác thực token được gửi từ cái client ID trên

        GoogleIdToken idToken = verifier.verify(token); // Xác mình token hợp lệ ko hợp lệ nó sẽ trả null
        if (idToken == null) {
            System.out.println("Token không hợp lệ hoặc hết hạn.");
            return ResponseEntityUtil.unauthorizedError("Token không hợp lệ hoặc hết hạn");
        }

        Payload payload = idToken.getPayload(); // Payload chưa thông tin của người dùng bằng cách mã hóa Token
        String email = payload.getEmail();
        String firstName = (String) payload.get("given_name");
        String lastName = (String) payload.get("family_name");
        String fullName = firstName + " " + lastName;
        String image = (String) payload.get("picture");
        Optional<Customer> existingCustomer = customerService.getCustomer(email); // Kiểm tra đã tồn tại Email này thì
                                                                                  // login luôn
        if (existingCustomer.isPresent()) {
            return userService.returnUser(existingCustomer.get().getEmail(), "CUSTOMER",
                    existingCustomer.get().getFullName());
        } else { // Ngược lại tạo mới
            String password = customerService.generatePassword(8); // Mật khẩu mẫu "Password123"
            Customer newCustomer = new Customer();
            newCustomer.setAvatar(image);
            newCustomer.setDateOfBirth(new Date());
            newCustomer.setEmail(email);
            newCustomer.setFullName(fullName);
            newCustomer.setGender(true);
            newCustomer.setPassword(passwordEncoder.encode(password)); // Mật khẻ mẫu "Password123");
            newCustomer.setPhoneNumber("0123456789");
            newCustomer.setUserPoint(0);
            customerService.createCustomerGoogle(newCustomer);
            // Gửi email thông báo tài khoản đã được tạo
            mailerService.send(new Mail(email, "Đăng ký thành công Electroland", "Mật khẩu của bạn là: " + password));
            return userService.authentication_getData(email, password); // Rồi đăng nhập
        }
    }
}
