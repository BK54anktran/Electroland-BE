package fpoly.electroland.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.Receipt;
import fpoly.electroland.model.ReceiptDetail;
import fpoly.electroland.repository.ReceiptDetailRepository;

@Service
public class ReceiptDetailService {

    @Autowired
    ReceiptDetailRepository receiptDetailRepository;

    public List<ReceiptDetail> getAll() {
        return receiptDetailRepository.findAll();
    }

    public List<Object[]> getSalesDataByMonthRange(LocalDateTime compareDate) {
        // Check if compareDate is null
        if (compareDate == null) {
            throw new IllegalArgumentException("The compareDate parameter cannot be null");
        }

        // Convert LocalDateTime to LocalDate (remove time portion)
        int month = compareDate.getMonthValue();
        int year = compareDate.getYear();
        LocalDateTime end = compareDate;

        // Calculate the start of the month (01/MM/YYYY)
        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0); // Get the first day of the month
        // Call the Repository to get data
        System.out.println("start: " + start);
        System.out.println("end: " + end);
        return receiptDetailRepository.findSalesDataByDateRange(start, end);
    }

    // Lấy doanh thu từ đầu tháng đến ngày truyền vào (endDate)
    public Long getRevenue(LocalDateTime endDate) {
        // Tính toán ngày đầu tháng
        LocalDateTime startDate = LocalDateTime.of(endDate.getYear(), endDate.getMonth(), 1, 0, 0, 0, 0);
        return receiptDetailRepository.getRevenueByDateRange(startDate, endDate);
    }

    // Lấy số lượng đơn thành công từ đầu tháng đến ngày truyền vào (endDate)
    public Long getSuccessfulOrders(LocalDateTime endDate) {
        // Tính toán ngày đầu tháng
        LocalDateTime startDate = LocalDateTime.of(endDate.getYear(), endDate.getMonth(), 1, 0, 0, 0, 0);
        return receiptDetailRepository.getSuccessfulOrders(startDate, endDate);
    }

    // Lấy số lượng đơn thất bại từ đầu tháng đến ngày truyền vào (endDate)
    public Long getFailedOrders(LocalDateTime endDate) {
        // Tính toán ngày đầu tháng
        LocalDateTime startDate = LocalDateTime.of(endDate.getYear(), endDate.getMonth(), 1, 0, 0, 0, 0);
        return receiptDetailRepository.getFailedOrders(startDate, endDate);
    }
    // Lấy số lượng đơn đang xử lí từ đầu tháng đến ngày truyền vào (endDate)
    public Long getProcessingOrders(LocalDateTime endDate) {
        // Tính toán ngày đầu tháng
        LocalDateTime startDate = LocalDateTime.of(endDate.getYear(), endDate.getMonth(), 1, 0, 0, 0, 0);
        return receiptDetailRepository.getProcessingOrders(startDate, endDate);
    }
    // Lấy số lượng khách hàng từ đầu tháng đến ngày truyền vào (endDate)
    public Long getCustomerCount(LocalDateTime endDate) {
        // Tính toán ngày đầu tháng
        LocalDateTime startDate = LocalDateTime.of(endDate.getYear(), endDate.getMonth(), 1, 0, 0, 0, 0);
        return receiptDetailRepository.getCustomerCount(startDate, endDate);
    }

    // Tính phần trăm thay đổi của doanh thu so với tháng trước
    public Double getRevenuePercentChange(LocalDateTime endDate) {
        // Tính toán startDate và endDate cho tháng hiện tại
        LocalDateTime startDateCurrentMonth = LocalDateTime.of(endDate.getYear(), endDate.getMonth(), 1, 0, 0, 0, 0);
        LocalDateTime endDateCurrentMonth = endDate;

        // Tính toán startDate và endDate cho tháng trước
        LocalDateTime startDatePreviousMonth = startDateCurrentMonth.minusMonths(1);
        LocalDateTime endDatePreviousMonth = endDateCurrentMonth.minusMonths(1);

        // Lấy doanh thu của tháng hiện tại và tháng trước
        Long revenueCurrentMonth = receiptDetailRepository.getRevenueByDateRange(startDateCurrentMonth,
                endDateCurrentMonth);
        Long revenuePreviousMonth = receiptDetailRepository.getRevenueByDateRange(startDatePreviousMonth,
                endDatePreviousMonth);

        // Tính phần trăm thay đổi
        if (revenuePreviousMonth == null || revenuePreviousMonth == 0) {
            return revenueCurrentMonth != null && revenueCurrentMonth > 0 ? 100.0 : 0.0; // Tránh chia cho 0, nếu tháng
                                                                                         // trước không có doanh thu
        }
        double percentChange = ((double) (revenueCurrentMonth != null ? revenueCurrentMonth : 0)
                - (revenuePreviousMonth != null ? revenuePreviousMonth : 0))
                / (revenuePreviousMonth != null && revenuePreviousMonth > 0 ? revenuePreviousMonth : 1);
        BigDecimal roundedPercentChange = new BigDecimal(percentChange * 100).setScale(2, RoundingMode.HALF_UP);
        return roundedPercentChange.doubleValue();
    }

    // Tính phần trăm thay đổi số đơn thành công so với tháng trước
    public Double getSuccessfulOrdersPercentChange(LocalDateTime endDate) {
        // Tính toán startDate và endDate cho tháng hiện tại
        LocalDateTime startDateCurrentMonth = LocalDateTime.of(endDate.getYear(), endDate.getMonth(), 1, 0, 0, 0, 0);
        LocalDateTime endDateCurrentMonth = endDate;

        // Tính toán startDate và endDate cho tháng trước
        LocalDateTime startDatePreviousMonth = startDateCurrentMonth.minusMonths(1);
        LocalDateTime endDatePreviousMonth = endDateCurrentMonth.minusMonths(1);

        // Lấy số lượng đơn thành công của tháng hiện tại và tháng trước
        Long successfulOrdersCurrentMonth = receiptDetailRepository.getSuccessfulOrders(startDateCurrentMonth,
                endDateCurrentMonth);
        Long successfulOrdersPreviousMonth = receiptDetailRepository.getSuccessfulOrders(startDatePreviousMonth,
                endDatePreviousMonth);

        // Tính phần trăm thay đổi
        if (successfulOrdersPreviousMonth == 0) {
            return successfulOrdersCurrentMonth > 0 ? 100.0 : 0.0; // Tránh chia cho 0
        }
        double percentChange = ((double) (successfulOrdersCurrentMonth != null ? successfulOrdersCurrentMonth : 0)
                - (successfulOrdersPreviousMonth != null ? successfulOrdersPreviousMonth : 0))
                / (successfulOrdersPreviousMonth != null && successfulOrdersPreviousMonth > 0
                        ? successfulOrdersPreviousMonth
                        : 1);
        BigDecimal roundedPercentChange = new BigDecimal(percentChange * 100).setScale(2, RoundingMode.HALF_UP);
        return roundedPercentChange.doubleValue();
    }

    // Tính phần trăm thay đổi số đơn thất bại so với tháng trước
    public Double getFailedOrdersPercentChange(LocalDateTime endDate) {
        // Tính toán startDate và endDate cho tháng hiện tại
        LocalDateTime startDateCurrentMonth = LocalDateTime.of(endDate.getYear(), endDate.getMonth(), 1, 0, 0, 0, 0);
        LocalDateTime endDateCurrentMonth = endDate;

        // Tính toán startDate và endDate cho tháng trước
        LocalDateTime startDatePreviousMonth = startDateCurrentMonth.minusMonths(1);
        LocalDateTime endDatePreviousMonth = endDateCurrentMonth.minusMonths(1);

        // Lấy số lượng đơn thất bại của tháng hiện tại và tháng trước
        Long failedOrdersCurrentMonth = receiptDetailRepository.getFailedOrders(startDateCurrentMonth,
                endDateCurrentMonth);
        System.out.println("failedOrdersCurrentMonth: " + failedOrdersCurrentMonth);

        Long failedOrdersPreviousMonth = receiptDetailRepository.getFailedOrders(startDatePreviousMonth,
                endDatePreviousMonth);
        System.out.println("failedOrdersCurrentMonth: " + failedOrdersPreviousMonth);
        // Tính phần trăm thay đổi
        if (failedOrdersPreviousMonth == 0) {
            return failedOrdersCurrentMonth > 0 ? 100.0 : 0.0; // Tránh chia cho 0
        }
        double percentChange = ((double) failedOrdersCurrentMonth - failedOrdersPreviousMonth)
                / failedOrdersPreviousMonth;
        BigDecimal roundedPercentChange = new BigDecimal(percentChange * 100).setScale(2, RoundingMode.HALF_UP);
        return roundedPercentChange.doubleValue();
    }
    // Tính phần trăm thay đổi số đơn đang xử lí so với tháng trước
    public Double getProcessingOrdersPercentChange(LocalDateTime endDate) {
            // Tính toán startDate và endDate cho tháng hiện tại
            LocalDateTime startDateCurrentMonth = LocalDateTime.of(endDate.getYear(), endDate.getMonth(), 1, 0, 0, 0, 0);
            LocalDateTime endDateCurrentMonth = endDate;
    
            // Tính toán startDate và endDate cho tháng trước
            LocalDateTime startDatePreviousMonth = startDateCurrentMonth.minusMonths(1);
            LocalDateTime endDatePreviousMonth = endDateCurrentMonth.minusMonths(1);
    
            // Lấy số lượng đơn thất bại của tháng hiện tại và tháng trước
            Long ProcessingOrdersCurrentMonth = receiptDetailRepository.getProcessingOrders(startDateCurrentMonth,
                    endDateCurrentMonth);
            System.out.println("failedOrdersCurrentMonth: " + ProcessingOrdersCurrentMonth);
    
            Long ProcessingOrdersPreviousMonth = receiptDetailRepository.getProcessingOrders(startDatePreviousMonth,
                    endDatePreviousMonth);
            System.out.println("failedOrdersCurrentMonth: " + ProcessingOrdersPreviousMonth);
            // Tính phần trăm thay đổi
            if (ProcessingOrdersPreviousMonth == 0) {
                return ProcessingOrdersCurrentMonth > 0 ? 100.0 : 0.0; // Tránh chia cho 0
            }
            double percentChange = ((double) ProcessingOrdersCurrentMonth - ProcessingOrdersPreviousMonth)
                    / ProcessingOrdersPreviousMonth;
            BigDecimal roundedPercentChange = new BigDecimal(percentChange * 100).setScale(2, RoundingMode.HALF_UP);
            return roundedPercentChange.doubleValue();
        }
    // Tính phần trăm thay đổi số khách hàng so với tháng trước
    public Double getCustomerCountPercentChange(LocalDateTime endDate) {
        // Tính toán startDate và endDate cho tháng hiện tại
        LocalDateTime startDateCurrentMonth = LocalDateTime.of(endDate.getYear(), endDate.getMonth(), 1, 0, 0, 0, 0);
        LocalDateTime endDateCurrentMonth = endDate;

        // Tính toán startDate và endDate cho tháng trước
        LocalDateTime startDatePreviousMonth = startDateCurrentMonth.minusMonths(1);
        LocalDateTime endDatePreviousMonth = endDateCurrentMonth.minusMonths(1);

        // Lấy số lượng khách hàng của tháng hiện tại và tháng trước
        Long customerCountCurrentMonth = receiptDetailRepository.getCustomerCount(startDateCurrentMonth,
                endDateCurrentMonth);
        Long customerCountPreviousMonth = receiptDetailRepository.getCustomerCount(startDatePreviousMonth,
                endDatePreviousMonth);

        // Tính phần trăm thay đổi
        if (customerCountPreviousMonth == 0) {
            return customerCountCurrentMonth > 0 ? 100.0 : 0.0; // Tránh chia cho 0
        }
        double percentChange = ((double) customerCountCurrentMonth - customerCountPreviousMonth)
                / customerCountPreviousMonth;
        BigDecimal roundedPercentChange = new BigDecimal(percentChange * 100).setScale(2, RoundingMode.HALF_UP);
        return roundedPercentChange.doubleValue();
    }

    public List<Object[]> getRevenueByMonth(LocalDateTime endDate) {
        // Lấy tháng và năm từ endDate
        int year = endDate.getYear();

        // In ra tháng và năm
        System.err.println("year: " + year);

        // Gọi repository để lấy doanh thu theo tháng và năm
        List<Object[]> result = receiptDetailRepository.getRevenueByMonth(year);

        // In ra kết quả
        System.err.println("result: " + result);

        // Trả về kết quả dưới dạng List<Object[]>
        return result;
    }

    public Double getSuccessRate(LocalDateTime compareDate) {
        if (compareDate == null) {
            throw new IllegalArgumentException("The compareDate parameter cannot be null");
        }

        // Convert LocalDateTime to LocalDate (remove time portion)
        int month = compareDate.getMonthValue();
        int year = compareDate.getYear();
        LocalDateTime end = compareDate;

        // Calculate the start of the month (01/MM/YYYY)
        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
        // Lấy tổng số đơn hàng
        Long totalOrders = receiptDetailRepository.getTotalOrders(start, end);

        // Lấy số đơn hàng thành công
        Long successfulOrders = receiptDetailRepository.getSuccessfulOrders(start, end);

        // Tính tỷ lệ thành công theo phần trăm
        double successRate = 0.0;
        if (totalOrders != null && successfulOrders != null && totalOrders > 0) {
            successRate = (double) successfulOrders / totalOrders; // Tỷ lệ thành công (0.3, 0.7, v.v.)
        }

        // Chuyển tỷ lệ thành công thành phần trăm và làm tròn đến 2 chữ số thập phân
        BigDecimal percent = new BigDecimal(successRate).setScale(2, RoundingMode.HALF_UP); // Nhân với 100 để chuyển
                                                                                            // thành phần trăm
        return percent != null ? percent.doubleValue() : 0.0; // Trả về tỷ lệ phần trăm làm tròn hoặc 0 nếu null
    }

    // Phương thức lấy thông tin thống kê phương thức thanh toán
    public List<Object[]> getPaymentMethodStats(LocalDateTime compareDate) {
        if (compareDate == null) {
            throw new IllegalArgumentException("The compareDate parameter cannot be null");
        }

        // Convert LocalDateTime to LocalDate (remove time portion)
        int month = compareDate.getMonthValue();
        int year = compareDate.getYear();
        LocalDateTime end = compareDate;

        // Calculate the start of the month (01/MM/YYYY)
        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
        return receiptDetailRepository.getPaymentMethodStats(start, end);
    }

    public boolean checkReceiptDetailExist(int productId, int customerId) {
        return receiptDetailRepository.existsByProduct_IdAndReceipt_Customer_IdAndReceipt_ReceiptStatus_Id(productId, customerId,
                3);
    }
}
