package fpoly.electroland.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data

public class ReceiptRequest {

    private int id;
    private String address;
    private String nameReciver;
    private String phoneReciever;
    private String note;
    private int paymentType;
    private List<Integer> listCouponProduct;
    private int idReceiptCoupon;
    private Double totalAmount;
    private String content;

}
