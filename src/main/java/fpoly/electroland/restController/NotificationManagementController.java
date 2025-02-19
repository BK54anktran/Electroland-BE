package fpoly.electroland.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.model.Receipt;
import fpoly.electroland.model.Review;
import fpoly.electroland.service.ReceiptService;
import fpoly.electroland.service.ReviewService;

@RestController
@RequestMapping("/admin")

public class NotificationManagementController {
    @Autowired
    ReviewService reviewService;
    @Autowired
    ReceiptService receiptService;

    @PutMapping("/review/updateReadStatus/{id}")
    public String updateStatusRead(@PathVariable("id") int id) {
        boolean isUpdated = reviewService.updateReadStatus(id);
        if (isUpdated) {
            return "Status updated successfully!";
        } else {
            return "Failed to update status!";
        }
    }

    @PutMapping("/receipt/updateReadStatus/{id}")
    public String updateReceiptStatus(@PathVariable("id") int id) {
        boolean isUpdated = receiptService.updateReadStatus(id);
        if (isUpdated) {
            return "Receipt status updated successfully!";
        } else {
            return "Failed to update receipt status!";
        }
    }
}
