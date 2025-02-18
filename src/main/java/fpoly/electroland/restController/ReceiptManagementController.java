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
import fpoly.electroland.service.ReceiptService;
import fpoly.electroland.service.UserService;

@RestController
@RequestMapping("/admin")
public class ReceiptManagementController {
    @Autowired
    ReceiptService receiptService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    UserService userService;

    @GetMapping("/receipts")
    public List<Receipt> GetAllList() {
        List<Receipt> list = receiptService.getAll();
        return list;
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
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr) {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;

        if (startDateStr != null) {
            LocalDate localStartDate = LocalDate.parse(startDateStr, dateFormatter);
            startDate = localStartDate.atStartOfDay();
        }

        if (endDateStr != null) {
            LocalDate localEndDate = LocalDate.parse(endDateStr, dateFormatter);
            endDate = localEndDate.atTime(23, 59, 59);
        }

        return receiptService.getReceiptsByDateRange(startDate, endDate);
    }

    @GetMapping("/receipts/search")
    public ResponseEntity<List<Receipt>> searchReceipts(@RequestParam String searchKey) {
        try {
            // Gọi service để tìm kiếm nhân viên theo key
            List<Receipt> Receipts = receiptService.searchReceipts(searchKey);
            return ResponseEntity.ok(Receipts);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/receipts/updatestatus/{receiptID}/{receiptStatusID}")
    public ResponseEntity<?> updateReceiptStatus(
            @PathVariable Long receiptID,
            @PathVariable Integer receiptStatusID) {
        try {
            Integer userId = userService.getUser().getId();

            Receipt updatedReceipt = receiptService.updateReceiptStatus(receiptID, receiptStatusID, userId);

            return ResponseEntity.ok(updatedReceipt);

        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy hóa đơn với ID: " + receiptID);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi cập nhật hóa đơn: " + e.getMessage());
        }
    }

    

    // 🔹 1. API lấy tổng số đơn hàng
    @GetMapping("/orders/count")
    public long getTotalOrders() {
        return receiptService.countTotalOrders();
    }

    // 🔹 2. API lấy số đơn hàng theo trạng thái
    @GetMapping("/orders/status")
    public Map<String, Long> getOrdersByStatus() {
        return receiptService.countOrdersByStatus();
    }

    // 🔹 3. API lấy tổng doanh thu
    @GetMapping("/orders/revenue")
    public double getTotalRevenue() {
        return receiptService.getTotalRevenue();
    }

    // 🔹 4. API lấy doanh thu theo tháng
    @GetMapping("/orders/revenue/monthly")
    public List<Object[]> getRevenueByMonth() {
        return receiptService.getRevenueByMonth();
    }

    // 🔹 5. API lấy số đơn hàng theo phương thức thanh toán
    @GetMapping("/orders/payment-methods")
    public List<Object[]> getOrdersByPaymentMethod() {
        return receiptService.countOrdersByPaymentMethod();
    }


    // 🔹 7. API lấy tỷ lệ hoàn đơn
    @GetMapping("/orders/refund-rate")
    public double getRefundRate() {
        return receiptService.getRefundRate();
    }

}
