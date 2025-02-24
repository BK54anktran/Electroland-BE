package fpoly.electroland.restController;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.model.Receipt;
import fpoly.electroland.model.ReceiptDetail;
import fpoly.electroland.service.EmployeeService;
import fpoly.electroland.service.ReceiptDetailService;
import fpoly.electroland.service.ReceiptService;
import fpoly.electroland.service.UserService;
@RestController
@RequestMapping("/admin")
public class DashboardController {
    
    @Autowired
    private ReceiptDetailService receiptDetailService;

    /**
     * API nhận ngày từ tham số truyền vào để tính tổng số lượng sản phẩm bán được
     * trong khoảng từ 1 tháng trước đến ngày truyền vào.
     *
     * @param date Ngày kết thúc (theo định dạng YYYY-MM-DD).
     * @return Dữ liệu tổng số lượng sản phẩm bán trong phạm vi từ 1 tháng trước đến ngày đó.
     */

    @GetMapping("/dashboard/sales-data-by-month-range")
    public ResponseEntity<List<Object[]>> getSalesDataByMonthRange( 
        @RequestParam(value = "date", required = false) String  CompareDateStr) {
            LocalDateTime CompareDate = null;
            if (CompareDateStr != null) {
                CompareDate = LocalDateTime.parse(CompareDateStr + "T00:00:00");
                System.out.println("CompareDate: " + CompareDate);
            }
        List<Object[]> salesData = receiptDetailService.getSalesDataByMonthRange(CompareDate);
        return new ResponseEntity<>(salesData, HttpStatus.OK);
    }
}
