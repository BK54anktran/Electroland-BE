package fpoly.electroland.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptCouponDto {
    int id;
    Double discountMoney;
    Double discountPercent;
    Double maxDiscount;
    Double minReceiptPrice;
    String description;

}
