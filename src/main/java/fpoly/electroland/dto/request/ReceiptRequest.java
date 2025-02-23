package fpoly.electroland.dto.request;

import java.util.Date;
import fpoly.electroland.model.Customer;
import fpoly.electroland.model.Payment;
import fpoly.electroland.model.ReceiptCoupon;
import fpoly.electroland.model.ReceiptStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data

public class ReceiptRequest {

    private String nameReciver;
    private String phoneReciver;
    private String address;
    private String note;
    private Payment payment;
    private ReceiptCoupon ReceiptCoupon;
}
