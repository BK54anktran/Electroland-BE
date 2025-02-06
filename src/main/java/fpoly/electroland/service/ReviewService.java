package fpoly.electroland.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.Review;
import fpoly.electroland.repository.ReviewRepository;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;
    
    public List<Review> getReviewsByproductId(int productId) {
        return reviewRepository.getReviewsByProductId(productId);
    }
}
