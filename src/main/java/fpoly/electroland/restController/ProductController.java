package fpoly.electroland.restController;

import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.model.Product;
import fpoly.electroland.service.ProductService;
import fpoly.electroland.service.UserService;
import fpoly.electroland.util.ResponseEntityUtil;

import java.lang.reflect.Array;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;
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
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @GetMapping("/product")
    public Object getMethodName(@RequestParam(name = "id", required = false, defaultValue = "0") int id) {
        userService.getUser();
        if (id == 0) {
            return ResponseEntityUtil.ok(productService.getProduct());
        }
        return ResponseEntityUtil.ok(productService.getProduct(id));
    }

    @PostMapping("/product/search")
    public Object getProductsSearch(@RequestBody String body) {
        userService.getUser();
        JSONObject jsonObject = new JSONObject(body);
        if (jsonObject.get("brands") != null) {
            // JSONArray brands = new JSONArray(jsonObject.get("brands"));
            System.out.println(jsonObject.get("brands").getClass().getName());
            // return
            // ResponseEntityUtil.ok(productService.getProductSupplier(brands.getInt(0)));
        }
        return ResponseEntityUtil.ok(productService.getProduct());
    }

    @PostMapping("/product/save")
    public Object saveMethodName(@RequestBody Product product) {
        return productService.saveProduct(product);
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