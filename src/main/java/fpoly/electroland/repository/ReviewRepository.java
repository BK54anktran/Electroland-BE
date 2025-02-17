package fpoly.electroland.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("SELECT r FROM Review r WHERE r.product.id = :productId")
    List<Review> getReviewsByProductId(int productId);

    @Query("SELECT r FROM Review r WHERE " +
            "(:productId = 0 OR r.product.id = :productId) AND " +
            "(:point = 0 OR r.point = :point) AND " +
            "(:status IS NULL OR r.status = :status) AND " +
            "(:keyword IS NULL OR LOWER(r.content) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "ORDER BY r.date DESC")
    List<Review> searchReviews(Integer productId, Integer point, Boolean status, String keyword);

}
