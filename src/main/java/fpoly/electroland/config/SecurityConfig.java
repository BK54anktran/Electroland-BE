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

import fpoly.electroland.util.JwtRequestFilter;
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
        Set<String> Endpoint = Set.of("/login", "/product", "/category" , "/supplier");
        jwtRequestFilter.setEndpoints(Endpoint);
        http
                .cors(withDefaults()) // Hỗ trợ CORS
                .csrf(csrf -> csrf.disable()) // Tắt CSRF cho API
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/login").permitAll() // Mở quyền truy cập cho API đăng nhập
                            .requestMatchers("/admin/**").hasAnyAuthority("Admin") // Quyền hạn khác
                            .anyRequest().permitAll(); // Yêu cầu xác thực với các request khác
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
        config.addAllowedHeader("*");
        config.addAllowedMethod("*"); // GET, POST, PUT, DELETE, ...
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

}