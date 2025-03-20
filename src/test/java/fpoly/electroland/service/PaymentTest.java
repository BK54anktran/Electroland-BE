package fpoly.electroland.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import fpoly.electroland.model.Payment;
import fpoly.electroland.model.PaymentStatus;
import fpoly.electroland.model.PaymentType;
import fpoly.electroland.repository.PaymentRepository;

@ExtendWith(MockitoExtension.class)
public class PaymentTest {
    @Mock
    PaymentRepository paymentRepository;
    
    @InjectMocks 
    PaymentService paymentService;

    private Payment payment1;
    private Payment payment2;

    @BeforeEach
    void setUp(){
        PaymentStatus paymentStatus1 = new PaymentStatus(1,"Hoàn thành");
        PaymentStatus paymentStatus2 = new PaymentStatus(2, "Chưa hoàn thành");

        PaymentType paymentType1 = new PaymentType(1, "Tiền mặt", "");
        PaymentType paymentType2 = new PaymentType(2, "Chuyển khoản", "");

        payment1 = new Payment();
        payment1.setAmount(20000000.0);
        payment1.setContent("Thanh toán điện thoại iPhone 16e");
        payment1.setCreateTime(new Date());
        payment1.setId(1);
        payment1.setPaymentStatus(paymentStatus1);
        payment1.setPaymentType(paymentType1);
        payment1.setSuccessTime(new Date());

        payment2 = new Payment();
        payment2.setAmount(40000000.0);
        payment2.setContent("Thanh toán tiền");
        payment2.setCreateTime(new Date());
        payment2.setId(2);
        payment2.setPaymentStatus(paymentStatus2);
        payment2.setPaymentType(paymentType2);
        payment2.setSuccessTime(new Date());
    }

    @Test
    void createPayment(){
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment1);
        Payment payment = paymentService.create(payment1);
        assertEquals(payment1, payment, "Result should be payment1");
        assertNotNull(payment,"Payment should not be null");
        verify(paymentRepository, times(1)).save(payment1);
    }
}
