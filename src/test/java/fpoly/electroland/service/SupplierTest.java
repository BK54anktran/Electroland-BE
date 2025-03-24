package fpoly.electroland.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import fpoly.electroland.model.Supplier;
import fpoly.electroland.repository.SupplierRepository;

@ExtendWith(MockitoExtension.class)
public class SupplierTest {
    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private SupplierService supplierService;

    private Supplier supplier1;
    private Supplier supplier2;

    @BeforeEach
    public void setUp() {
        supplier1 = new Supplier(1, "Supplier A", "");
        supplier2 = new Supplier(2, "Supplier B", "");
    }

    @Test
    public void testGetAllSuppliers() {
        when(supplierRepository.findAll()).thenReturn(Arrays.asList(supplier1, supplier2));

        List<Supplier> suppliers = supplierService.getAllSuppliers();
        System.out.println(suppliers);
        assertNotNull(suppliers, "Supplier list should not be null");
        assertEquals(2, suppliers.size(), "Supplier list should contain 2 elements");
        assertEquals("Supplier A", suppliers.get(0).getName(), "First supplier should be Supplier A");
        assertEquals("Supplier B", suppliers.get(1).getName(), "Second supplier should be Supplier B");

        verify(supplierRepository, times(1)).findAll();
    }
}
