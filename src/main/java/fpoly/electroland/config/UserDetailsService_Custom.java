package fpoly.electroland.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import fpoly.electroland.model.Customer;
import fpoly.electroland.model.Employee;
import fpoly.electroland.model.User;
import fpoly.electroland.service.CustomerService;
import fpoly.electroland.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Configuration
@RequiredArgsConstructor
public class UserDetailsService_Custom implements UserDetailsService {

    private final EmployeeService employeeService;

    private final CustomerService customerService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String requestUrl = request.getRequestURL().toString();

        if (requestUrl.startsWith("/admin")) {
            Optional<Employee> userInfo = employeeService.getEmployee(email);
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

        Optional<Customer> customer = customerService.getCustomer(email);
        if (customer.isPresent()) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("Customer"));
            return new User(customer.get().getId(), customer.get().getFullName(),
                    customer.get().getEmail(),
                    passwordEncoder().encode(customer.get().getPassword()), authorities);
        }
        throw new UsernameNotFoundException(email);
    }
}
