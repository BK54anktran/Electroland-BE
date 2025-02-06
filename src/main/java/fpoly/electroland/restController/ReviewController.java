package fpoly.electroland.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.model.Review;
import fpoly.electroland.service.ReviewService;
import fpoly.electroland.util.ResponseEntityUtil;

@RestController
@RequestMapping("/admin")
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    
    @GetMapping("/review")
    public List<Review> GetAllList() {
        return reviewService.getAll();
    }
    
    
    @GetMapping("/reviews")
    public Object getMethodName(@RequestParam(name = "id", required = false, defaultValue = "0") int id) {
        return ResponseEntityUtil.ok(reviewService.getReviewsByproductId(id));
    }
}
