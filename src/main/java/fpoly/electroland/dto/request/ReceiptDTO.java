package fpoly.electroland.dto.request;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ReceiptDTO {
    private String id;
    private String customerName;
    private String customerAddress;
    private String customerPhone;
    private String email;
    private String date;
    private double discount;
    private List<Item> items;

    @Data
    public static class Item {
        private String name;
        private int quantity;
        private double price;
    }

    public double totalPrice;
    
    public double finalPrice;
    
}
