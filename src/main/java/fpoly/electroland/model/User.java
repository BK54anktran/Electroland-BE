// package fpoly.electroland.model;

// import java.util.Collection;
// import java.util.List;
// import lombok.AllArgsConstructor;
// import lombok.Data;
// import lombok.NoArgsConstructor;

// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;

// @Data
// @AllArgsConstructor
// @NoArgsConstructor
// public class User implements UserDetails {

// private int id;
// private String name;
// private String email;
// private String password;
// private String role;

// public User(String name, String email, String password, String role) {
// this.email = email;
// this.password = password;
// this.role = role;
// }

// @Override
// public Collection<? extends GrantedAuthority> getAuthorities() {
// return List.of(new SimpleGrantedAuthority(role));
// }

// @Override
// public String getUsername() {
// return name;
// }

// @Override
// public boolean isAccountNonExpired() {
// return UserDetails.super.isAccountNonExpired();
// }

// @Override
// public boolean isAccountNonLocked() {
// return UserDetails.super.isAccountNonLocked();
// }

// @Override
// public boolean isCredentialsNonExpired() {
// return UserDetails.super.isCredentialsNonExpired();
// }

// @Override
// public boolean isEnabled() {
// return UserDetails.super.isEnabled();
// }
// }