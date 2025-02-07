package fpoly.electroland.restController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.model.Receipt;
import fpoly.electroland.model.ReceiptDetail;
import fpoly.electroland.service.ReceiptService;

@RestController
@RequestMapping("/admin")
public class ReceiptManagementController {
    @Autowired
    ReceiptService receiptService;

    @GetMapping("/receipts")      
    public List<Receipt> GetAllList() {
        return receiptService.getAll();
    }   
     // Lấy chi tiết hóa đơn theo receiptId
     @GetMapping("/receiptDetail/{receiptId}")
     public ResponseEntity<?> getReceiptDetail(@PathVariable Long receiptId) {
         List<ReceiptDetail> receiptDetails = receiptService.getReceiptDetailsByReceiptId(receiptId);
 
         if (receiptDetails.isEmpty()) {
             return ResponseEntity.status(404).body("Receipt not found.");
         }
         return ResponseEntity.ok(receiptDetails);
     }              
}
