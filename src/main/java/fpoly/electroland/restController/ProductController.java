package fpoly.electroland.restController;

import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.model.Category;
import fpoly.electroland.model.Product;
import fpoly.electroland.model.ProductAttribute;
import fpoly.electroland.model.ProductImg;
import fpoly.electroland.model.Supplier;
import fpoly.electroland.service.ProductService;
import fpoly.electroland.service.UserService;
import fpoly.electroland.util.ResponseEntityUtil;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

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

        // int id = (int) body.get("id");
        // String name = (String) body.get("name");
        // String avatar =(String) body.get("avatar");
        // String description = (String) body.get("description");
        // Double price = Double.parseDouble((String)body.get("price"));
        // Double priceDiscount = Double.parseDouble((String)body.get("priceDiscount"));
        // Boolean status = (Boolean)body.get("status");
        // Category category = (Category) body.get("category");
        // Supplier supplier = (Supplier)body.get("supplier");
        // List<ProductImg> listImgs = (List<ProductImg>) body.get("productImgs");
        // List<ProductAttribute> listAttributes = (List<ProductAttribute>)body.get("productAttributes");
      
        // Product newP = new Product();
        // newP.setId(id);
        // newP.setName(name);
        // newP.setAvatar(avatar);
        // newP.setDescription(description);
        // newP.setPrice(price);
        // newP.setPriceDiscount(priceDiscount);
        // newP.setStatus(status);
        // newP.setCategory(category);
        // newP.setSupplier(supplier);
        // newP.setProductImgs(listImgs);
        // newP.setProductAttributes(listAttributes);
        // System.out.println(newP);
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

    // @GetMapping("/search")
    // public ResponseEntity<?> searchProduct(@RequestParam String search) {
    // try {
    // List<Product> products = productService.searchProducts(search);
    // if (products.isEmpty()) {
    // return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No products found
    // matching the keyword: " + search);
    // }
    // return ResponseEntity.ok(products);
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error
    // occurred while searching for products.");
    // }
    // }

    // @GetMapping("/sort")
    // public ResponseEntity<List<Product>> sortProducts(
    // @RequestParam String criteria,
    // @RequestParam String order) {
    // try {
    // List<Product> products = productService.sortProducts(criteria, order);
    // return ResponseEntity.ok(products);
    // } catch (IllegalArgumentException e) {
    // return ResponseEntity.badRequest().body(null);
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    // }
    // }
}