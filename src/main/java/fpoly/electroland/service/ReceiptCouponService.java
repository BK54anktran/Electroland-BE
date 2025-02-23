package fpoly.electroland.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.Employee;
import fpoly.electroland.model.Receipt;
import fpoly.electroland.model.ReceiptCoupon;
import fpoly.electroland.repository.ActionRepository;
import fpoly.electroland.repository.EmployeeRepository;
import fpoly.electroland.repository.ReceiptCouponRepository;
import fpoly.electroland.util.CreateAction;

@Service
public class ReceiptCouponService {

    @Autowired
    ReceiptCouponRepository receiptCouponRepository;

    @Autowired
    ActionRepository actionRepository;

    @Autowired
    CreateAction createAction;

    @Autowired
    EmployeeRepository employeeRepository;

    public List<ReceiptCoupon> getAllReceiptCoupon() {
        return receiptCouponRepository.findAll();
    }

    public Optional<ReceiptCoupon> getReceiptCouponById(int id) {
        return receiptCouponRepository.findById(id);
    }

    public List<ReceiptCoupon> searchByDiscountPercent(String discountPercent) {
        return receiptCouponRepository.findByDiscountPercent(discountPercent);
    }

    public List<ReceiptCoupon> searchByDiscountMoney(String discountMoney) {
        return receiptCouponRepository.findByDiscountMoney(discountMoney);
    }

    public List<ReceiptCoupon> searchReceiptCoupon(String discountPercent, String discountMoney) {
        if (discountPercent == null && discountMoney == null) {
            return new ArrayList<>();
        }
        if (discountPercent != null && discountMoney != null) {
            return receiptCouponRepository.findByDiscountPercentAndDiscountMoney(discountPercent, discountMoney);
        } else if (discountPercent != null) {
            return searchByDiscountPercent(discountPercent);
        } else if (discountMoney != null) {
            return searchByDiscountMoney(discountMoney);
        }
        return new ArrayList<>();
    }

    public ReceiptCoupon newDiscountOrder(ReceiptCoupon receiptCoupon, int userId){
        ReceiptCoupon savedReceiptCoupon = receiptCouponRepository.save(receiptCoupon);
        Employee creatorEmployee = employeeRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        createAction.createAction("ReceiptCoupon", "CREATE", savedReceiptCoupon.getId(), null,
            savedReceiptCoupon.toString(), creatorEmployee);
        return savedReceiptCoupon;
    }

    public ReceiptCoupon updateDiscountOrder(Long id, ReceiptCoupon receiptCoupon, int userId){
        Optional<ReceiptCoupon> optionalReceiptCoupon = receiptCouponRepository.findById(id);
        if (optionalReceiptCoupon.isPresent()) {
            ReceiptCoupon existingReceiptCoupon = optionalReceiptCoupon.get();
            String oldValue = existingReceiptCoupon.toString();
            existingReceiptCoupon.setDiscountMoney(receiptCoupon.getDiscountMoney());
            existingReceiptCoupon.setDiscountPercent(receiptCoupon.getDiscountPercent());
            existingReceiptCoupon.setMaxDiscount(receiptCoupon.getMaxDiscount());
            existingReceiptCoupon.setMinReceiptPrice(receiptCoupon.getMinReceiptPrice());
            existingReceiptCoupon.setStatus(receiptCoupon.getStatus());
            existingReceiptCoupon.setDescription(receiptCoupon.getDescription());

            ReceiptCoupon savedReceiptCoupon = receiptCouponRepository.save(existingReceiptCoupon);

            Employee creatorEmployee = employeeRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            createAction.createAction("ReceiptCoupon", "UPDATE", savedReceiptCoupon.getId(), oldValue,
                savedReceiptCoupon.toString(), creatorEmployee);

            return savedReceiptCoupon;
        }else {
            throw new RuntimeException("Employee not found with id: " + id);
        }
    }

    public ReceiptCoupon deleteReceiptCoupon(Long id, int userId){
        Optional<ReceiptCoupon> optionalReceiptCoupon = receiptCouponRepository.findById(id);
        if (optionalReceiptCoupon.isPresent()) {
            ReceiptCoupon existingReceiptCoupon = optionalReceiptCoupon.get();
            String oldValue = existingReceiptCoupon.toString();
            existingReceiptCoupon.setStatus(false);

            ReceiptCoupon savedReceiptCoupon = receiptCouponRepository.save(existingReceiptCoupon);

            Employee creatorEmployee = employeeRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            createAction.createAction("ReceiptCoupon", "DELETE", savedReceiptCoupon.getId(), oldValue,
                savedReceiptCoupon.toString(), creatorEmployee);

            return savedReceiptCoupon;
        }else {
            throw new RuntimeException("Employee not found with id: " + id);
        }
    }
}
