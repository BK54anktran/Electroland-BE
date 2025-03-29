package fpoly.electroland.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import fpoly.electroland.model.Supplier;
import fpoly.electroland.repository.SupplierRepository;
import fpoly.electroland.util.CreateAction;

@ExtendWith(MockitoExtension.class)
public class SupplierTest {
    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    CreateAction createAction;

    @InjectMocks
    private SupplierService supplierService;

    private Supplier supplier1;
    private Supplier supplier2;

    @BeforeEach
    public void setUp() {
        supplier1 = new Supplier(1, "Supplier A", "");
        supplier2 = new Supplier(2, "Supplier B", "");

        doNothing().when(createAction).createAction(anyString(), anyString(), anyInt(), any(), anyString(), any());
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

    @Test
    public void createSupplier_Success() {
        Supplier newSupplier = new Supplier(3, "Supplier C", "");
        when(supplierRepository.save(any(Supplier.class))).thenReturn(newSupplier);

        Supplier createdSupplier = supplierService.createSupplier(newSupplier, 1);

        assertNotNull(createdSupplier, "Created supplier should not be null");
        assertEquals("Supplier C", createdSupplier.getName(), "Supplier name should match");
        verify(supplierRepository, times(2)).save(any(Supplier.class));
        verify(createAction, times(1)).createAction(eq("Supplier"), eq("CREATE"), anyInt(), isNull(), anyString(), any());
    }

    @Test
    public void updateSupplier_Success() {
        Supplier existingSupplier = new Supplier(1, "Supplier A", "");
        Supplier updatedSupplier = new Supplier(1, "Supplier A Updated", "newLogo.png");

        when(supplierRepository.findById(anyLong())).thenReturn(Optional.of(existingSupplier));
        when(supplierRepository.save(any(Supplier.class))).thenReturn(updatedSupplier);

        Supplier result = supplierService.updateSupplier(1L, updatedSupplier, 1);

        assertNotNull(result, "Updated supplier should not be null");
        assertEquals("Supplier A Updated", result.getName(), "Supplier name should be updated");
        assertEquals("newLogo.png", result.getLogo(), "Supplier logo should be updated");
        verify(supplierRepository, times(1)).save(any(Supplier.class));
        verify(createAction, times(1)).createAction(eq("Supplier"), eq("UPDATE"), anyInt(), anyString(), anyString(), any());
    }

    @Test
    public void updateSupplier_NotFound() {
        when(supplierRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            supplierService.updateSupplier(1L, supplier1, 1);
        });

        assertEquals("Employee not found with id: 1", exception.getMessage(), "Exception message should match");
        verify(supplierRepository, times(0)).save(any(Supplier.class));
    }

}
