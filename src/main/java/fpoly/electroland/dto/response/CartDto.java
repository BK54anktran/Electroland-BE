package fpoly.electroland.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {

    int id;
    int idProduct;
    String nameProduct;
    String avatarProduct;
    String description;
    int quantity;
    Boolean status;
    Double price;
}
