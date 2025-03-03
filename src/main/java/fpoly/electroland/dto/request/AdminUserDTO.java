package fpoly.electroland.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminUserDTO {
    private String fullName;
    private String email;
    private String phoneNumber;
    private String role;
    private Boolean status;
}
