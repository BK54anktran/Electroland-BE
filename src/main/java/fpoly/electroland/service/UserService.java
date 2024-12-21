package fpoly.electroland.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.Employee;
import fpoly.electroland.model.User;
import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final EmployeeService employeeService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Employee> userInfo = employeeService.getUser(email);
        if (userInfo.isPresent()) {
            List<GrantedAuthority> authorities = userInfo.get().getEmployeeAuthority().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getAuthority().getName())) // Chuyển các
                    .collect(Collectors.toList());
            return new User(userInfo.get().getId(), userInfo.get().getFullName(),
                    userInfo.get().getEmail(),
                    passwordEncoder().encode(userInfo.get().getPassword()),
                    authorities);
        }
        throw new UsernameNotFoundException(email);
    }
}
