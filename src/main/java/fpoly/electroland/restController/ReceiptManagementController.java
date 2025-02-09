package fpoly.electroland.restController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
     @GetMapping("/receipts/date-range")
     public List<Receipt> getReceiptsByDateRange(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
    return receiptService.getReceiptsByDateRange(startDate, endDate);
}
    @GetMapping("/receipts/search")
    public List<Receipt> searchReceipts(@RequestParam String searchKey) {
        return receiptService.searchReceipts(searchKey);
    }       
}
