package fpoly.electroland.restController;

import java.io.File;
import org.springframework.http.HttpHeaders;
import java.util.List;

import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.dto.request.ReceiptDTO;
import fpoly.electroland.model.Customer;
import fpoly.electroland.model.Receipt;
import fpoly.electroland.model.ReceiptStatus;
import fpoly.electroland.model.User;
import fpoly.electroland.service.CustomerService;
import fpoly.electroland.service.PdfService;
import fpoly.electroland.service.ReceiptService;
import fpoly.electroland.service.ReceiptStatusService;
import fpoly.electroland.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Autowired
    PdfService pdfService;
    

    @GetMapping("/userReceipt")
    public List<Receipt> getReceiptByUser() {
        Customer customer = customerService.findCustomerById(userService.getUser().getId()).get();
        return receiptService.getReceiptsByUser(customer);
    }
    
    @GetMapping("/getReceiptStatus")
    public List<ReceiptStatus> getReceiptStatust() {
        return receiptStatusService.getAll();
    }
    @PostMapping("/generate")
    public ResponseEntity<?> generateReceipt(@RequestBody ReceiptDTO receipt) {
        try {
            String pdfPath = pdfService.generatePdf(receipt);
            File file = new File(pdfPath);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                    .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                    .body(new FileSystemResource(file));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Lỗi tạo PDF!");
        }
    }
}
