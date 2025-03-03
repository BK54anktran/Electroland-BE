package fpoly.electroland.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Set;

@Configuration
public class SecurityConfig {

    @SuppressWarnings("unused")
    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtRequestFilter jwtRequestFilter) throws Exception {
        Set<String> permitAllEndpoint = Set.of("/login", "/product", "/employees", "/employees/save",
                "/employees/update/**", "/register", "/admin/customer","/admin/customer/save", "/admin/customer/update/**","/admin/customer/search/**", "/admin/customer/filter/**", "/admin/customer/search-filter/**","/admin/review","/admin/review/**","admin/review/sreachs/**", "/admin/review/{id}/status", 
                "/receiptCoupon", "/receiptCoupon/search", "/receiptCoupon/{id}", "/receiptCoupon/new", "/receiptCoupon/update/{id}", "/receiptCoupon/delete/{id}", 
                "/getUserInfo", "/updateInfo", "/admin/product", "/discountProduct", "/discountProduct/newDiscountProduct", "/discountProduct/search", "/discountProduct/update/{id}");
        Set<String> AdminEndpoint = Set.of("/admin"); // Để tạm để test
        // jwtRequestFilter.setEndpoints(permitAllEndpoint);
        http
                .cors(withDefaults()) // Hỗ trợ CORS
                .csrf(csrf -> csrf.disable()) // Tắt CSRF cho API
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(AdminEndpoint.toArray(new String[0])).hasAnyAuthority("Admin")
                            // .requestMatchers(permitAllEndpoint.toArray(new String[0])).permitAll()
                            .anyRequest().permitAll();
                })
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // Nếu sử dụng cookie hoặc xác thực
        config.addAllowedOrigin("http://localhost:3000"); // URL React app
        config.addAllowedOrigin("https://bk54anktran.web.app");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*"); // GET, POST, PUT, DELETE, ...
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}