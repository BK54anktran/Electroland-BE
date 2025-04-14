package fpoly.electroland.restController;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.model.ReceiptCoupon;
import fpoly.electroland.model.Supplier;
import fpoly.electroland.service.SupplierService;
import fpoly.electroland.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @Autowired
    UserService userService;

    @GetMapping("/supplier")
    public List<Supplier> getSuppliers(){
        return supplierService.getAllSuppliers();
    }

    @PostMapping("/admin/product/newSupplier")
    public ResponseEntity<Supplier> newSupplier(@RequestBody Supplier supplier) {
        Integer userId = userService.getUser().getId();
        Supplier saveSupplier = supplierService.createSupplier(supplier, userId);
        return ResponseEntity.ok(saveSupplier);
    }

    @PutMapping("/admin/product/updateSupplier/{id}")
    public ResponseEntity<?> updateSupplier(@PathVariable Long id, @RequestBody Supplier supplier){
        try {
            Integer userId = userService.getUser().getId();
            Supplier udateSupplier = supplierService.updateSupplier(id, supplier, userId);
            if (udateSupplier == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy Mã giảm giá với ID: " + id);
            }
            return ResponseEntity.ok(udateSupplier);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy Mã giảm giá với ID: " + id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Lỗi khi cập nhật mã giảm giá: " + e.getMessage());
        }
    }
}
