// package fpoly.electroland.service;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.Mock;
// import org.mockito.Mockito;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContext;
// import org.springframework.security.core.context.SecurityContextHolder;

// import fpoly.electroland.model.User;

// @ExtendWith(MockitoExtension.class)
// public class UserTest {
//     @Mock
//     private Authentication mockAuthentication;

//     @Mock
//     private SecurityContextHolder securityContextService;

//     @Mock
//     private UserService userService;

//     @Mock
//     private User user1;

//     @BeforeEach
//     void setUp() {
//         user1 = new User();
//         user1.setEmail("daotankiet@gmail.com");
//         user1.setId(1);
//         user1.setName("Đào Tấn Kiệt");
//         user1.setPassword("123");
//         user1.setRole("Admin");
//     }

//     @Test
//     public void testGetUser() {
//         // Mock the SecurityContextService
//         when(securityContextService.getAuthentication()).thenReturn(mockAuthentication);
//         when(mockAuthentication.getPrincipal()).thenReturn(user1);

//         // Set the securityContextService mock in the UserService (Assuming UserService is using it)
//         userService.setSecurityContextService(securityContextService);

//         // Call the method
//         User result = userService.getUser();
//         System.out.println(result);

//         assertNotNull(result, "User should not be null");
//         Mockito.verify(mockAuthentication).getPrincipal();
//     }
// }
