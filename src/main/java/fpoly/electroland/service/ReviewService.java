package fpoly.electroland.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.Employee;
import fpoly.electroland.model.Review;
import fpoly.electroland.repository.ReviewReponsitory;

@Service
public class ReviewService {

    @Autowired
    ReviewReponsitory reviewReponsitory;

       public List<Review> getAll(){
        return reviewReponsitory.findAll();
   }

}
