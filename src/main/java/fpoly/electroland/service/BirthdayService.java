package fpoly.electroland.service;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.Customer;
import fpoly.electroland.model.ReceiptCoupon;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class BirthdayService {
    
    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerCouponService customerCouponService;

    @Autowired
    private JavaMailSender mailSender;

    @Scheduled(cron = "0 54 17 * * ?") // Chạy vào mỗi ngày lúc 9h sáng
    public void checkBirthdaysAndSendEmail() throws MessagingException, IOException {
        List<Customer> customers = customerService.getAll();
        LocalDate today = LocalDate.now();

        for (Customer customer : customers) {
            if (isBirthday(customer, today)) {
                // Gửi email chúc mừng sinh nhật
                sendBirthdayEmail(customer);

                // Tạo và gán ReceiptCoupon
                generateAndAssignCoupon(customer);
            }
        }
    }
    private boolean isBirthday(Customer customer, LocalDate today) {
        // Kiểm tra nếu ngày sinh của khách hàng trùng với ngày hôm nay
        if (customer.getDateOfBirth() == null) {
            return false;
        }
    
        // Chuyển đổi từ java.sql.Date sang LocalDate (không cần dùng toInstant())
        java.util.Date customerBirthdaySql = customer.getDateOfBirth();
        LocalDate customerBirthday = ((Date) customerBirthdaySql).toLocalDate();  // Sử dụng toLocalDate() trực tiếp
    
        // Kiểm tra ngày sinh trùng với ngày hôm nay
        return customerBirthday.getMonth() == today.getMonth() && customerBirthday.getDayOfMonth() == today.getDayOfMonth();
    }
    
    
    private void sendBirthdayEmail(Customer customer) throws MessagingException, IOException {
        // Tạo MimeMessage để gửi email HTML
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);  // true cho phép đính kèm file và hình ảnh
    
        // Địa chỉ email người nhận và tiêu đề
        helper.setTo(customer.getEmail());
        helper.setSubject("Chúc Mừng Sinh Nhật!");
    
        // Nội dung email với HTML, bao gồm hình ảnh và nút
        String emailContent = "<html><body>" +
                "<h1>Kính gửi " + customer.getFullName() + ",</h1>" +
                "<p>Chúc mừng sinh nhật! Chúc bạn có một ngày tuyệt vời!</p>" +
                "<p>Đây là voucher sinh nhật đặc biệt dành cho bạn:</p>" +
                "<ul>" +
                "<li><strong>Voucher giảm giá:</strong> 10.0 VND</li>" +
                "<li><strong>Giảm giá phần trăm:</strong> 10%</li>" +
                "<li><strong>Giảm tối đa:</strong> 50.0 VND</li>" +
                "<li><strong>Áp dụng cho hóa đơn tối thiểu:</strong> 100.0 VND</li>" +
                "</ul>" +
                "<p><strong>Mô tả:</strong> Chúc mừng sinh nhật! Đây là món quà đặc biệt dành cho bạn.</p>" +
                // Đặt kích thước của hình ảnh bằng thuộc tính width và height
                "<p><img src='cid:birthdayImage' alt='Bánh sinh nhật' width='300' height='200' /></p>" +  // Thêm hình ảnh và điều chỉnh kích thước
                "<p>Trân trọng,<br>Electroland</p>" +
                // Thêm nút liên kết
                "<p><a href='http://localhost:3000/' style='display:inline-block; padding:10px 20px; background-color:#007bff; color:white; text-decoration:none; border-radius:5px;'>Ghé thăm Website của chúng tôi</a></p>" +
                "</body></html>";
    
        // Set nội dung email là HTML
        helper.setText(emailContent, true);
    
        // Đính kèm hình ảnh vào email (sử dụng cid để nhúng ảnh vào email)

      File birthdayImage = new ClassPathResource("static/images/birthday.png").getFile();
        helper.addInline("birthdayImage", birthdayImage);
    
        // Gửi email
        mailSender.send(message);
    }
    
    
    

    private void generateAndAssignCoupon(Customer customer) {
        // Log để kiểm tra xem phương thức có được gọi không
        System.out.println("Generating coupon for customer: " + customer.getFullName());
    
        // Tạo một ReceiptCoupon mới và gán các giá trị
        ReceiptCoupon coupon = new ReceiptCoupon();
        coupon.setDiscountMoney(10.0); // Ví dụ gán giảm giá cố định
        coupon.setDiscountPercent(0.10);
        coupon.setMaxDiscount(50.0);
        coupon.setMinReceiptPrice(100.0);
        coupon.setDescription("Chúc mừng sinh nhật! Đây là món quà đặc biệt dành cho bạn.");
        coupon.setStatus(true); // Đảm bảo coupon còn hiệu lực
    
        // Lưu coupon
        ReceiptCoupon savedCoupon = customerCouponService.createReceiptCoupon(coupon);
    
        // Log sau khi lưu coupon
        System.out.println("Coupon saved: " + savedCoupon);
    
        // Tạo CustomerCoupon gắn coupon cho khách hàng
        customerCouponService.addReceiptCouponToCustomer(customer.getId(), savedCoupon);
    }
    
}
