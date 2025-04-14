package fpoly.electroland.restController;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.model.Category;
import fpoly.electroland.model.Supplier;
import fpoly.electroland.service.CategoryService;
import fpoly.electroland.service.UserService;

@RestController
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    @GetMapping("/category")
    public List<Category> GetAllList() {
        return categoryService.getAll();
    }

    @PostMapping("/admin/product/newCategory")
    public ResponseEntity<Category> newSupplier(@RequestBody Category category) {
        Integer userId = userService.getUser().getId();
        Category save = categoryService.createCategory(category, userId);
        return ResponseEntity.ok(save);
    }

    @PutMapping("/admin/product/updateCategory/{id}")
    public ResponseEntity<?> updateSupplier(@PathVariable Long id, @RequestBody Category category){
        try {
            Integer userId = userService.getUser().getId();
            Category update = categoryService.updateCategory(id, category, userId);
            if (update == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy Mã giảm giá với ID: " + id);
            }
            return ResponseEntity.ok(update);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy Mã giảm giá với ID: " + id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Lỗi khi cập nhật mã giảm giá: " + e.getMessage());
        }
    }

}
