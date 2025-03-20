package fpoly.electroland.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import fpoly.electroland.model.Category;
import fpoly.electroland.model.Supplier;
import fpoly.electroland.repository.CategoryRepository;

@ExtendWith(MockitoExtension.class)
public class CategoryTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryService categoryService;

    @Test
    void getListCategories(){
        // Dữ liệu giả lập
        Category cate1 = new Category(1, "Category A", "");
        Category cate2 = new Category(2, "Category B", "");

        // Chỉ định hành vi của phương thức findAll()
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(cate1, cate2));

        // Gọi phương thức getAllSuppliers()
        List<Category> categories = categoryService.getAll();

        // Kiểm tra kết quả
        assertNotNull(categories, "Category list should not be null");
        assertEquals(2, categories.size(), "Category list should contain 2 elements");
        assertEquals("Category A", categories.get(0).getName(), "First supplier should be Category A");
        assertEquals("Category B", categories.get(1).getName(), "Second supplier should be Category B");

        // Kiểm tra phương thức findAll() đã được gọi đúng cách
        verify(categoryRepository, times(1)).findAll();
    }
}
