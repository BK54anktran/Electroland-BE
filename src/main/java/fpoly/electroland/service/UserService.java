package fpoly.electroland.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.User;
import fpoly.electroland.util.JwtUtil;
import fpoly.electroland.util.ResponseEntityUtil;

@Service
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    Authentication authentication = null;

    @Autowired
    private JwtUtil jwtUtil;

    public User getUser() {
        this.authentication = this.authentication instanceof User
                ? this.authentication
                : SecurityContextHolder.getContext().getAuthentication();
        // System.out.println("Author: "+authentication.getPrincipal().toString());
        return this.authentication.getPrincipal() instanceof User ? (User) authentication.getPrincipal() : null;
    }

    public Object authentication_getData(String email, String password) {
        try {
            this.authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));
        } catch (Exception e) {
            return ResponseEntityUtil.unauthorizedError("Mật khẩu không chính xác");
        }
        User user = (User) authentication.getPrincipal();
        Map<String, String> data = new HashMap<>();
        data.put("token", jwtUtil.generateToken(user.getEmail(), user.getRole()));
        data.put("userName", user.getName());
        return ResponseEntity.ok(data);
    }
}
