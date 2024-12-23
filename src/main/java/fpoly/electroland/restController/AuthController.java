package fpoly.electroland.restController;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import fpoly.electroland.model.User;
import fpoly.electroland.util.JwtUtil;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("admin/login")
    public String authenticateAdmin(@RequestBody User user) throws AuthenticationException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        return jwtUtil.generateToken(user.getUsername());
    }

    @PostMapping("/login")
    public Map<String, String> authenticate(@RequestBody User user) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        User user2 = (User) authentication.getPrincipal();
        Map<String, String> user_Login = new HashMap<>();
        user_Login.put("token", jwtUtil.generateToken(user.getUsername()));
        user_Login.put("userName", user2.getName());
        System.out.println(user2.getName());
        return user_Login;
    }
}
