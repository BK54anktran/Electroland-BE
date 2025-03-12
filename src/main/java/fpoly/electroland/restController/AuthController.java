package fpoly.electroland.restController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import fpoly.electroland.model.Customer;
import fpoly.electroland.model.User;
import fpoly.electroland.service.CustomerService;
import fpoly.electroland.service.EmployeeService;
import fpoly.electroland.service.UserService;
import fpoly.electroland.util.ResponseEntityUtil;
import java.util.Optional;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

@RestController
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    CustomerService customerService;

    @Autowired
    EmployeeService employeeService;

    @PostMapping("admin/login")
    public Object authenticateAdmin(@RequestBody User user) throws AuthenticationException {
        if (!employeeService.getEmployee(user.getEmail()).isPresent())
            return ResponseEntityUtil.unauthorizedError("Tài khoản không tồn tại");
        return userService.authentication_getData(user.getEmail(), user.getPassword());
    }

    @PostMapping("/login")
    public Object authenticate(@RequestBody User user) throws AuthenticationException {
        if (!customerService.getCustomer(user.getEmail()).isPresent())
            return ResponseEntityUtil.unauthorizedError("Tài khoản không tồn tại");
        return userService.authentication_getData(user.getEmail(), user.getPassword());
    }

    @PostMapping("/register")
    public Object register(@RequestBody Customer user) throws AuthenticationException {
        if (customerService.getCustomer(user.getEmail()).isPresent())
            return ResponseEntityUtil.badRequest("Tài khoản đã tồn tại");
        else {
            customerService.createCustomer(user);
            return userService.authentication_getData(user.getEmail(), user.getPassword());
        }
    }
    @PostMapping("/google-login")
    public Object authenticateWithGoogle(@RequestParam String token) throws GeneralSecurityException, IOException {
        @SuppressWarnings("deprecation")
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
            new NetHttpTransport(),
            new JacksonFactory()
        )
        .setAudience(Collections.singletonList("975818315662-aamqq1dhn3adae3640jrc8902oedicfn.apps.googleusercontent.com")) // Đảm bảo trùng với Client ID của bạn
        .build();

        GoogleIdToken idToken = verifier.verify(token);
        if (idToken == null) {
            System.out.println("Token không hợp lệ hoặc hết hạn.");
            return ResponseEntityUtil.unauthorizedError("Token không hợp lệ hoặc hết hạn");
        }

        Payload payload = idToken.getPayload();

        String email = payload.getEmail();
        String firstName = (String) payload.get("given_name");
        String lastName = (String) payload.get("family_name");
        String fullName = firstName + " " + lastName;
        String image = (String) payload.get("picture");

        System.out.println("IMAGE:" +image);

        Optional<Customer> existingCustomer = customerService.getCustomer(email);
        if (existingCustomer.isPresent()) {
            return userService.authentication_getData(email, existingCustomer.get().getPassword());
        } else {
            Customer newCustomer = new Customer();
            newCustomer.setEmail(email);
            newCustomer.setFullName(fullName);
            newCustomer.setDateOfBirth(new Date());
            newCustomer.setPhoneNumber("0123456789");
            newCustomer.setPassword("Password123!");
            newCustomer.setGender(true);
            newCustomer.setAvatar(image);
            System.out.println(newCustomer);
            customerService.createCustomerGoogle(newCustomer);
            return userService.authentication_getData(email, newCustomer.getPassword());
        }
    }    
}
