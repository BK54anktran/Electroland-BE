package fpoly.electroland.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import fpoly.electroland.model.TypeCustomer;
import fpoly.electroland.repository.TypeCustomerRepository;

@ExtendWith(MockitoExtension.class)
public class TypeCustomerTest {
    @Mock
    TypeCustomerRepository typeCustomerRepository;
    
    @InjectMocks
    TypeCustomerService typeCustomerService;

    private TypeCustomer typeCustomer;
    
    @BeforeEach
    void setUp(){
        typeCustomer = new TypeCustomer();
        typeCustomer.setId(1);
        typeCustomer.setLevelPoint(1000);
        typeCustomer.setNameType("Kim cuong");
    }

    @Test
    void getTypeCustomerTest(){
        when(typeCustomerRepository.findById(1)).thenReturn(Optional.of(typeCustomer));
        TypeCustomer result = typeCustomerService.getTypeCustomer(1);
        System.out.println(result);
        assertEquals(result, typeCustomer, "Result should be typeCustomer");
        assertNotNull(result, "Result should not be null");
        verify(typeCustomerRepository, times(1)).findById(1);
    }
}
