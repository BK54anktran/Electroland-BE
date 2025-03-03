package fpoly.electroland.service;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.Mail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailerService {
    private final JavaMailSender sender;
    private final RedisTemplate<String, String> redisTemplate;

    // Inject JavaMailSender và RedisTemplate thông qua constructor
    @Autowired
    public MailerService(JavaMailSender sender, RedisTemplate<String, String> redisTemplate) {
        this.sender = sender;
        this.redisTemplate = redisTemplate;
    }

    public void send(String to, String subject, String body)
            throws MessagingException {
        this.send(new Mail(to, subject, body));
    }

    public void send(Mail mail) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        helper.setFrom(mail.getFrom());
        helper.setTo(mail.getTo());
        helper.setSubject(mail.getSubject());
        helper.setText(mail.getBody(), true);
        helper.setReplyTo(mail.getFrom());
        for (String email : mail.getCc())
            helper.addCc(email);
        for (String email : mail.getBcc())
            helper.addBcc(email);
        for (File file : mail.getFiles())
            helper.addAttachment(file.getName(), file);
        sender.send(message);
    }

    // Gửi OTP qua email
    public Object sendOtpEmail(String email) throws MessagingException {
        int otp = 100000 + (int) (Math.random() * 900000);
        Mail mailOtp = new Mail(email, "Mã OTP xác nhận thay đổi mật khẩu", "Mã OTP của bạn là: " + otp);
        this.send(mailOtp);
        redisTemplate.opsForValue().set("otp:" + email, otp + "", 5, TimeUnit.MINUTES);
        return "Otp đã được gửi thành công";
    }
}