package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeePasswordService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendEmployeePasswordEmail(String toEmail, String employeeName, String password) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(fromEmail);
        helper.setTo(toEmail);
        helper.setSubject("Thông báo mật khẩu mới từ ElectroLand");

        String emailContent = createEmailContent(employeeName, password);
        helper.setText(emailContent, true); // Set to true for HTML content

        mailSender.send(message);
    }

    private String createEmailContent(String employeeName, String password) {
        StringBuilder content = new StringBuilder();
        content.append("<!DOCTYPE html>");
        content.append("<html>");
        content.append("<head>");
        content.append("<meta charset='UTF-8'>");
        content.append("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        content.append("<title>Mật khẩu mới từ ElectroLand</title>");
        content.append("</head>");
        content.append("<body style='margin: 0; padding: 0; font-family: Arial, sans-serif; color: #333; background-color: #f9f9f9;'>");
        
        // Main container
        content.append("<div style='max-width: 600px; margin: 0 auto; background-color: #ffffff;'>");
        
        // Header
        content.append("<div style='background-color: #0066cc; padding: 20px; text-align: center;'>");
        content.append("<h1 style='color: #ffffff; margin: 0; font-size: 24px; font-weight: normal;'>ElectroLand</h1>");
        content.append("<p style='color: #ffffff; margin: 5px 0 0; font-size: 16px;'>Thông báo mật khẩu mới</p>");
        content.append("</div>");
        
        // Content
        content.append("<div style='padding: 20px 30px;'>");
        content.append("<p style='font-size: 14px; line-height: 1.5;'>Kính gửi <strong style='color: #800080;'>").append(employeeName).append("</strong>,</p>");
        
        content.append("<p style='font-size: 14px; line-height: 1.5;'>Chào mừng bạn đến với ElectroLand! Chúng tôi rất vui mừng được hợp tác cùng bạn. Dưới đây là thông tin đăng nhập của bạn:</p>");
        
        // Password box
        content.append("<div style='margin: 20px 0;'>");
        content.append("<div style='border-left: 4px solid #0066cc; padding-left: 10px;'>");
        content.append("<h2 style='color: #0066cc; margin: 0; font-size: 16px; font-weight: bold;'>Mật khẩu mới của bạn</h2>");
        content.append("</div>");
        content.append("<div style='border: 1px dashed #0066cc; padding: 15px; text-align: center; margin-top: 10px;'>");
        content.append("<p style='font-family: monospace; font-size: 20px; margin: 0; color: #0066cc; font-weight: bold;'>").append(password).append("</p>");
        content.append("</div>");
        content.append("</div>");
        
        // Next steps
        content.append("<div style='margin: 20px 0;'>");
        content.append("<h3 style='color: #0066cc; font-size: 16px; margin-bottom: 10px;'>Các bước tiếp theo:</h3>");
        content.append("<ul style='padding-left: 20px; margin: 0;'>");
        content.append("<li style='margin-bottom: 8px; line-height: 1.5;'><strong>Đăng nhập:</strong> Sử dụng mật khẩu trên để đăng nhập vào hệ thống ElectroLand.</li>");
        content.append("<li style='margin-bottom: 8px; line-height: 1.5;'><strong>Đổi mật khẩu:</strong> Vì lý do bảo mật, vui lòng đổi mật khẩu ngay sau khi đăng nhập lần đầu.</li>");
        content.append("<li style='margin-bottom: 8px; line-height: 1.5;'><strong>Cập nhật thông tin:</strong> Kiểm tra và cập nhật thông tin cá nhân của bạn trong hệ thống.</li>");
        content.append("</ul>");
        content.append("</div>");
        
        // Security note
        content.append("<div style='background-color: #fff8e6; border-left: 4px solid #ffc107; padding: 10px; margin: 20px 0;'>");
        content.append("<p style='margin: 0; font-size: 14px; line-height: 1.5;'><strong>Lưu ý bảo mật:</strong> Không chia sẻ mật khẩu này với bất kỳ ai. ElectroLand sẽ không bao giờ yêu cầu mật khẩu của bạn qua email hoặc điện thoại.</p>");
        content.append("</div>");
        
        content.append("<p style='font-size: 14px; line-height: 1.5;'>Chúng tôi rất mong bạn sẽ có một trải nghiệm tuyệt vời tại ElectroLand. Nếu bạn cần hỗ trợ, đừng ngần ngại liên hệ với chúng tôi.</p>");
        
        content.append("<p style='font-size: 14px; line-height: 1.5;'>Trân trọng,<br><strong>Đội ngũ ElectroLand</strong></p>");
        content.append("</div>");
        
        // Support section
        content.append("<div style='background-color: #f5f5f5; padding: 20px 30px; border-top: 1px solid #eeeeee;'>");
        content.append("<h3 style='color: #333; font-size: 16px; margin: 0 0 15px;'>Cần hỗ trợ?</h3>");
        
        // Hotline
        content.append("<div style='display: flex; margin-bottom: 15px; align-items: center;'>");
        content.append("<div style='margin-right: 10px;'>");
        content.append("<div style='width: 30px; height: 30px; background-color: #0066cc; border-radius: 50%; display: flex; align-items: center; justify-content: center;'>");
        content.append("<span style='color: white; font-size: 14px;'>📞</span>");
        content.append("</div>");
        content.append("</div>");
        content.append("<div>");
        content.append("<p style='margin: 0; font-size: 14px;'><strong>Hotline</strong><br>");
        content.append("<a href='tel:19001508' style='color: #0066cc; text-decoration: none;'>1900 1508</a></p>");
        content.append("</div>");
        content.append("</div>");
        
        // Email support
        content.append("<div style='display: flex; align-items: center;'>");
        content.append("<div style='margin-right: 10px;'>");
        content.append("<div style='width: 30px; height: 30px; background-color: #0066cc; border-radius: 50%; display: flex; align-items: center; justify-content: center;'>");
        content.append("<span style='color: white; font-size: 14px;'>✉️</span>");
        content.append("</div>");
        content.append("</div>");
        content.append("<div>");
        content.append("<p style='margin: 0; font-size: 14px;'><strong>Email hỗ trợ</strong><br>");
        content.append("<a href='mailto:support@electroland.com' style='color: #0066cc; text-decoration: none;'>support@electroland.com</a></p>");
        content.append("</div>");
        content.append("</div>");
        content.append("</div>");
        
        // Footer
        content.append("<div style='padding: 15px; text-align: center; color: #666;'>");
        content.append("<p style='margin: 0; font-size: 12px;'>***</p>");
        content.append("</div>");
        
        content.append("</div>");
        content.append("</body>");
        content.append("</html>");

        return content.toString();
    }
}