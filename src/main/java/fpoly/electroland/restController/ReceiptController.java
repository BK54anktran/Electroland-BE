package fpoly.electroland.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.model.Customer;
import fpoly.electroland.model.Receipt;
import fpoly.electroland.model.ReceiptStatus;
import fpoly.electroland.service.CustomerService;
import fpoly.electroland.service.ReceiptService;
import fpoly.electroland.service.ReceiptStatusService;
import fpoly.electroland.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class ReceiptController {
    @Autowired
    ReceiptService receiptService;

    @Autowired
    CustomerService customerService;

    @Autowired
    ReceiptStatusService receiptStatusService;

    @Autowired
    UserService userService;

    @GetMapping("/userReceipt")
    public List<Receipt> getReceiptByUser() {
        Customer customer = customerService.findCustomerById(userService.getUser().getId()).get();
        return receiptService.getReceiptsByUser(customer);
    }

    @GetMapping("/getReceiptStatus")
    public List<ReceiptStatus> getReceiptStatust() {
        return receiptStatusService.getAll();
    }
}
