package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.ReviewImg;

@Repository
public interface ReviewImgRepository extends JpaRepository<ReviewImg,Integer> {
    
}
