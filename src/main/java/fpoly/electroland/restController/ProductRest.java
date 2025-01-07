package fpoly.electroland.restController;

import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.model.Category;
import fpoly.electroland.model.Product;
import fpoly.electroland.model.Supplier;
import fpoly.electroland.repository.CategoryReponsitory;
import fpoly.electroland.repository.ProductReponsitory;
import fpoly.electroland.repository.SupplierReponsitory;
import fpoly.electroland.service.CategoryService;
import fpoly.electroland.service.ProductService;
import fpoly.electroland.service.UserService;
import fpoly.electroland.util.ResponseEntityUtil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class ProductRest {

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Autowired
    ProductReponsitory productReponsitory;

    @Autowired
    CategoryReponsitory categoryReponsitory;

    @Autowired
    SupplierReponsitory supplierReponsitory;

    @GetMapping("/supplier")
    public List<Supplier> getListSuppliers() {
        return supplierReponsitory.findAll();
    }

    @GetMapping("/product")
    public Object getMethodName(@RequestParam(name = "id", required = false, defaultValue = "0") int id) {
        userService.getUser();
        if (id == 0) {
            return ResponseEntityUtil.ok(productService.getProduct());
        }
        return ResponseEntityUtil.ok(productService.getProduct(id));
    }

    @PostMapping("/product/save")
    public Object saveMethodName(@RequestBody Product product) {
        return productReponsitory.save(product);
    }

    @PutMapping("/product/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id, @RequestBody Product product) {
        System.out.println(id);
        try {
            Product updatedProduct = productService.updateProduct(id, product);
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal server error");
        }
    }

    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Đã xóa thành công nhân viên với ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi xóa nhân viên: " + e.getMessage());
        }
    }
}