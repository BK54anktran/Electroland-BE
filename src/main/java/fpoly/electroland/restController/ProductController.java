package fpoly.electroland.restController;

import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.model.Category;
import fpoly.electroland.model.Employee;
import fpoly.electroland.model.Product;
import fpoly.electroland.model.ProductAttribute;
import fpoly.electroland.model.ProductImg;
import fpoly.electroland.model.Supplier;
import fpoly.electroland.service.ProductService;
import fpoly.electroland.service.UserService;
import fpoly.electroland.util.ResponseEntityUtil;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestMapping;
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
            @RequestParam(name = "key", required = false) String key,
            @RequestParam(name = "sortOrder", required = false) String sortOrder,
            @RequestParam(name = "category", required = false, defaultValue = "0") int category,
            @RequestParam(name = "minPrice", required = false, defaultValue = "0") int minPrice,
            @RequestParam(name = "maxPrice", required = false, defaultValue = "0") int maxPrice,
            @RequestParam(name = "supplier", required = false) List<Integer> supplier) {
        Sort sort = Sort.unsorted();
        if ("asc".equalsIgnoreCase(sortOrder))
            sort = Sort.by("price").ascending();
        if ("desc".equalsIgnoreCase(sortOrder))
            sort = Sort.by("price").descending();

        if (id != 0) {
            return ResponseEntityUtil.ok(productService.getProduct(id));
        }

        // Gọi phương thức service duy nhất với các tham số lọc
        return ResponseEntityUtil
                .ok(productService.getProductByFilter(key, category, minPrice, maxPrice, supplier, sort));
    }

    // @GetMapping("/product")
    // public List<Product> getMethodName() {
    // List<Product> list = productService.getProduct();
    // return list;
    // }

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

    @PostMapping("/saveProduct")
    public void saveProduct(@RequestBody Product product) {
        productService.editProduct(product);
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

    @GetMapping("/admin/product/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String keyword) {
        try {
            List<Product> products = productService.searchProducts(keyword); 
            return ResponseEntity.ok(products); 
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(null); 
        }
    }

    // Thêm một endpoint để lấy thống kê sản phẩm
    @GetMapping("/product/statistics")
    public ResponseEntity<List<Object[]>> getProductStatistics(
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortOrder) {

        // Nếu không có tham số sắp xếp, mặc định sắp xếp theo tên sản phẩm
        Sort sort = Sort.by(Sort.Order.asc(sortField != null ? sortField : "name"));

        // Nếu có tham số sắp xếp giảm dần
        if ("desc".equals(sortOrder)) {
            sort = Sort.by(Sort.Order.desc(sortField != null ? sortField : "name"));
        }

        // Lấy thống kê sản phẩm
        List<Object[]> statistics = productService.getProductStatistics(sort);

        return ResponseEntity.ok(statistics);
    }

    // 🔹 API lấy Top 10 sản phẩm doanh thu cao nhất
    @GetMapping("/product/top10-revenue")
    public ResponseEntity<List<Map<String, Object>>> getTop10ProductRevenue(
            @RequestParam(required = false) String startDateStr,
            @RequestParam(required = false) String endDateStr) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime startDate = startDateStr != null ? LocalDate.parse(startDateStr, formatter).atStartOfDay() : null;
        LocalDateTime endDate = endDateStr != null ? LocalDate.parse(endDateStr, formatter).atTime(23, 59, 59) : null;

        return ResponseEntity.ok(productService.getTop10ProductRevenue(startDate, endDate));
    }

}