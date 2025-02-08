package fpoly.electroland.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.service.ReviewService;
import fpoly.electroland.util.ResponseEntityUtil;

@RestController
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    @GetMapping("/reviews")
    public Object getMethodName(@RequestParam(name = "id", required = false, defaultValue = "0") int id) {
        return ResponseEntityUtil.ok(reviewService.getReviewsByproductId(id));
    }

    @PostMapping("/createReview")
    public String postMethodName(@RequestBody Object object) {
        System.out.println(object);
        return null;
    }
}
