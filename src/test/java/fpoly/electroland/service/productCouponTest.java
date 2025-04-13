package fpoly.electroland.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import fpoly.electroland.model.Employee;
import fpoly.electroland.model.ProductCoupon;
import fpoly.electroland.repository.EmployeeRepository;
import fpoly.electroland.repository.ProductCouponRepository;
import fpoly.electroland.util.CreateAction;

@ExtendWith(MockitoExtension.class)
public class productCouponTest {
    @Mock
    ProductCouponRepository productCouponRepository;

    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    CreateAction createAction;

    @InjectMocks
    ProductCouponService productCouponService;

    private ProductCoupon existingProductCoupon;
    private ProductCoupon updateProductCoupon;
    private Employee employee;

    @BeforeEach
    void setUp() {
        existingProductCoupon = new ProductCoupon();
        existingProductCoupon.setId(1);
        existingProductCoupon.setPoint(100);
        existingProductCoupon.setDescription("Old description");
        existingProductCoupon.setValue(500.0);
        existingProductCoupon.setStatus(true);  // Initial status

        updateProductCoupon = new ProductCoupon();
        updateProductCoupon.setPoint(150);
        updateProductCoupon.setDescription("Updated description");
        updateProductCoupon.setValue(700.0);

        employee = new Employee();
        employee.setId(1);
        employee.setFullName("John Doe");

        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee)); 

        when(productCouponRepository.findById(1L)).thenReturn(Optional.of(existingProductCoupon)); 

        when(productCouponRepository.save(any(ProductCoupon.class))).thenReturn(existingProductCoupon);
    }


    @Test
    void getListSuccess(){
        System.out.println("Get list success");
        List<ProductCoupon> list = new ArrayList<>();
        list.add(new ProductCoupon());
        when(productCouponService.getList()).thenReturn(list);
        Object result = productCouponService.getList();
        assertNotNull(result, "The product list should not be null");
    }
    
    @Test
    void newProductCouponSuccess() {
        System.out.println("New product coupon success");

        ProductCoupon productCoupon = new ProductCoupon();
        Employee employee = new Employee();
        employee.setId(1);  // Giả lập ID cho employee (Sử dụng Integer)

        // Giả lập việc tìm thấy employee với ID 1
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));  // Sử dụng Integer ở đây

        // Giả lập việc lưu productCoupon
        when(productCouponRepository.save(any())).thenReturn(productCoupon);

        // Gọi phương thức newProductCoupon với ID của employee
        ProductCoupon result = productCouponService.newProductCoupon(productCoupon, employee.getId());

        assertNotNull(result, "The product coupon should not be null");
    }

    // @Test
    // void updateProductCouponSuccess() {
    //     Integer integerId = 1;  // Integer ID
    //     Long longId = Long.valueOf(integerId);  // Chuyển Integer thành Long

    //     // Giả lập việc tìm thấy ProductCoupon với Long ID
    //     when(productCouponRepository.findById(longId)).thenReturn(Optional.of(existingProductCoupon));  // ID là Long trong mock
    //     when(productCouponRepository.save(any(ProductCoupon.class))).thenReturn(updateProductCoupon);
    //     when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

    //     // Gọi phương thức updateProductCoupon với Integer ID
    //     ProductCoupon result = productCouponService.updateProductCoupon(longId, updateProductCoupon, 1);  // ID là Long trong service

    //     // Kiểm tra kết quả
    //     assertNotNull(result, "Updated product coupon should not be null");
    //     assertEquals(updateProductCoupon.getRedemptionCost(), result.getRedemptionCost());
    //     assertEquals(updateProductCoupon.getDescription(), result.getDescription());
    //     assertEquals(updateProductCoupon.getValue(), result.getValue());

    //     // Kiểm tra các tương tác
    //     verify(productCouponRepository, times(1)).save(any(ProductCoupon.class));
        
    //     // Kiểm tra phương thức createAction được gọi với Integer ID
    //     verify(createAction, times(1)).createAction(eq("ProductCoupon"), eq("UPDATE"), eq(integerId), anyString(), anyString(), eq(employee));  // Dùng integerId (kiểu Integer) trong verify
    // }

    @Test
    void deleteProductCouponTest(){
        Long longID = 1L;
        System.out.println("Delete product coupon test");

        // Create the mock employee with ID 1
        Employee employee = new Employee();
        employee.setId(1);
        employee.setFullName("John Doe");

        // Mock the employee repository to return this employee when searching by ID 1
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));  // Mock the employee retrieval

        // Now, mock the productCouponRepository.findById to return an existing product coupon if needed
        ProductCoupon productCoupon = new ProductCoupon();
        productCoupon.setId(1);
        productCoupon.setPoint(100);
        productCoupon.setStatus(true);  // Assuming the initial status is true
        when(productCouponRepository.findById(longID)).thenReturn(Optional.of(productCoupon));

        // Mock the productCouponRepository.save to return the updated ProductCoupon
        when(productCouponRepository.save(any(ProductCoupon.class))).thenReturn(productCoupon);

        // Mock the createAction to verify that it's being called correctly
        doNothing().when(createAction).createAction(eq("ProductCoupon"), eq("DELETE"), eq(1), anyString(), anyString(), eq(employee));

        // Call the method to delete the product coupon
        ProductCoupon result = productCouponService.deleteProductCoupon(longID, employee.getId());

        // Verify the methods were called as expected
        verify(employeeRepository, times(1)).findById(1);
        verify(productCouponRepository, times(1)).save(any(ProductCoupon.class));  // Check save was called
        verify(createAction, times(1)).createAction(eq("ProductCoupon"), eq("DELETE"), eq(1), anyString(), anyString(), eq(employee));

        // Check that the product coupon's status was updated
        assertFalse(result.getStatus(), "The product coupon status should be false after deletion");
    }

}
