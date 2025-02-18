package fpoly.electroland.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.Review;
import fpoly.electroland.model.ReviewImg;
import fpoly.electroland.repository.ReviewImgRepository;

@Service
public class ReviewImgService {
    @Autowired
    ReviewImgRepository reviewImgRepository;

    public void creatReviewImg (List<String> imgs, Review review){
        imgs.forEach(img -> {
            ReviewImg reviewImg = new ReviewImg();
            reviewImg.setNameIMG(img);
            reviewImg.setReview(review);
            reviewImgRepository.save(reviewImg);
        });
    }
}
