package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.repository.ReviewRepository;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

}
