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
@RequestMapping("/admin/discount")
public class AdminDiscountController {
    
    @Autowired
    ReceiptCouponService receiptCouponService;

    @Autowired
    ProductCouponService productCouponService;

    @Autowired
    UserService userService;

    @GetMapping("/discountOrder")
    public List<ReceiptCoupon> getListDiscountOrder() {
        return receiptCouponService.getAllReceiptCoupon();
    }
    
    @GetMapping("/discountOrder/search/discountPercent")
    public List<ReceiptCoupon> searchByDiscountPercent(@RequestParam String discountPercent) {
        return receiptCouponService.searchByDiscountPercent(discountPercent);
    }

    @GetMapping("/discountOrder/search/discountMoney")
    public List<ReceiptCoupon> searchByDiscountMoney(@RequestParam String discountMoney) {
        return receiptCouponService.searchByDiscountMoney(discountMoney);
    }

    @GetMapping("/discountOrder/search")
    public List<ReceiptCoupon> searchReceiptCoupon(
            @RequestParam(required = false) String discountPercent,
            @RequestParam(required = false) String discountMoney) {
        return receiptCouponService.searchReceiptCoupon(discountPercent, discountMoney);
    }   

    @GetMapping("/discountOrder/{id}")
    public ReceiptCoupon getReceiptCouponById(@PathVariable int id) {
        return receiptCouponService.getReceiptCouponById(id).orElse(null);
    }

    @PostMapping("/discountOrder/newDiscountOrder")
    public ResponseEntity<ReceiptCoupon> newDReceiptCoupon(@RequestBody ReceiptCoupon receiptCoupon) {
        Integer userId = userService.getUser().getId();
        ReceiptCoupon saveReceiptCoupon = receiptCouponService.newDiscountOrder(receiptCoupon, userId);
        return ResponseEntity.ok(saveReceiptCoupon);
    }

    @PutMapping("/discountOrder/updateDiscountOrder/{id}")
    public ResponseEntity<?> updaReceiptCoupon(@PathVariable Long id, @RequestBody ReceiptCoupon receiptCoupon){
        try {
            Integer userId = userService.getUser().getId();
            ReceiptCoupon updatedReceiptCoupon = receiptCouponService.updateDiscountOrder(id, receiptCoupon, userId);
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

    @PutMapping("/discountOrder/deleteDiscountOrder/{id}")
    public ResponseEntity<?> deleteReceiptCoupon(@PathVariable Long id){
        try {
            Integer userId = userService.getUser().getId();
            ReceiptCoupon deleteReceiptCoupon = receiptCouponService.deleteReceiptCoupon(id, userId);
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
    @GetMapping("/discountProduct")
    public Object getAllProductCoupon() {
        return productCouponService.getList();
    }

    @PostMapping("/discountProduct/newDiscountProduct")
    public ProductCoupon newProductCoupon(@RequestBody ProductCoupon productCoupon){
        return productCouponService.newProductCoupon(productCoupon);
    }

    // @PutMapping("/discountProduct/update/{id}")
    // public ResponseEntity<?> updateProductCoupon(@PathVariable Long id, @RequestBody ProductCoupon productCoupon){
    //     try{
    //         Integer userId = userService.getUser().getId();
    //         ProductCoupon updatedProductCoupon = productCouponService.updateProductCoupon(id, productCoupon, userId);
    //         return ResponseEntity.ok(updatedProductCoupon);
    //     } catch(NoSuchElementException e){
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy Mã giảm giá với ID: " + id);
    //     } catch(Exception e){
    //         e.printStackTrace();
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    //         .body("Lỗi khi cập nhật mã giảm giá: " + e.getMessage());
    //     }
    // }

    @GetMapping("/discountProduct/search")
    public ResponseEntity<List<ProductCoupon>> searchProductCoupon(@RequestParam String key) {
        try {
            List<ProductCoupon> discountProducts = productCouponService.searchDiscountProduct(key);
            return ResponseEntity.ok(discountProducts);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
