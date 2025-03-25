package fpoly.electroland.service;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import fpoly.electroland.dto.request.ReceiptDTO;
import lombok.RequiredArgsConstructor;

import java.awt.Color;

@Service
@RequiredArgsConstructor
public class PdfService {

    @Value("${pdf.directory}")
    private String pdfDir;
    
    // Màu sắc chủ đạo
    private static final Color PRIMARY_COLOR = new Color(0, 128, 128); // Teal
    private static final Color SECONDARY_COLOR = new Color(240, 240, 240); // Light Gray
    private static final Color TEXT_COLOR = new Color(50, 50, 50); // Dark Gray
    private static final Color ACCENT_COLOR = new Color(255, 153, 0); // Orange

    public String generatePdf(ReceiptDTO receipt) throws Exception {
        // Tạo thư mục nếu chưa có
        Files.createDirectories(Paths.get(pdfDir));
        String pdfPath = pdfDir + "/receipt_" + receipt.getId() + ".pdf";

        // Khởi tạo tài liệu PDF
        Document document = new Document(PageSize.A4, 36, 36, 54, 36); // Margins: left, right, top, bottom
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
        
        // Thêm page numbers
        writer.setPageEvent(new PdfPageNumbering());
        
        document.open();

        // Định dạng font
        Font titleFont = new Font(Font.HELVETICA, 20, Font.BOLD, PRIMARY_COLOR);
        Font subTitleFont = new Font(Font.HELVETICA, 14, Font.BOLD, PRIMARY_COLOR);
        Font normalFont = new Font(Font.HELVETICA, 10, Font.NORMAL, TEXT_COLOR);
        Font boldFont = new Font(Font.HELVETICA, 10, Font.BOLD, TEXT_COLOR);
        Font highlightFont = new Font(Font.HELVETICA, 11, Font.BOLD, ACCENT_COLOR);
        
        // Thêm logo và header
        addHeaderWithLogo(document);
        
        // Tiêu đề hóa đơn
        Paragraph title = new Paragraph("HÓA ĐƠN BÁN HÀNG", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(5);
        document.add(title);
        
        // Số hóa đơn
        Paragraph invoiceNumber = new Paragraph("Số: " + receipt.getId(), subTitleFont);
        invoiceNumber.setAlignment(Element.ALIGN_CENTER);
        invoiceNumber.setSpacingAfter(20);
        document.add(invoiceNumber);

        // Thông tin hóa đơn và khách hàng trong 2 cột
        PdfPTable infoTable = new PdfPTable(2);
        infoTable.setWidthPercentage(100);
        infoTable.setSpacingBefore(10);
        infoTable.setSpacingAfter(20);
        
        // Cột trái: Thông tin hóa đơn
        PdfPCell leftCell = new PdfPCell();
        leftCell.setBorder(Rectangle.NO_BORDER);
        leftCell.setPadding(5);
        
        Paragraph receiptInfo = new Paragraph("THÔNG TIN HÓA ĐƠN", subTitleFont);
        receiptInfo.setSpacingAfter(10);
        leftCell.addElement(receiptInfo);
        
        // Format date
        String formattedDate = receipt.getDate();
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = inputFormat.parse(receipt.getDate());
            formattedDate = outputFormat.format(date);
        } catch (Exception e) {
            // Keep original if parsing fails
        }
        
        addInfoLine(leftCell, "Mã hóa đơn:", receipt.getId(), boldFont, normalFont);
        addInfoLine(leftCell, "Ngày tạo:", formattedDate, boldFont, normalFont);
        addInfoLine(leftCell, "Phương thức thanh toán:", "Thanh toán khi nhận hàng", boldFont, normalFont);
        
        // Cột phải: Thông tin khách hàng
        PdfPCell rightCell = new PdfPCell();
        rightCell.setBorder(Rectangle.NO_BORDER);
        rightCell.setPadding(5);
        
        Paragraph customerInfo = new Paragraph("THÔNG TIN KHÁCH HÀNG", subTitleFont);
        customerInfo.setSpacingAfter(10);
        rightCell.addElement(customerInfo);
        
        addInfoLine(rightCell, "Tên khách hàng:", receipt.getCustomerName(), boldFont, normalFont);
        addInfoLine(rightCell, "Địa chỉ:", receipt.getCustomerAddress(), boldFont, normalFont);
        addInfoLine(rightCell, "Số điện thoại:", receipt.getCustomerPhone(), boldFont, normalFont);
        if (receipt.getEmail() != null && !receipt.getEmail().isEmpty()) {
            addInfoLine(rightCell, "Email:", receipt.getEmail(), boldFont, normalFont);
        }
        
        infoTable.addCell(leftCell);
        infoTable.addCell(rightCell);
        document.add(infoTable);

        // Danh sách sản phẩm
        Paragraph productListTitle = new Paragraph("CHI TIẾT SẢN PHẨM", subTitleFont);
        productListTitle.setSpacingAfter(10);
        document.add(productListTitle);
        
        PdfPTable productTable = new PdfPTable(5);
        productTable.setWidthPercentage(100);
        productTable.setSpacingBefore(5);
        productTable.setWidths(new float[]{0.5f, 3f, 0.8f, 1.2f, 1.5f});

        // Header với background color
        addStyledTableHeader(productTable, "STT");
        addStyledTableHeader(productTable, "Tên sản phẩm");
        addStyledTableHeader(productTable, "SL");
        addStyledTableHeader(productTable, "Đơn giá");
        addStyledTableHeader(productTable, "Thành tiền");

        // Dữ liệu sản phẩm với màu nền xen kẽ
        int index = 1;
        for (ReceiptDTO.Item item : receipt.getItems()) {
            Color rowColor = (index % 2 == 0) ? SECONDARY_COLOR : Color.WHITE;
            
            addStyledCell(productTable, String.valueOf(index++), normalFont, rowColor, Element.ALIGN_CENTER);
            addStyledCell(productTable, item.getName(), normalFont, rowColor, Element.ALIGN_LEFT);
            addStyledCell(productTable, String.valueOf(item.getQuantity()), normalFont, rowColor, Element.ALIGN_CENTER);
            addStyledCell(productTable, String.format("%,.0f VNĐ", item.getPrice()), normalFont, rowColor, Element.ALIGN_RIGHT);
            addStyledCell(productTable, String.format("%,.0f VNĐ", item.getQuantity() * item.getPrice()), boldFont, rowColor, Element.ALIGN_RIGHT);
        }
        document.add(productTable);

        // Tổng tiền, giảm giá, phí vận chuyển
        PdfPTable summaryTable = new PdfPTable(2);
        summaryTable.setWidthPercentage(50);
        summaryTable.setSpacingBefore(15);
        summaryTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
        summaryTable.setWidths(new float[]{1.5f, 1f});

        double totalPrice = receipt.totalPrice;
        double discount = receipt.getDiscount();
        double finalPrice = receipt.finalPrice;

        addSummaryRow(summaryTable, "Tổng tiền hàng:", String.format("%,.0f VNĐ", totalPrice), boldFont, normalFont);
        addSummaryRow(summaryTable, "Phí vận chuyển:", "0 VNĐ", boldFont, normalFont);
        addSummaryRow(summaryTable, "Giảm giá:", String.format("%,.0f VNĐ", discount), boldFont, normalFont);
        
        // Tổng thanh toán với highlight
        PdfPCell totalLabelCell = new PdfPCell(new Phrase("Tổng thanh toán:", highlightFont));
        totalLabelCell.setBorder(Rectangle.TOP);
        totalLabelCell.setPaddingTop(8);
        totalLabelCell.setBorderWidthTop(1);
        totalLabelCell.setBorderColorTop(PRIMARY_COLOR);
        
        PdfPCell totalValueCell = new PdfPCell(new Phrase(String.format("%,.0f VNĐ", finalPrice), highlightFont));
        totalValueCell.setBorder(Rectangle.TOP);
        totalValueCell.setPaddingTop(8);
        totalValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        totalValueCell.setBorderWidthTop(1);
        totalValueCell.setBorderColorTop(PRIMARY_COLOR);
        
        summaryTable.addCell(totalLabelCell);
        summaryTable.addCell(totalValueCell);
        
        document.add(summaryTable);
        
        // Thêm footer với thông tin liên hệ và cảm ơn
        addFooter(document);
        
        document.close();

        return pdfPath;
    }
    
    private void addHeaderWithLogo(Document document) throws Exception {
        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100);
        headerTable.setWidths(new float[]{1f, 2f});
        headerTable.setSpacingAfter(20);
        
        // Logo cell
        PdfPCell logoCell = new PdfPCell();
        logoCell.setBorder(Rectangle.NO_BORDER);
        
        // Thay đường dẫn logo thực tế của bạn
        // Image logo = Image.getInstance("path/to/your/logo.png");
        // logo.scaleToFit(100, 100);
        // logoCell.addElement(logo);
        
        // Hoặc sử dụng text thay thế nếu không có logo
        Font logoFont = new Font(Font.HELVETICA, 24, Font.BOLD, PRIMARY_COLOR);
        Paragraph logoText = new Paragraph("ElectroLand", logoFont);
        logoCell.addElement(logoText);
        
        // Company info cell
        PdfPCell infoCell = new PdfPCell();
        infoCell.setBorder(Rectangle.NO_BORDER);
        infoCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        
        Font companyInfoFont = new Font(Font.HELVETICA, 10, Font.NORMAL, TEXT_COLOR);
        
        Paragraph companyName = new Paragraph("CÔNG TY TNHH ELECTROLAND", new Font(Font.HELVETICA, 12, Font.BOLD, TEXT_COLOR));
        companyName.setAlignment(Element.ALIGN_RIGHT);
        infoCell.addElement(companyName);
        
        Paragraph address = new Paragraph("Địa chỉ: 123 Đường Công Nghệ, Quận 9, TP.HCM", companyInfoFont);
        address.setAlignment(Element.ALIGN_RIGHT);
        infoCell.addElement(address);
        
        Paragraph contact = new Paragraph("Hotline: 1900 1234 | Email: info@electroland.com", companyInfoFont);
        contact.setAlignment(Element.ALIGN_RIGHT);
        infoCell.addElement(contact);
        
        Paragraph website = new Paragraph("Website: www.electroland.com", companyInfoFont);
        website.setAlignment(Element.ALIGN_RIGHT);
        infoCell.addElement(website);
        
        headerTable.addCell(logoCell);
        headerTable.addCell(infoCell);
        
        document.add(headerTable);
    }
    
    private void addFooter(Document document) throws Exception {
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        
        PdfPTable footerTable = new PdfPTable(1);
        footerTable.setWidthPercentage(100);
        footerTable.setSpacingBefore(20);
        
        PdfPCell thankYouCell = new PdfPCell();
        thankYouCell.setBorder(Rectangle.NO_BORDER);
        thankYouCell.setPadding(5);
        
        Font footerFont = new Font(Font.HELVETICA, 10, Font.ITALIC, TEXT_COLOR);
        Font thankYouFont = new Font(Font.HELVETICA, 12, Font.BOLD, PRIMARY_COLOR);
        
        Paragraph thankYou = new Paragraph("Cảm ơn Quý khách đã mua hàng tại ElectroLand!", thankYouFont);
        thankYou.setAlignment(Element.ALIGN_CENTER);
        thankYouCell.addElement(thankYou);
        
        Paragraph policy = new Paragraph("Hàng đã mua không được đổi trả nếu không có lỗi từ nhà sản xuất.", footerFont);
        policy.setAlignment(Element.ALIGN_CENTER);
        thankYouCell.addElement(policy);
        
        Paragraph contact = new Paragraph("Mọi thắc mắc xin liên hệ: 1900 1234 hoặc email: support@electroland.com", footerFont);
        contact.setAlignment(Element.ALIGN_CENTER);
        thankYouCell.addElement(contact);
        
        footerTable.addCell(thankYouCell);
        document.add(footerTable);
    }

    private void addInfoLine(PdfPCell cell, String label, String value, Font labelFont, Font valueFont) {
        Paragraph paragraph = new Paragraph();
        paragraph.add(new Phrase(label + " ", labelFont));
        paragraph.add(new Phrase(value, valueFont));
        paragraph.setSpacingAfter(5);
        cell.addElement(paragraph);
    }
    
    private void addStyledTableHeader(PdfPTable table, String headerTitle) {
        Font headerFont = new Font(Font.HELVETICA, 10, Font.BOLD, Color.WHITE);
        PdfPCell header = new PdfPCell(new Phrase(headerTitle, headerFont));
        header.setBackgroundColor(PRIMARY_COLOR);
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        header.setVerticalAlignment(Element.ALIGN_MIDDLE);
        header.setPadding(8);
        table.addCell(header);
    }
    
    private void addStyledCell(PdfPTable table, String text, Font font, Color bgColor, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(bgColor);
        cell.setHorizontalAlignment(alignment);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(6);
        table.addCell(cell);
    }
    
    private void addSummaryRow(PdfPTable table, String label, String value, Font labelFont, Font valueFont) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
        labelCell.setBorder(Rectangle.NO_BORDER);
        labelCell.setPadding(5);
        
        PdfPCell valueCell = new PdfPCell(new Phrase(value, valueFont));
        valueCell.setBorder(Rectangle.NO_BORDER);
        valueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        valueCell.setPadding(5);
        
        table.addCell(labelCell);
        table.addCell(valueCell);
    }
    
    // Inner class for page numbering
    private static class PdfPageNumbering extends com.lowagie.text.pdf.PdfPageEventHelper {
        public void onEndPage(PdfWriter writer, Document document) {
            PdfPTable table = new PdfPTable(1);
            table.setTotalWidth(document.right() - document.left());
            
            Font pageFont = new Font(Font.HELVETICA, 8, Font.NORMAL, Color.GRAY);
            PdfPCell cell = new PdfPCell(new Phrase("Trang " + writer.getPageNumber(), pageFont));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);
            
            table.writeSelectedRows(0, -1, document.left(), document.bottom() - 10, writer.getDirectContent());
        }
    }
}