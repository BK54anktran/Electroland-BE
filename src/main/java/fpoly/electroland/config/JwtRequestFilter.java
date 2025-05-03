package fpoly.electroland.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import fpoly.electroland.model.Customer;
import fpoly.electroland.model.Employee;
import fpoly.electroland.model.User;
import fpoly.electroland.service.CustomerService;
import fpoly.electroland.service.EmployeeService;
import fpoly.electroland.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Set<String> endpointsToSkip = new HashSet<>();

    public void setEndpoints(Set<String> endpoints) {
        this.endpointsToSkip = endpoints;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws java.io.IOException, jakarta.servlet.ServletException {

        // Bỏ qua các endpoint cần skip
        if (endpointsToSkip.contains(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }

        // Lấy Authorization header
        final String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;
        String role = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwt);
                role = jwtUtil.extractRole(jwt);
            } catch (JwtException e) {
                throw e;
            }
        }

        // Xác thực nếu username không null và chưa được xác thực trước đó
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = null;

            if ("CUSTOMER".equals(role)) {
                Optional<Customer> customer = customerService.getCustomer(username);
                if (customer.isPresent()) {
                    List<GrantedAuthority> authorities = new ArrayList<>();
                    authorities.add(new SimpleGrantedAuthority("Customer"));
                    userDetails = new User(customer.get().getId(), customer.get().getFullName(),
                            customer.get().getEmail(),
                            customer.get().getPassword(), "CUSTOMER", authorities);
                } else
                    throw new UsernameNotFoundException(username);
            } else {
                Optional<Employee> userInfo = employeeService.getEmployee(username);
                if (userInfo.isPresent()) {
                    List<GrantedAuthority> authorities = new ArrayList<>();
                    authorities.add(new SimpleGrantedAuthority(userInfo.get().getRole()));
                    userDetails = new User(userInfo.get().getId(), userInfo.get().getFullName(),
                            userInfo.get().getEmail(),
                            userInfo.get().getPassword(), userInfo.get().getRole(),
                            authorities);
                } else
                    throw new UsernameNotFoundException(username);
            }

            // Kiểm tra token hợp lệ
            if (userDetails != null && jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                var authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Gọi filter tiếp theo trong chuỗi
        chain.doFilter(request, response);
    }
}
