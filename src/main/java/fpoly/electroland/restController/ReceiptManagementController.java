package fpoly.electroland.restController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.model.Receipt;
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
}
