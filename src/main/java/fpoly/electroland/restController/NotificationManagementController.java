package fpoly.electroland.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
  
}
