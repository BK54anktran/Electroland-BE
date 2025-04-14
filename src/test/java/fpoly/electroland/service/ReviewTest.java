package fpoly.electroland.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import fpoly.electroland.model.Customer;
import fpoly.electroland.model.Product;
import fpoly.electroland.model.Review;
import fpoly.electroland.model.ReviewImg;
import fpoly.electroland.repository.CustomerRepository;
import fpoly.electroland.repository.ReviewImgRepository;
import fpoly.electroland.repository.ReviewRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ReviewTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks 
    private CustomerService customerService;

    @Mock
    private ReviewImgRepository reviewImgRepository;

    @InjectMocks
    private ReviewImgService reviewImgService;

    private Review reviewMock1;
    private Review reviewMock2;

    private Product productMock1;
    private Product productMock2;

    private ReviewImg reviewImgMock1;
    private ReviewImg reviewImgMock2;

    private Customer customerMock;

    @BeforeEach
    void setUp() throws ParseException {
        // Khởi tạo đối tượng Customer
        customerMock = new Customer();
        customerMock.setId(1);
        customerMock.setAvatar(null);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateOfBirth = sdf.parse("2024-10-13"); 
        customerMock.setDateOfBirth(dateOfBirth);
        customerMock.setEmail("daotankiet@gmail.com");
        customerMock.setFullName("Đào Tấn Kiệt");
        customerMock.setGender(true);
        customerMock.setPassword("123");
        customerMock.setPhoneNumber("0987654321");
        customerMock.setStatus(true); 

        // Khởi tạo đối tượng Product (sản phẩm)
        productMock1 = new Product();
        productMock1.setId(1);
        productMock1.setName("Sản phẩm 1");
        productMock1.setPrice(100.0);
        productMock1.setPriceDiscount(90.0);
        productMock1.setStatus(true);
        
        productMock2 = new Product();
        productMock2.setId(2);
        productMock2.setName("Sản phẩm 2");
        productMock2.setPrice(200.0);
        productMock2.setPriceDiscount(180.0);
        productMock2.setStatus(true);

        // Khởi tạo đối tượng ReviewImg
        reviewImgMock1 = new ReviewImg();
        reviewImgMock1.setId(1);
        reviewImgMock1.setNameIMG("ReviewImg1");
        reviewImgMock1.setReview(reviewMock1);

        reviewImgMock2 = new ReviewImg();
        reviewImgMock2.setId(2);
        reviewImgMock2.setNameIMG("ReviewImg2");
        reviewImgMock2.setReview(reviewMock1);

        // Khởi tạo đối tượng Review (Đánh giá)
        reviewMock1 = new Review();
        reviewMock1.setContent("Ngon");
        reviewMock1.setCustomer(customerMock);
        reviewMock1.setDate(new Date());
        reviewMock1.setId(1);
        reviewMock1.setImgs(Arrays.asList(reviewImgMock1, reviewImgMock2));
        reviewMock1.setIsRead(true);
        reviewMock1.setPoint(5);
        reviewMock1.setProduct(productMock1); // Liên kết với sản phẩm 1
        reviewMock1.setStatus(true);

        // Khởi tạo đối tượng Review thứ 2
        reviewMock2 = new Review();
        reviewMock2.setContent("Tốt");
        reviewMock2.setCustomer(customerMock);
        reviewMock2.setDate(new Date());
        reviewMock2.setId(2);
        reviewMock2.setImgs(Arrays.asList(reviewImgMock1));
        reviewMock2.setIsRead(true);
        reviewMock2.setPoint(4);
        reviewMock2.setProduct(productMock2); // Liên kết với sản phẩm 2
        reviewMock2.setStatus(true);
    }

    @Test
    public void testReviewSetup() {

    }
}
