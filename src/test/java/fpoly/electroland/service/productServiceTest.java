package fpoly.electroland.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import fpoly.electroland.model.Product;
import fpoly.electroland.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class productServiceTest {
    @Mock
    private ProductRepository productRepository;  // Mock ProductRepository

    @InjectMocks
    private ProductService productService;

    @Test
    void myFirstTest(){
        System.out.println("Alooo");
    }

    @Test
    void getAllProductSuccessfully() {
        System.out.println("success");
        List<Product> productList = new ArrayList<>();
        productList.add(new Product());
        when(productService.getProduct()).thenReturn(productList);
        List<Product> result = productService.getProduct();
        assertNotNull(result, "The product list should not be null");
        assertFalse(result.isEmpty(), "The product list should not be empty");
    }
}
