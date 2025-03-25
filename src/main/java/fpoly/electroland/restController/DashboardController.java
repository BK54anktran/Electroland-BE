package fpoly.electroland.restController;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
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
import fpoly.electroland.service.ExcelExportService;
import fpoly.electroland.service.ReceiptDetailService;
import fpoly.electroland.service.ReceiptService;
import fpoly.electroland.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
@RestController
@RequestMapping("/admin")
public class DashboardController {
    
    @Autowired
    private ReceiptDetailService receiptDetailService;

    @Autowired
    private ExcelExportService excelExportService;
    //API để thống kê top 10 sản phẩm bán chạy nhất
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
    // API để nhận ngày và trả về dữ liệu thống kê
    @GetMapping("/dashboard/getSalesData")
    public ResponseEntity<Map<String, Object>> getSalesData(
        @RequestParam (value = "date", required = false) String  endDateStr) {
            LocalDateTime endDate = null;
            // Kiểm tra nếu endDateStr không null và chuyển thành LocalDateTime
            if (endDateStr != null) {
                endDate = LocalDateTime.parse(endDateStr + "T00:00:00");
                System.out.println("CompareDate: " + endDate);
            }
        // Tạo một Map để trả về dữ liệu
        Map<String, Object> response = new HashMap<>();
        
        // Tính toán các chỉ số cho tháng hiện tại và tháng trước
        Long revenue = receiptDetailService.getRevenue(endDate);
        Long successfulOrders = receiptDetailService.getSuccessfulOrders(endDate);
        Long failedOrders = receiptDetailService.getFailedOrders(endDate);
        Long customerCount = receiptDetailService.getCustomerCount(endDate);
        
        // Tính toán phần trăm thay đổi so với tháng trước
        Double revenuePercentChange = receiptDetailService.getRevenuePercentChange(endDate);
        Double successfulOrdersPercentChange = receiptDetailService.getSuccessfulOrdersPercentChange(endDate);
        Double failedOrdersPercentChange = receiptDetailService.getFailedOrdersPercentChange(endDate);
        Double customerCountPercentChange = receiptDetailService.getCustomerCountPercentChange(endDate);
        
        // Thêm dữ liệu vào response
        response.put("doanhSo", createStatistic("Doanh số", revenue, "VND", revenuePercentChange));
        response.put("donThanhCong", createStatistic("Đơn thành công", successfulOrders, "Đơn", successfulOrdersPercentChange));
        response.put("donThatBai", createStatistic("Đơn thất bại", failedOrders, "Đơn", failedOrdersPercentChange));
        response.put("soLuongKhachHang", createStatistic("Số lượng khách hàng", customerCount, "Người", customerCountPercentChange));

        return ResponseEntity.ok(response);
    }

    // Helper method to create statistic object
    private Map<String, Object> createStatistic(String title, Long value, String suffix, Double percentChange) {
        Map<String, Object> statistic = new HashMap<>();
        statistic.put("title", title);
        statistic.put("value", value);
        statistic.put("suffix", suffix);
        statistic.put("percentChange", percentChange);
        return statistic;
    }
    // API để nhận ngày và trả về doanh thu theo tháng
    @GetMapping("/dashboard/getRevenueByMonth")
    public ResponseEntity<List<Object[]>> getRevenueByMonth(
            @RequestParam(value = "date") String endDateStr) {
        
                LocalDateTime endDate = null;
                // Kiểm tra nếu endDateStr không null và chuyển thành LocalDateTime
                if (endDateStr != null) {
                    endDate = LocalDateTime.parse(endDateStr + "T00:00:00");
                    System.out.println("CompareDate: " + endDate);
                }

         // Lấy doanh thu theo tháng từ Service
    List<Object[]> revenueData = receiptDetailService.getRevenueByMonth(endDate);

    return ResponseEntity.ok(revenueData);
    }
     // API tỉ lệ đơn hàng thành công trên tổng đơn hàng từ đầu tháng đến ngày truyền vào
     @GetMapping("/dashboard/success-rate")
     public ResponseEntity<Double> getSuccessRate(
             @RequestParam(value = "date") String endDateStr) {
         
                 LocalDateTime endDate = null;
                 // Kiểm tra nếu endDateStr không null và chuyển thành LocalDateTime
                 if (endDateStr != null) {
                     endDate = LocalDateTime.parse(endDateStr + "T00:00:00");
                     System.out.println("CompareDate: " + endDate);
                 }
 
          // Lấy doanh thu theo tháng từ Service
     Double successratedata = receiptDetailService.getSuccessRate(endDate);
 
     return ResponseEntity.ok(successratedata);
     }
     @GetMapping("/dashboard/payment-method-stats")
    public ResponseEntity<List<Object[]>> getPaymentMethodStats(
        @RequestParam(value = "date") String endDateStr) {
            LocalDateTime endDate = null;
            // Kiểm tra nếu endDateStr không null và chuyển thành LocalDateTime
            if (endDateStr != null) {
                endDate = LocalDateTime.parse(endDateStr + "T00:00:00");
                System.out.println("CompareDate: " + endDate);
            }
        // Gọi service để lấy dữ liệu
        List<Object[]> paymentStats = receiptDetailService.getPaymentMethodStats(endDate);

        // Trả về dữ liệu
        return ResponseEntity.ok(paymentStats);
    }
    @GetMapping("/dashboard/all-dashboard-data")
    public void exportAllDashboardData(
            @RequestParam(value = "date") String endDateStr,
            HttpServletResponse response) throws IOException {
    
        LocalDateTime endDate = null;
        if (endDateStr != null) {
            endDate = LocalDateTime.parse(endDateStr + "T00:00:00");
        }
    
        // Get all data
        Map<String, Object> salesData = getSalesDataMap(endDate);
        List<Object[]> revenueByMonth = receiptDetailService.getRevenueByMonth(endDate);
        Double successRate = receiptDetailService.getSuccessRate(endDate);
        List<Object[]> paymentStats = receiptDetailService.getPaymentMethodStats(endDate);
        List<Object[]> salesDataByMonth = receiptDetailService.getSalesDataByMonthRange(endDate);
    
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=bao_cao.xlsx");
        
        // Export all data to a single Excel file with multiple sheets
        try {
            excelExportService.exportAllDashboardData(
                salesData,
                revenueByMonth,
                successRate,
                paymentStats,
                salesDataByMonth,
                endDate,
                response // passing response to export method to write to OutputStream
            );
        } catch (IOException e) {
            // Log the error
            e.printStackTrace();
            // Set an error status
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error exporting Excel file: " + e.getMessage());
        }
    }
    
    
    
     // Helper method to get sales data map (similar to getSalesData in DashboardController)
     private Map<String, Object> getSalesDataMap(LocalDateTime endDate) {
        // Tạo một Map để trả về dữ liệu
        Map<String, Object> response = new java.util.HashMap<>();
        
        // Tính toán các chỉ số cho tháng hiện tại và tháng trước
        Long revenue = receiptDetailService.getRevenue(endDate);
        Long successfulOrders = receiptDetailService.getSuccessfulOrders(endDate);
        Long failedOrders = receiptDetailService.getFailedOrders(endDate);
        Long customerCount = receiptDetailService.getCustomerCount(endDate);
        
        // Tính toán phần trăm thay đổi so với tháng trước
        Double revenuePercentChange = receiptDetailService.getRevenuePercentChange(endDate);
        Double successfulOrdersPercentChange = receiptDetailService.getSuccessfulOrdersPercentChange(endDate);
        Double failedOrdersPercentChange = receiptDetailService.getFailedOrdersPercentChange(endDate);
        Double customerCountPercentChange = receiptDetailService.getCustomerCountPercentChange(endDate);
        
        // Thêm dữ liệu vào response
        response.put("doanhSo", createStatistic("Doanh số", revenue, "VND", revenuePercentChange));
        response.put("donThanhCong", createStatistic("Đơn thành công", successfulOrders, "Đơn", successfulOrdersPercentChange));
        response.put("donThatBai", createStatistic("Đơn thất bại", failedOrders, "Đơn", failedOrdersPercentChange));
        response.put("soLuongKhachHang", createStatistic("Số lượng khách hàng", customerCount, "Người", customerCountPercentChange));

        return response;
    }
    
}
