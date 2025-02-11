package fpoly.electroland.restController;

import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.model.Product;
import fpoly.electroland.service.ProductService;
import fpoly.electroland.service.UserService;
import fpoly.electroland.util.ResponseEntityUtil;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    public Object getMethodName(
            @RequestParam(name = "id", required = false, defaultValue = "0") int id,
            @RequestParam(name = "key", required = false, defaultValue = "") String key,
            @RequestParam(name = "sortOrder", required = false, defaultValue = "") String sortOrder,
            @RequestParam(name = "category", required = false, defaultValue = "0") int category,
            @RequestParam(name = "minPrice", required = false, defaultValue = "0") int minPrice,
            @RequestParam(name = "maxPrice", required = false, defaultValue = "0") int maxPrice,
            @RequestParam(name = "supplier", required = false) List<Integer> supplier) {
        Sort sort = Sort.unsorted();
        if ("asc".equalsIgnoreCase(sortOrder)) {
            sort = Sort.by("price").ascending();
        } else if ("desc".equalsIgnoreCase(sortOrder)) {
            sort = Sort.by("price").descending();
        }

        boolean hasPriceFilter = minPrice > 0 || maxPrice > 0;
        boolean hasSupplierFilter = supplier != null && !supplier.isEmpty();

        if (id != 0) {
            return ResponseEntityUtil.ok(productService.getProduct(id));
        }

        if (!key.isEmpty() && category != 0 && hasPriceFilter && hasSupplierFilter) {
            return ResponseEntityUtil
                    .ok(productService.getProductByFillter(key, category, minPrice, maxPrice, supplier, sort));
        }

        if (!key.isEmpty() && category != 0 && hasSupplierFilter) {
            return ResponseEntityUtil.ok(productService.getProductByFillter(key, category, supplier, sort));
        }

        if (!key.isEmpty() && hasPriceFilter && hasSupplierFilter) {
            return ResponseEntityUtil.ok(productService.getProductByFillter(key, minPrice, maxPrice, supplier, sort));
        }

        if (!key.isEmpty() && hasSupplierFilter) {
            return ResponseEntityUtil.ok(productService.getProductByFillter(key, supplier, sort));
        }

        if (!key.isEmpty() && hasPriceFilter) {
            return ResponseEntityUtil.ok(productService.getProductByFillter(key, minPrice, maxPrice, sort));
        }

        if (category != 0 && hasPriceFilter && hasSupplierFilter) {
            return ResponseEntityUtil
                    .ok(productService.getProductByFillter(category, minPrice, maxPrice, supplier, sort));
        }

        if (category != 0 && hasSupplierFilter) {
            return ResponseEntityUtil.ok(productService.getProductByFillter(category, supplier, sort));
        }

        if (hasPriceFilter && hasSupplierFilter) {
            return ResponseEntityUtil
                    .ok(productService.getProductByFillter(minPrice, maxPrice, supplier, sort));
        }

        if (hasSupplierFilter) {
            return ResponseEntityUtil.ok(productService.getProductByFillter(supplier, sort));
        }

        if (!key.isEmpty()) {
            return ResponseEntityUtil.ok(productService.getProductByKey(key, sort));
        }

        return ResponseEntityUtil.ok(productService.getProduct(sort));
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