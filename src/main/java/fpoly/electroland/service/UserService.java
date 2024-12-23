package fpoly.electroland.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.User;
import fpoly.electroland.util.JwtUtil;

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
        System.out.println(authentication.getPrincipal().toString());
        return this.authentication.getPrincipal() instanceof User ? (User) authentication.getPrincipal() : new User();
    }

    public Map<String, String> authentication_getData(String email, String password) {
        this.authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        User user = (User) authentication.getPrincipal();
        Map<String, String> data = new HashMap<>();
        data.put("token", jwtUtil.generateToken(user.getEmail()));
        data.put("userName", user.getName());
        return data;
    }
}
