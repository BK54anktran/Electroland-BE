package fpoly.electroland.restController;

import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.model.Product;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @GetMapping
    public Object getListProduct(@RequestParam(name = "id", required = false, defaultValue = "0") int id) {
        userService.getUser();
        if (id == 0) {
            return ResponseEntityUtil.ok(productService.getProduct());
        }
        return ResponseEntityUtil.ok(productService.getProduct(id));
    }

    @PostMapping("/save")
    public Object saveMethodName(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @PutMapping("/update/{id}")
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

    @GetMapping("/search")
    public ResponseEntity<?> searchProduct(@RequestParam String search) {
        try {
            List<Product> products = productService.searchProducts(search);
            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No products found matching the keyword: " + search);
            }
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while searching for products.");
        }
    }

    @GetMapping("/sort")
    public ResponseEntity<List<Product>> sortProducts(
            @RequestParam String criteria,
            @RequestParam String order) {
        try {
            List<Product> products = productService.sortProducts(criteria, order);
            return ResponseEntity.ok(products);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}