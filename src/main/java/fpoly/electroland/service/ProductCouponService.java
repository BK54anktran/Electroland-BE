package fpoly.electroland.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.ProductCoupon;
import fpoly.electroland.model.Employee;
import fpoly.electroland.model.ProductCoupon;
import fpoly.electroland.repository.ActionRepository;
import fpoly.electroland.repository.EmployeeRepository;
import fpoly.electroland.repository.ProductCouponRepository;
import fpoly.electroland.util.CreateAction;

@Service
public class ProductCouponService {

    @Autowired
    ProductCouponRepository productCouponRepository;

    @Autowired
    ActionRepository actionRepository;

    @Autowired
    CreateAction createAction;

    @Autowired
    EmployeeRepository employeeRepository;

    public Object getList() {
        return productCouponRepository.findAll();
    }

    public List<ProductCoupon> getAll(){
        return productCouponRepository.findAll();
    }
  
    public ProductCoupon newProductCoupon(ProductCoupon productCoupon, int userId) {
        ProductCoupon savedProductCoupon = productCouponRepository.save(productCoupon);
        Employee creatorEmployee = employeeRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        createAction.createAction("ProductCoupon", "CREATE", savedProductCoupon.getId(), null,
                savedProductCoupon.toString(), creatorEmployee);
        return savedProductCoupon;
    }

    public ProductCoupon updateProductCoupon(Long id, ProductCoupon updateProductCoupon, int userId) {
        Optional<ProductCoupon> optionalProductCoupon = productCouponRepository.findById(id);
        if (optionalProductCoupon.isPresent()) {
            ProductCoupon existingProductCoupon = optionalProductCoupon.get();

            existingProductCoupon.setPoint(updateProductCoupon.getPoint());
            existingProductCoupon.setDescription(updateProductCoupon.getDescription());
            existingProductCoupon.setValue(updateProductCoupon.getValue());
            existingProductCoupon.setProduct(updateProductCoupon.getProduct());

            ProductCoupon savedProductCoupon = productCouponRepository.save(existingProductCoupon);

            Employee creatorEmployee = employeeRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            int maxLength = 200;

            String oldValue = existingProductCoupon.toString();
            if (oldValue.length() > maxLength) {
                oldValue = oldValue.substring(0, maxLength - 3) + "...";
            }

            String newValue = savedProductCoupon.toString();
            if (newValue.length() > maxLength) {
                newValue = newValue.substring(0, maxLength - 3) + "...";
            }

            createAction.createAction("ProductCoupon", "UPDATE", savedProductCoupon.getId(), oldValue, newValue,
                    creatorEmployee);
            return savedProductCoupon;
        } else {
            throw new RuntimeException("Employee not found with id: " + id);
        }
    }

    public List<ProductCoupon> searchProductCoupon(String key) {
        Integer keyNumeric = null;
        String keyString = key;
        Double keyDouble = null;
        try {
            keyNumeric = Integer.parseInt(key);
        } catch (NumberFormatException e) {
            try {
                keyDouble = Double.parseDouble(key);
            } catch (NumberFormatException ex) {
                keyString = key;
            }
        }
        return productCouponRepository.findByPointOrProductNameContainingOrValue(keyNumeric, keyString,
                keyDouble);
    }

    public ProductCoupon deleteProductCoupon(Long id, int userId) {
        Optional<ProductCoupon> optionalProductCoupon = productCouponRepository.findById(id);
        if (optionalProductCoupon.isPresent()) {
            ProductCoupon existingProductCoupon = optionalProductCoupon.get();
            String oldValue = existingProductCoupon.toString();
            existingProductCoupon.setStatus(false);

            ProductCoupon savedProductCoupon = productCouponRepository.save(existingProductCoupon);

            Employee creatorEmployee = employeeRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            createAction.createAction("ProductCoupon", "DELETE", savedProductCoupon.getId(), oldValue,
                    savedProductCoupon.toString(), creatorEmployee);

            return savedProductCoupon;
        } else {
            throw new RuntimeException("Employee not found with id: " + id);
        }
    }

 
}
