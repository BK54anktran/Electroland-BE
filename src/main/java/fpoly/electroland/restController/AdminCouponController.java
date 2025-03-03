package fpoly.electroland.restController;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.model.Employee;
import fpoly.electroland.model.ProductCoupon;
import fpoly.electroland.model.ReceiptCoupon;
import fpoly.electroland.service.ProductCouponService;
import fpoly.electroland.service.ReceiptCouponService;
import fpoly.electroland.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/admin/coupon")
public class AdminCouponController {
    
    @Autowired
    ReceiptCouponService receiptCouponService;

    @Autowired
    ProductCouponService productCouponService;

    @Autowired
    UserService userService;

    @GetMapping("/receiptCoupon")
    public List<ReceiptCoupon> getListReceiptCoupons() {
        return receiptCouponService.getListReceiptCoupons();
    }
    
    @GetMapping("/receiptCoupon/search/percent")
    public List<ReceiptCoupon> searchByDiscountPercent(@RequestParam String percent) {
        return receiptCouponService.searchByDiscountPercent(percent);
    }

    @GetMapping("/receiptCoupon/search/money")
    public List<ReceiptCoupon> searchByDiscountMoney(@RequestParam String discountMoney) {
        return receiptCouponService.searchByDiscountMoney(discountMoney);
    }

    @GetMapping("/receiptCoupon/search")
    public List<ReceiptCoupon> searchReceiptCoupon(
            @RequestParam(required = false) String discountPercent,
            @RequestParam(required = false) String discountMoney) {
        return receiptCouponService.searchReceiptCoupon(discountPercent, discountMoney);
    }   

    @GetMapping("/receiptCoupon/{id}")
    public ReceiptCoupon getReceiptCouponById(@PathVariable int id) {
        return receiptCouponService.getReceiptCouponById(id).orElse(null);
    }

    @PostMapping("/receiptCoupon/new")
    public ResponseEntity<ReceiptCoupon> newDReceiptCoupon(@RequestBody ReceiptCoupon receiptCoupon) {
        Integer userId = userService.getUser().getId();
        ReceiptCoupon saveReceiptCoupon = receiptCouponService.newReceiptCoupon(receiptCoupon, userId);
        return ResponseEntity.ok(saveReceiptCoupon);
    }

    @PutMapping("/receiptCoupon/update/{id}")
    public ResponseEntity<?> updaReceiptCoupon(@PathVariable Long id, @RequestBody ReceiptCoupon receiptCoupon){
        try {
            Integer userId = userService.getUser().getId();
            ReceiptCoupon updatedReceiptCoupon = receiptCouponService.updaReceiptCoupon(id, receiptCoupon, userId);
            if (updatedReceiptCoupon == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy Mã giảm giá với ID: " + id);
            }
            return ResponseEntity.ok(updatedReceiptCoupon);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy Mã giảm giá với ID: " + id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Lỗi khi cập nhật mã giảm giá: " + e.getMessage());
        }
    }

    @PutMapping("/receiptCoupon/delete/{id}")
    public ResponseEntity<?> deleteReceiptCoupon(@PathVariable Long id){
        try {
            Integer userId = userService.getUser().getId();
            ReceiptCoupon deleteReceiptCoupon = receiptCouponService.delReceiptCoupon(id, userId);
            if (deleteReceiptCoupon == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy Mã giảm giá với ID: " + id);
            }
            return ResponseEntity.ok(deleteReceiptCoupon);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy Mã giảm giá với ID: " + id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Lỗi khi cập nhật mã giảm giá: " + e.getMessage());
        }
    }

    //ProductCoupon
    @GetMapping("/productCoupon")
    public Object getListProductCoupon() {
        return productCouponService.getList();
    }

    @PostMapping("/productCoupon/new")
    public ResponseEntity<ProductCoupon> newProductCoupon(@RequestBody ProductCoupon productCoupon){
        Integer userId = userService.getUser().getId();
        ProductCoupon saveProductCoupon = productCouponService.newProductCoupon(productCoupon, userId);
        return ResponseEntity.ok(saveProductCoupon);
    }

    @PutMapping("/productCoupon/update/{id}")
    public ResponseEntity<?> updateProductCoupon(@PathVariable Long id, @RequestBody ProductCoupon productCoupon){
        try{
            Integer userId = userService.getUser().getId();
            ProductCoupon updatedProductCoupon = productCouponService.updateProductCoupon(id, productCoupon, userId);
            return ResponseEntity.ok(updatedProductCoupon);
        } catch(NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy Mã giảm giá với ID: " + id);
        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Lỗi khi cập nhật mã giảm giá: " + e.getMessage());
        }
    }

    @GetMapping("/productCoupon/search")
    public ResponseEntity<List<ProductCoupon>> searchProductCoupon(@RequestParam String key) {
        try {
            List<ProductCoupon> discountProducts = productCouponService.searchProductCoupon(key);
            return ResponseEntity.ok(discountProducts);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
