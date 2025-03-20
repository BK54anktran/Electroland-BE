package fpoly.electroland.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import fpoly.electroland.model.PaymentType;
import fpoly.electroland.repository.PaymentTypeRepository;

@ExtendWith(MockitoExtension.class)
public class PaymentTypeTest {
    @Mock
    PaymentTypeRepository paymentTypeRepository;
    
    @InjectMocks
    PaymentTypeService paymentTypeService;

    private PaymentType paymentType1;
    private PaymentType paymentType2;

    @BeforeEach
    void setUp(){
        paymentType1 = new PaymentType(1, "Tiền mặt", "");
        paymentType2 = new PaymentType(2, "Chuyển khoản", "");
    }

    @Test
    void getList(){
        when(paymentTypeRepository.findAll()).thenReturn(Arrays.asList(paymentType1, paymentType2));
        Object result = paymentTypeService.getList();
        assertNotNull(result, "The result should not be null");
        assertTrue(result instanceof List, "The result should be a List");
        List<?> paymentTypes = (List<?>) result;
        System.out.println(paymentTypes);
        assertEquals(2, paymentTypes.size(), "The size of the list should be 2");
        assertTrue(paymentTypes.stream().anyMatch(pt -> pt instanceof PaymentType && ((PaymentType) pt).getName().equals("Tiền mặt")),
            "The list should contain 'Tiền mặt' payment type");
        assertTrue(paymentTypes.stream().anyMatch(pt -> pt instanceof PaymentType && ((PaymentType) pt).getName().equals("Chuyển khoản")),
            "The list should contain 'Chuyển khoản' payment type");
        verify(paymentTypeRepository, times(1)).findAll();
    }

    @Test
    void getPaymentById(){
        when(paymentTypeRepository.findById(1)).thenReturn(Optional.of(paymentType1));
        Object result = paymentTypeService.getPaymentById(1);
        System.out.println(result);
        assertNotNull(result, "The result should not be null");
        assertTrue(result instanceof PaymentType, "The result should be a PaymentType");
        PaymentType paymentType = (PaymentType) result;
        assertEquals(1, paymentType.getId(), "The id of the payment type should be 1");
        assertEquals("Tiền mặt", paymentType.getName(), "The name of the payment type should be Tiền mặt");
        verify(paymentTypeRepository, times(1)).findById(1);
    }
}
