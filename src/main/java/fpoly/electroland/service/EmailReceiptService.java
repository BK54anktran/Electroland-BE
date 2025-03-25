package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import fpoly.electroland.dto.request.ReceiptDTO;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class EmailReceiptService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;


    public void sendEmailWithAttachment(ReceiptDTO receipt, String pdfPath) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(fromEmail);
        helper.setTo(receipt.getEmail());
        helper.setSubject("Cảm ơn bạn đã mua hàng tại ElectroLand - Hóa đơn #" + receipt.getId());

        String emailContent = createEmailContent(receipt);
        helper.setText(emailContent, true); // Set to true for HTML content

        FileSystemResource file = new FileSystemResource(new File(pdfPath));
        helper.addAttachment("HoaDon_" + receipt.getId() + ".pdf", file);

        mailSender.send(message);
    }

    private String createEmailContent(ReceiptDTO receipt) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String formattedDate = dateFormat.format(new Date());

        StringBuilder content = new StringBuilder();
        content.append("<html><body style='font-family: Arial, sans-serif; color: #333;'>");
        content.append("<div style='max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 5px;'>");
        content.append("<h1 style='color: #0066cc; text-align: center;'>Cảm ơn bạn đã mua hàng tại ElectroLand!</h1>");
        content.append("<p>Kính gửi ").append(receipt.getCustomerName()).append(",</p>");
        content.append("<p>Chúng tôi xin chân thành cảm ơn bạn đã lựa chọn ElectroLand. Đơn hàng của bạn đã được xử lý thành công.</p>");
        content.append("<h2 style='color: #0066cc;'>Chi tiết đơn hàng:</h2>");
        content.append("<ul>");
        content.append("<li><strong>Mã đơn hàng:</strong> #").append(receipt.getId()).append("</li>");
        content.append("<li><strong>Ngày đặt hàng:</strong> ").append(formattedDate).append("</li>");
        content.append("<li><strong>Tổng giá trị đơn hàng:</strong> ").append(String.format("%,.0f", receipt.getFinalPrice())).append(" VNĐ</li>");
        content.append("</ul>");
        content.append("<p>Bạn có thể tìm thấy hóa đơn chi tiết đính kèm trong email này.</p>");
        content.append("<h3 style='color: #0066cc;'>Các bước tiếp theo:</h3>");
        content.append("<ol>");
        content.append("<li>Kiểm tra kỹ thông tin trên hóa đơn.</li>");
        content.append("<li>Lưu giữ hóa đơn này để tham khảo trong tương lai.</li>");
        content.append("<li>Nếu bạn có bất kỳ câu hỏi nào, vui lòng liên hệ với chúng tôi.</li>");
        content.append("</ol>");
        content.append("<p>Chúng tôi sẽ thông báo cho bạn khi đơn hàng được gửi đi.</p>");
        content.append("<p style='background-color: #f0f0f0; padding: 10px; border-radius: 5px;'>");
        content.append("<strong>Cần hỗ trợ?</strong><br>");
        content.append("Gọi cho chúng tôi: <a href='tel:").append("1900 1508").append("' style='color: #0066cc;'>").append("</a><br>");
        content.append("Email: <a href='mailto:support@electroland.com' style='color: #0066cc;'>support@electroland.com</a>");
        content.append("</p>");
        content.append("<p style='text-align: center; margin-top: 20px;'>");
        content.append("<a href='").append("http://localhost:3000/").append("' style='background-color: #0066cc; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;'>Ghé thăm website của chúng tôi</a>");
        content.append("</p>");
        content.append("<p style='font-size: 12px; color: #666; text-align: center; margin-top: 20px;'>");
        content.append("Đây là email tự động, vui lòng không trả lời email này.<br>");
        content.append("© 2023 ElectroLand. Tất cả các quyền được bảo lưu.");
        content.append("</p>");
        content.append("</div></body></html>");

        return content.toString();
    }
}