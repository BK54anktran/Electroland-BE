package fpoly.electroland.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    private int id;

    String address;

    private boolean status;

    private String nameReciever;

    private String phoneReciever;

}
