package fpoly.electroland.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.Employee;
import fpoly.electroland.model.Receipt;
import fpoly.electroland.model.ReceiptDetail;
import fpoly.electroland.model.ReceiptStatus;
import fpoly.electroland.repository.ActionRepository;
import fpoly.electroland.repository.EmployeeRepository;
import fpoly.electroland.repository.ReceiptDetailRepository;
import fpoly.electroland.repository.ReceiptRepository;
import fpoly.electroland.repository.ReceiptStatusRepository;
import fpoly.electroland.util.CreateAction;

@Service
public class ReceiptService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ActionRepository actionRepository;

    @Autowired
    CreateAction createAction;
    @Autowired
    ReceiptRepository receiptRepository;
    @Autowired
    ReceiptStatusRepository receiptStatusRepository;
    @Autowired
    private ReceiptDetailRepository receiptDetailRepository;

    @Autowired
    private ActionService actionService;

    public List<Receipt> getAll() {
        return receiptRepository.findAll();
    }

    // Sửa phương thức getReceiptById để nhận tham số kiểu Long
    public Optional<Receipt> getReceiptById(Long receiptId) {
        return receiptRepository.findById(receiptId); // Phương thức này đã trả về Optional<Receipt>
    }

    // Sửa phương thức getReceiptDetailsByReceiptId để nhận tham số kiểu Long
    public List<ReceiptDetail> getReceiptDetailsByReceiptId(Long receiptId) {
        return receiptDetailRepository.findByReceiptId(receiptId);
    }

    public List<Receipt> getReceiptsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null) {
            startDate = receiptRepository.findEarliestDate().orElse(LocalDateTime.of(2000, 1, 1, 0, 0, 0));
        }
        if (endDate == null) {
            endDate = LocalDateTime.now();
        }
        return receiptRepository.findReceiptsByDateRange(startDate, endDate);
    }

    public List<Receipt> searchReceipts(String searchKey) {
        List<Receipt> results = receiptRepository.searchReceipts(searchKey);
        return results;
    }

    public Receipt updateReceiptStatus(Long id, Integer statusId, int userId) {
        // Tìm hóa đơn theo ID
        Receipt existingReceipt = receiptRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy hóa đơn với ID: " + id));

        // Lưu trạng thái cũ trước khi cập nhật
        Integer oldStatusId = existingReceipt.getReceiptStatus().getId(); // Lưu ID cũ

        // Tìm trạng thái mới theo ID
        ReceiptStatus newStatus = receiptStatusRepository.findById(statusId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy trạng thái hóa đơn với ID: " + statusId));

        // Cập nhật trạng thái mới
        existingReceipt.setReceiptStatus(newStatus);

        // Lưu vào DB
        Receipt savedReceipt = receiptRepository.save(existingReceipt);

        // Tìm nhân viên thực hiện hành động
        Employee creatorEmployee = employeeRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên với ID: " + userId));

        // Ghi lại hành động vào bảng Action (Chỉ log ID để tránh StackOverflow)
        createAction.createAction(
                "Receipt",
                "UPDATE",
                savedReceipt.getId(),
                "Old Status: " + oldStatusId, // Sử dụng biến đã lưu
                "New Status: " + savedReceipt.getReceiptStatus().getId(), // Lấy trạng thái sau khi update
                creatorEmployee);

        return savedReceipt;
    }

    // 🔹 1. Tổng số đơn hàng
    public long countTotalOrders() {
        return receiptRepository.countTotalOrders();
    }

    // 🔹 2. Đếm đơn hàng theo trạng thái
    public Map<String, Long> countOrdersByStatus() {
        List<Object[]> results = receiptRepository.countOrdersByStatus();
        Map<String, Long> stats = new HashMap<>();

        for (Object[] row : results) {
            String status = (String) row[0];
            Long count = (Long) row[1];
            stats.put(status, count);
        }
        return stats;
    }

    // 🔹 3. Tổng doanh thu từ đơn hàng
    public double getTotalRevenue() {
        Double result = receiptRepository.totalRevenue();
        return result != null ? result : 0.0;
    }

    // 🔹 4. Doanh thu theo tháng
    public List<Object[]> getRevenueByMonth() {
        return receiptRepository.revenueByMonth();
    }

    // 🔹 5. Số đơn hàng theo phương thức thanh toán
    public List<Object[]> countOrdersByPaymentMethod() {
        return receiptRepository.countOrdersByPaymentMethod();
    }

    // 🔹 7. Tỷ lệ hoàn đơn
    public double getRefundRate() {
        Double result = receiptRepository.refundRate();
        return result != null ? result : 0.0;
    }

    public List<Map<String, Object>> getAllOrdersWithDetails() {
        List<Object[]> results = receiptRepository.findAllOrdersWithDetails();
        List<Map<String, Object>> orders = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> orderData = new HashMap<>();
            orderData.put("id", row[0]);
            orderData.put("receiptDate", row[1]);
            orderData.put("deliveryDate", row[2]);
            orderData.put("status", row[3]);
            orderData.put("paymentMethod", row[4]);
            orders.add(orderData);
        }

        return orders;
    }

    public List<Receipt> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return receiptRepository.findOrdersByDateRange(startDate, endDate);
    }

    // Thống kê tổng doanh thu trong khoảng thời gian
    public double getTotalRevenueByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        Double revenue = receiptRepository.getTotalRevenueByDateRange(startDate, endDate);
        return revenue != null ? revenue : 0.0;
    }

    public List<Map<String, Object>> getRevenueByMonth(LocalDateTime startDate, LocalDateTime endDate) {
        List<Object[]> results = receiptRepository.getRevenueByMonth(startDate, endDate);
        List<Map<String, Object>> revenueData = new ArrayList<>();
        for (Object[] row : results) {
            Map<String, Object> data = new HashMap<>();
            data.put("year", row[0]); // Lấy năm
            data.put("month", row[1]); // Lấy tháng
            data.put("revenue", row[2]); // Lấy doanh thu
            revenueData.add(data);
        }
        return revenueData;
    }

    public List<Map<String, Object>> getOrdersCountByMonth(LocalDateTime startDate, LocalDateTime endDate) {
        List<Object[]> results = receiptRepository.countOrdersByMonth(startDate, endDate);
        List<Map<String, Object>> ordersData = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> data = new HashMap<>();
            data.put("year", row[0]);
            data.put("month", row[1]);
            data.put("totalOrders", row[2]);
            ordersData.add(data);
        }
        return ordersData;
    }
    public Map<String, Long> countOrdersByStatusWithinRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<Object[]> results = receiptRepository.countOrdersByStatusWithinRange(startDate, endDate);
        Map<String, Long> stats = new HashMap<>();
        for (Object[] row : results) {
            String status = (String) row[0];
            Long count = (Long) row[1];
            stats.put(status, count);
        }
        return stats;
    }
    
    public List<Object[]> countOrdersByPaymentMethodWithinRange(LocalDateTime startDate, LocalDateTime endDate) {
        return receiptRepository.countOrdersByPaymentMethodWithinRange(startDate, endDate);
    }
    

}
