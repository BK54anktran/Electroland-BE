package fpoly.electroland.restController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.model.Customer;
import fpoly.electroland.model.Product;
import fpoly.electroland.model.Review;
import fpoly.electroland.service.CustomerService;
import fpoly.electroland.service.ProductService;
import fpoly.electroland.service.ReviewImgService;
import fpoly.electroland.service.ReviewService;
import fpoly.electroland.service.UserService;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


import fpoly.electroland.util.ResponseEntityUtil;

@RestController
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    @Autowired
    CustomerService customerService;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    ReviewImgService reviewImgService;

    @GetMapping("/admin/review")
    public List<Review> GetAllList() {
        return reviewService.getAll();
    }

    @DeleteMapping("/admin/review/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable int id) {
        try {
            reviewService.deleteReview(id);
            return ResponseEntity.ok("Review deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting review");
        }
    }

    @GetMapping("admin/review/sreachs")
    public Object searchReviews(
            @RequestParam(name = "productId", required = false, defaultValue = "0") int productId,
            @RequestParam(name = "point", required = false, defaultValue = "0") int point,
            @RequestParam(name = "status", required = false) Boolean status,
            @RequestParam(name = "keyword", required = false) String keyword) {

        return ResponseEntityUtil.ok(reviewService.searchReviews(productId, point, status, keyword));
    }

    @PutMapping("/admin/review/{id}/status")
    public ResponseEntity<?> updateReviewStatus(@PathVariable int id, @RequestParam boolean status) {
        try {
            Review review = reviewService.getReviewById(id);
            if (review != null) {
                review.setStatus(status);
                reviewService.saveReview(review);
                return ResponseEntity.ok("Review status updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating review status");
        }
    }

    @GetMapping("/reviews")
    public Object getMethodName(@RequestParam(name = "id", required = false, defaultValue = "0") int id) {
        return ResponseEntityUtil.ok(reviewService.getReviewsByproductId(id));
    }
    
    @PostMapping("/createReview")
    public String postMethodName(@RequestBody Map<String, Object> object) {
        Optional<Customer> customer = customerService.getCustomer(userService.getUser().getId());
        Integer idProduct = (Integer) object.get("id");
        Product product = productService.getProduct(idProduct);

        // // Lấy values và chuyển thành Map
        Map<String, Object> values = (Map<String, Object>) object.get("values");
        Integer point = (Integer) values.get("point");
        String content = (String) values.get("content");
        Review review = new Review();
        review.setPoint(point);
        review.setContent(content);
        review.setCustomer(customer.get());
        review.setProduct(product);
        Review newReview = reviewService.creatReview(review);

        // // Lấy danh sách imgs
        List<String> imgs = (List<String>) object.get("imgs");
        if (imgs.size() > 0) {
            reviewImgService.creatReviewImg(imgs, newReview);
        }

        return null;
    }
}
