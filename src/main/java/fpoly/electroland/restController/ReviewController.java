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
import fpoly.electroland.util.ResponseEntityUtil;

@RestController
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    @Autowired
    ReviewImgService reviewImgService;

    @Autowired
    UserService userService;

    @Autowired 
    CustomerService customerService;

    @Autowired
    ProductService productService;

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
        if(imgs.size()>0){
            reviewImgService.creatReviewImg(imgs, newReview);
        }

        
        return null;
    }
}
