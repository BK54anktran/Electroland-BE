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

       public List<Review> getAll(){
        return reviewRepository.findAll();
   }
    
    public List<Review> getReviewsByproductId(int productId) {
        return reviewRepository.getReviewsByProductId(productId);
    }
    public void deleteReview(int reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    public List<Review> searchReviews(int productId, int point, Boolean status, String keyword) {
        return reviewRepository.searchReviews(productId, point, status, keyword);
    }
    public Review getReviewById(int id) {
        return reviewRepository.findById(id).orElse(null);
    }
    
    public void saveReview(Review review) {
        reviewRepository.save(review);
    }
    
    
    
}
