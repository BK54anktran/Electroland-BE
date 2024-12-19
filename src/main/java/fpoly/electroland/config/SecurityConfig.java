// package fpoly.electroland.config;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.Customizer;
// import
// org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import
// org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
// import org.springframework.web.filter.CorsFilter;

// import static org.springframework.security.config.Customizer.withDefaults;

// /**
// * SecurityConfig
// */
// @Configuration

// public class SecurityConfig {
// @Autowired
// private UserDetailsService userDetailsService;

// public SecurityConfig(UserDetailsService userDetailsService) {
// this.userDetailsService = userDetailsService;
// }

// @Bean
// public PasswordEncoder passwordEncoder() {
// return new BCryptPasswordEncoder();
// }

// @SuppressWarnings("removal")
// @Bean
// public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

// http.cors(withDefaults())
// .csrf(csrf -> csrf.disable())
// .authorizeHttpRequests(auth -> {
// auth.requestMatchers("/admin/product-detail").hasAuthority("ROLE_ADMIN")
// .requestMatchers("/admin/**").hasAnyRole("ADMIN", "USER")
// .requestMatchers("/admin-login").permitAll()
// .anyRequest().permitAll();
// }).formLogin(formLogin -> formLogin
// .loginPage("/admin-login")
// .loginProcessingUrl("/process-login")
// .usernameParameter("email")
// .passwordParameter("password")
// .defaultSuccessUrl("/admin/index")
// .permitAll())
// .logout(httpSecurityLogoutConfigurer ->
// httpSecurityLogoutConfigurer.logoutUrl("/admin/log-out")
// .logoutSuccessUrl("/admin-login")
// .invalidateHttpSession(true)
// .clearAuthentication(true)
// .deleteCookies("JSESSIONID")
// .permitAll())
// .exceptionHandling(
// exceptionHandlingConfigurer ->
// exceptionHandlingConfigurer.accessDeniedPage("/accessDenied"))
// .rememberMe(rememberMeConfigurer -> rememberMeConfigurer.key("electroland")
// .rememberMeParameter("remember-me")
// .tokenValiditySeconds(86400))
// .httpBasic(Customizer.withDefaults());
// return http.build();
// }

// @Bean
// public AuthenticationManager
// authenticationManager(AuthenticationConfiguration configuration) throws
// Exception {
// return configuration.getAuthenticationManager();
// }

// @Bean
// public CorsFilter corsFilter() {
// UrlBasedCorsConfigurationSource source = new
// UrlBasedCorsConfigurationSource();
// CorsConfiguration config = new CorsConfiguration();
// config.setAllowCredentials(true); // Nếu sử dụng cookie hoặc xác thực
// config.addAllowedOrigin("http://localhost:3000"); // URL React app
// config.addAllowedHeader("*");
// config.addAllowedMethod("*"); // GET, POST, PUT, DELETE, ...
// source.registerCorsConfiguration("/**", config);
// return new CorsFilter(source);
// }

// }
