package fpoly.electroland.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCouponDto {
    int id;
    int idProduct;
    String img;
    String description;
    Double discount;
}
