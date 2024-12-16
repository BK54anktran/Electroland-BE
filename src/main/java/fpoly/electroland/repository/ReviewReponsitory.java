package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.Review;

@Repository
public interface ReviewReponsitory extends JpaRepository<Review, Integer> {

}
