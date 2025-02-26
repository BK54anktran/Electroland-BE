package fpoly.electroland.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class ExcelExportService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    
    /**
     * Export sales data by month range to Excel
     */
    public void exportSalesDataByMonthRange(List<Object[]> salesData, HttpServletResponse response) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sales Data By Month");
            
            // Set column widths
            sheet.setColumnWidth(0, 5000);
            sheet.setColumnWidth(1, 5000);
            
            // Create header row
            Row headerRow = sheet.createRow(0);
            CellStyle headerStyle = createHeaderStyle(workbook);
            
            Cell headerCell = headerRow.createCell(0);
            headerCell.setCellValue("Tháng");
            headerCell.setCellStyle(headerStyle);
            
            headerCell = headerRow.createCell(1);
            headerCell.setCellValue("Doanh số");
            headerCell.setCellStyle(headerStyle);
            
            // Create data rows
            CellStyle dataStyle = createDataStyle(workbook);
            int rowNum = 1;
            
            for (Object[] dataRow : salesData) {
                Row row = sheet.createRow(rowNum++);
                
                // Assuming the structure of Object[] is [month, product, sales]
                for (int i = 0; i < dataRow.length; i++) {
                    Cell cell = row.createCell(i);
                    if (dataRow[i] != null) {
                        cell.setCellValue(dataRow[i].toString());
                    } else {
                        cell.setCellValue("");
                    }
                    cell.setCellStyle(dataStyle);
                }
            }
            
            // Set response headers
            setExcelResponseHeaders(response, "sales_data_by_month.xlsx");
            
            // Write to response
            writeWorkbookToResponse(workbook, response);
        }
    }
    
    /**
     * Export sales data to Excel
     */
    public void exportSalesData(Map<String, Object> salesData, HttpServletResponse response) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sales Data");
            
            // Set column widths
            sheet.setColumnWidth(0, 5000);
            sheet.setColumnWidth(1, 5000);
            sheet.setColumnWidth(2, 3000);
            sheet.setColumnWidth(3, 5000);
            
            // Create header row
            Row headerRow = sheet.createRow(0);
            CellStyle headerStyle = createHeaderStyle(workbook);
            
            Cell headerCell = headerRow.createCell(0);
            headerCell.setCellValue("Chỉ số");
            headerCell.setCellStyle(headerStyle);
            
            headerCell = headerRow.createCell(1);
            headerCell.setCellValue("Giá trị");
            headerCell.setCellStyle(headerStyle);
            
            headerCell = headerRow.createCell(2);
            headerCell.setCellValue("Đơn vị");
            headerCell.setCellStyle(headerStyle);
            
            headerCell = headerRow.createCell(3);
            headerCell.setCellValue("% Thay đổi");
            headerCell.setCellStyle(headerStyle);
            
            // Create data rows
            CellStyle dataStyle = createDataStyle(workbook);
            int rowNum = 1;
            
            for (Map.Entry<String, Object> entry : salesData.entrySet()) {
                if (entry.getValue() instanceof Map) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> statistic = (Map<String, Object>) entry.getValue();
                    
                    Row row = sheet.createRow(rowNum++);
                    
                    Cell cell = row.createCell(0);
                    cell.setCellValue(statistic.get("title").toString());
                    cell.setCellStyle(dataStyle);
                    
                    cell = row.createCell(1);
                    cell.setCellValue(statistic.get("value").toString());
                    cell.setCellStyle(dataStyle);
                    
                    cell = row.createCell(2);
                    cell.setCellValue(statistic.get("suffix").toString());
                    cell.setCellStyle(dataStyle);
                    
                    cell = row.createCell(3);
                    Double percentChange = (Double) statistic.get("percentChange");
                    cell.setCellValue(percentChange != null ? percentChange + "%" : "N/A");
                    cell.setCellStyle(dataStyle);
                }
            }
            
            // Set response headers
            setExcelResponseHeaders(response, "sales_data.xlsx");
            
            // Write to response
            writeWorkbookToResponse(workbook, response);
        }
    }
    
    /**
     * Export revenue by month to Excel
     */
    public void exportRevenueByMonth(List<Object[]> revenueData, HttpServletResponse response) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Revenue By Month");
            
            // Set column widths
            sheet.setColumnWidth(0, 5000);
            sheet.setColumnWidth(1, 5000);
            
            // Create header row
            Row headerRow = sheet.createRow(0);
            CellStyle headerStyle = createHeaderStyle(workbook);
            
            Cell headerCell = headerRow.createCell(0);
            headerCell.setCellValue("Tháng");
            headerCell.setCellStyle(headerStyle);
            
            headerCell = headerRow.createCell(1);
            headerCell.setCellValue("Doanh thu");
            headerCell.setCellStyle(headerStyle);
            
            // Create data rows
            CellStyle dataStyle = createDataStyle(workbook);
            int rowNum = 1;
            
            for (Object[] dataRow : revenueData) {
                Row row = sheet.createRow(rowNum++);
                
                // Assuming the structure of Object[] is [month, revenue]
                for (int i = 0; i < dataRow.length; i++) {
                    Cell cell = row.createCell(i);
                    if (dataRow[i] != null) {
                        cell.setCellValue(dataRow[i].toString());
                    } else {
                        cell.setCellValue("");
                    }
                    cell.setCellStyle(dataStyle);
                }
            }
            
            // Set response headers
            setExcelResponseHeaders(response, "revenue_by_month.xlsx");
            
            // Write to response
            writeWorkbookToResponse(workbook, response);
        }
    }
    
    /**
     * Export success rate to Excel
     */
    public void exportSuccessRate(Double successRate, LocalDateTime date, HttpServletResponse response) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Success Rate");
            
            // Set column widths
            sheet.setColumnWidth(0, 5000);
            sheet.setColumnWidth(1, 5000);
            
            // Create title row
            Row titleRow = sheet.createRow(0);
            CellStyle titleStyle = createTitleStyle(workbook);
            
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("Tỉ lệ đơn hàng thành công");
            titleCell.setCellStyle(titleStyle);
            
            // Merge cells for title
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
            
            // Create date row
            Row dateRow = sheet.createRow(1);
            CellStyle dateStyle = createDataStyle(workbook);
            
            Cell dateCell = dateRow.createCell(0);
            dateCell.setCellValue("Ngày:");
            dateCell.setCellStyle(dateStyle);
            
            dateCell = dateRow.createCell(1);
            dateCell.setCellValue(date.format(DATE_FORMATTER));
            dateCell.setCellStyle(dateStyle);
            
            // Create data row
            Row dataRow = sheet.createRow(3);
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle dataStyle = createDataStyle(workbook);
            
            Cell headerCell = dataRow.createCell(0);
            headerCell.setCellValue("Chỉ số");
            headerCell.setCellStyle(headerStyle);
            
            headerCell = dataRow.createCell(1);
            headerCell.setCellValue("Giá trị");
            headerCell.setCellStyle(headerStyle);
            
            Row valueRow = sheet.createRow(4);
            
            Cell valueCell = valueRow.createCell(0);
            valueCell.setCellValue("Tỉ lệ thành công");
            valueCell.setCellStyle(dataStyle);
            
            valueCell = valueRow.createCell(1);
            valueCell.setCellValue(successRate != null ? successRate + "%" : "N/A");
            valueCell.setCellStyle(dataStyle);
            
            // Set response headers
            setExcelResponseHeaders(response, "success_rate.xlsx");
            
            // Write to response
            writeWorkbookToResponse(workbook, response);
        }
    }
    
    /**
     * Export payment method stats to Excel
     */
    public void exportPaymentMethodStats(List<Object[]> paymentStats, HttpServletResponse response) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Payment Method Stats");
            
            // Set column widths
            sheet.setColumnWidth(0, 5000);
            sheet.setColumnWidth(1, 5000);
            sheet.setColumnWidth(2, 5000);
            
            // Create header row
            Row headerRow = sheet.createRow(0);
            CellStyle headerStyle = createHeaderStyle(workbook);
            
            Cell headerCell = headerRow.createCell(0);
            headerCell.setCellValue("Phương thức thanh toán");
            headerCell.setCellStyle(headerStyle);
            
            headerCell = headerRow.createCell(1);
            headerCell.setCellValue("Số lượng");
            headerCell.setCellStyle(headerStyle);
            
            // Create data rows
            CellStyle dataStyle = createDataStyle(workbook);
            int rowNum = 1;
            
            for (Object[] dataRow : paymentStats) {
                Row row = sheet.createRow(rowNum++);
                
                // Assuming the structure of Object[] is [payment_method, count, percentage]
                for (int i = 0; i < dataRow.length; i++) {
                    Cell cell = row.createCell(i);
                    if (dataRow[i] != null) {
                        if (i == 2 && dataRow[i] instanceof Number) {
                            // Format percentage
                            cell.setCellValue(dataRow[i] + "%");
                        } else {
                            cell.setCellValue(dataRow[i].toString());
                        }
                    } else {
                        cell.setCellValue("");
                    }
                    cell.setCellStyle(dataStyle);
                }
            }
            
            // Set response headers
            setExcelResponseHeaders(response, "payment_method_stats.xlsx");
            
            // Write to response
            writeWorkbookToResponse(workbook, response);
        }
    }
    
    /**
     * Export all dashboard data to a single Excel file with multiple sheets
     */
    public void exportAllDashboardData(
            Map<String, Object> salesData,
            List<Object[]> revenueByMonth,
            Double successRate,
            List<Object[]> paymentStats,
            List<Object[]> salesDataByMonth,
            LocalDateTime date,
            HttpServletResponse response) throws IOException {
        
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            // Create sheets for each data type
            createSalesDataSheet(workbook, salesData);
            createRevenueByMonthSheet(workbook, revenueByMonth);
            createSuccessRateSheet(workbook, successRate, date);
            createPaymentMethodStatsSheet(workbook, paymentStats);
            createSalesDataByMonthSheet(workbook, salesDataByMonth);
            
            // Set response headers
            setExcelResponseHeaders(response, "dashboard_data_" + date.format(DATE_FORMATTER) + ".xlsx");
            
            // Write to response
            writeWorkbookToResponse(workbook, response);
        }
    }
    
    // Helper methods for creating individual sheets in the all-data export
    
    private void createSalesDataSheet(XSSFWorkbook workbook, Map<String, Object> salesData) {
        Sheet sheet = workbook.createSheet("Tổng quan doanh số");
        
        // Set column widths
        sheet.setColumnWidth(0, 5000);
        sheet.setColumnWidth(1, 5000);
        sheet.setColumnWidth(2, 3000);
        sheet.setColumnWidth(3, 5000);
        
        // Create header row
        Row headerRow = sheet.createRow(0);
        CellStyle headerStyle = createHeaderStyle(workbook);
        
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("Chỉ số");
        headerCell.setCellStyle(headerStyle);
        
        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("Giá trị");
        headerCell.setCellStyle(headerStyle);
        
        headerCell = headerRow.createCell(2);
        headerCell.setCellValue("Đơn vị");
        headerCell.setCellStyle(headerStyle);
        
        headerCell = headerRow.createCell(3);
        headerCell.setCellValue("% Thay đổi");
        headerCell.setCellStyle(headerStyle);
        
        // Create data rows
        CellStyle dataStyle = createDataStyle(workbook);
        int rowNum = 1;
        
        for (Map.Entry<String, Object> entry : salesData.entrySet()) {
            if (entry.getValue() instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> statistic = (Map<String, Object>) entry.getValue();
                
                Row row = sheet.createRow(rowNum++);
                
                Cell cell = row.createCell(0);
                cell.setCellValue(statistic.get("title").toString());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(1);
                cell.setCellValue(statistic.get("value").toString());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(2);
                cell.setCellValue(statistic.get("suffix").toString());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(3);
                Double percentChange = (Double) statistic.get("percentChange");
                cell.setCellValue(percentChange != null ? percentChange + "%" : "N/A");
                cell.setCellStyle(dataStyle);
            }
        }
    }
    
    private void createRevenueByMonthSheet(XSSFWorkbook workbook, List<Object[]> revenueData) {
        Sheet sheet = workbook.createSheet("Doanh thu theo tháng");
        
        // Set column widths
        sheet.setColumnWidth(0, 5000);
        sheet.setColumnWidth(1, 5000);
        
        // Create header row
        Row headerRow = sheet.createRow(0);
        CellStyle headerStyle = createHeaderStyle(workbook);
        
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("Tháng");
        headerCell.setCellStyle(headerStyle);
        
        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("Doanh thu");
        headerCell.setCellStyle(headerStyle);
        
        // Create data rows
        CellStyle dataStyle = createDataStyle(workbook);
        int rowNum = 1;
        
        for (Object[] dataRow : revenueData) {
            Row row = sheet.createRow(rowNum++);
            
            for (int i = 0; i < dataRow.length; i++) {
                Cell cell = row.createCell(i);
                if (dataRow[i] != null) {
                    cell.setCellValue(dataRow[i].toString());
                } else {
                    cell.setCellValue("");
                }
                cell.setCellStyle(dataStyle);
            }
        }
    }
    
    private void createSuccessRateSheet(XSSFWorkbook workbook, Double successRate, LocalDateTime date) {
        Sheet sheet = workbook.createSheet("Tỉ lệ thành công");
        
        // Set column widths
        sheet.setColumnWidth(0, 5000);
        sheet.setColumnWidth(1, 5000);
        
        // Create title row
        Row titleRow = sheet.createRow(0);
        CellStyle titleStyle = createTitleStyle(workbook);
        
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Tỉ lệ đơn hàng thành công");
        titleCell.setCellStyle(titleStyle);
        
        // Merge cells for title
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
        
        // Create date row
        Row dateRow = sheet.createRow(1);
        CellStyle dateStyle = createDataStyle(workbook);
        
        Cell dateCell = dateRow.createCell(0);
        dateCell.setCellValue("Ngày:");
        dateCell.setCellStyle(dateStyle);
        
        dateCell = dateRow.createCell(1);
        dateCell.setCellValue(date.format(DATE_FORMATTER));
        dateCell.setCellStyle(dateStyle);
        
        // Create data row
        Row dataRow = sheet.createRow(3);
        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle dataStyle = createDataStyle(workbook);
        
        Cell headerCell = dataRow.createCell(0);
        headerCell.setCellValue("Chỉ số");
        headerCell.setCellStyle(headerStyle);
        
        headerCell = dataRow.createCell(1);
        headerCell.setCellValue("Giá trị");
        headerCell.setCellStyle(headerStyle);
        
        Row valueRow = sheet.createRow(4);
        
        Cell valueCell = valueRow.createCell(0);
        valueCell.setCellValue("Tỉ lệ thành công");
        valueCell.setCellStyle(dataStyle);
        
        valueCell = valueRow.createCell(1);
        valueCell.setCellValue(successRate != null ? successRate + "%" : "N/A");
        valueCell.setCellStyle(dataStyle);
    }
    
    private void createPaymentMethodStatsSheet(XSSFWorkbook workbook, List<Object[]> paymentStats) {
        Sheet sheet = workbook.createSheet("Phương thức thanh toán");
        
        // Set column widths
        sheet.setColumnWidth(0, 5000);
        sheet.setColumnWidth(1, 5000);
        sheet.setColumnWidth(2, 5000);
        
        // Create header row
        Row headerRow = sheet.createRow(0);
        CellStyle headerStyle = createHeaderStyle(workbook);
        
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("Phương thức thanh toán");
        headerCell.setCellStyle(headerStyle);
        
        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("Số lượng");
        headerCell.setCellStyle(headerStyle);
        
        // Create data rows
        CellStyle dataStyle = createDataStyle(workbook);
        int rowNum = 1;
        
        for (Object[] dataRow : paymentStats) {
            Row row = sheet.createRow(rowNum++);
            
            for (int i = 0; i < dataRow.length; i++) {
                Cell cell = row.createCell(i);
                if (dataRow[i] != null) {
                    if (i == 2 && dataRow[i] instanceof Number) {
                        // Format percentage
                        cell.setCellValue(dataRow[i] + "%");
                    } else {
                        cell.setCellValue(dataRow[i].toString());
                    }
                } else {
                    cell.setCellValue("");
                }
                cell.setCellStyle(dataStyle);
            }
        }
    }
    
    private void createSalesDataByMonthSheet(XSSFWorkbook workbook, List<Object[]> salesData) {
        Sheet sheet = workbook.createSheet("Doanh số theo tháng");
        
        // Set column widths
        sheet.setColumnWidth(0, 5000);
        sheet.setColumnWidth(1, 5000);
        sheet.setColumnWidth(2, 5000);
        
        // Create header row
        Row headerRow = sheet.createRow(0);
        CellStyle headerStyle = createHeaderStyle(workbook);
        
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("Tên sản phẩm");
        headerCell.setCellStyle(headerStyle);
        
        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("Số lượng bán");
        headerCell.setCellStyle(headerStyle);
    
        
        // Create data rows
        CellStyle dataStyle = createDataStyle(workbook);
        int rowNum = 1;
        
        for (Object[] dataRow : salesData) {
            Row row = sheet.createRow(rowNum++);
            
            // Assuming the structure of Object[] is [month, product, sales]
            for (int i = 0; i < dataRow.length; i++) {
                Cell cell = row.createCell(i);
                if (dataRow[i] != null) {
                    cell.setCellValue(dataRow[i].toString());
                } else {
                    cell.setCellValue("");
                }
                cell.setCellStyle(dataStyle);
            }
        }
    }
    
    // Helper methods for styling
    
    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        
        // Background color
        style.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        // Border
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        
        // Font
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        
        // Alignment
        style.setAlignment(HorizontalAlignment.CENTER);
        
        return style;
    }
    
    private CellStyle createDataStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        
        // Border
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        
        // Font
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 11);
        style.setFont(font);
        
        return style;
    }
    
    private CellStyle createTitleStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        
        // Font
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 14);
        font.setBold(true);
        style.setFont(font);
        
        // Alignment
        style.setAlignment(HorizontalAlignment.CENTER);
        
        return style;
    }
    
    // Helper methods for response handling
    
    private void setExcelResponseHeaders(HttpServletResponse response, String fileName) {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
    }
    
    private void writeWorkbookToResponse(XSSFWorkbook workbook, HttpServletResponse response) throws IOException {
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
            outputStream.flush();
        }
    }
}

