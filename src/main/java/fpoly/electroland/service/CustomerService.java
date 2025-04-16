package fpoly.electroland.service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.Customer;
import fpoly.electroland.model.User;
import fpoly.electroland.repository.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    TypeCustomerService typeCustomerService;

    public Optional<Customer> findCustomerById(Integer id) {
        return customerRepository.findById(id);
    }

    public Optional<Customer> getCustomer(String email) {
        return customerRepository.findByEmail(email);
    }

    public Optional<Customer> getCustomer(Integer id) {
        return customerRepository.findById(id);
    }

    public Customer updateCustomer(Integer id, Customer updatedCustomer) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isPresent()) {
            Customer existingCustomer = customerOptional.get();
            // Cập nhật thông tin khách hàng
            existingCustomer.setFullName(updatedCustomer.getFullName());
            existingCustomer.setPhoneNumber(updatedCustomer.getPhoneNumber());
            existingCustomer.setDateOfBirth(updatedCustomer.getDateOfBirth());
            existingCustomer.setGender(updatedCustomer.getGender());
            existingCustomer.setEmail(updatedCustomer.getEmail());
            existingCustomer.setPassword(updatedCustomer.getPassword());
            existingCustomer.setStatus(updatedCustomer.getStatus());
            existingCustomer.setTypeCustomer(updatedCustomer.getTypeCustomer());
            return customerRepository.save(existingCustomer);
        } else {
            throw new RuntimeException("Customer not found with id " + id); // Ném ngoại lệ nếu không tìm thấy khách
                                                                            // hàng
        }
    }

    public Customer updateCustomer(Integer id, boolean status) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isPresent()) {
            Customer existingCustomer = customerOptional.get();
            // Cập nhật thông tin khách hàng;
            existingCustomer.setStatus(status);
            return customerRepository.save(existingCustomer);
        } else {
            throw new RuntimeException("Customer not found with id " + id); // Ném ngoại lệ nếu không tìm thấy khách
                                                                            // hàng
        }
    }

    public Customer createCustomer(Customer customer) {
        customer.setTypeCustomer(typeCustomerService.getTypeCustomer(1));
        customer.setAvatar("");
        customer.setStatus(true);
        return customerRepository.save(customer);
    }

    public Customer createCustomerGoogle(Customer customer) {
        customer.setStatus(true);
        customer.setTypeCustomer(typeCustomerService.getTypeCustomer(1));
        return customerRepository.save(customer);
    }

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    // Tìm kiếm khách hàng theo từ khóa
    public List<Customer> searchCustomers(String keyword) {
        return customerRepository.searchByKeyword(keyword);
    }

    // Lọc khách hàng theo trạng thái
    public List<Customer> filterCustomersByStatus(boolean status) {
        return customerRepository.findByStatus(status);
    }

    // Kết hợp tìm kiếm và lọc
    public List<Customer> searchAndFilterCustomers(String keyword, boolean status) {
        return customerRepository.searchAndFilter(keyword, status);
    }

    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public static String generatePassword(int length) {
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String special = "!@#$%^&*()-_=+<>?";
        String allChars = upper + lower + digits + special;

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        // Đảm bảo mỗi loại ký tự xuất hiện ít nhất một lần
        password.append(upper.charAt(random.nextInt(upper.length())));
        password.append(lower.charAt(random.nextInt(lower.length())));
        password.append(digits.charAt(random.nextInt(digits.length())));
        password.append(special.charAt(random.nextInt(special.length())));

        // Các ký tự còn lại chọn ngẫu nhiên từ toàn bộ ký tự
        for (int i = 4; i < length; i++) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        // Xáo trộn chuỗi
        return shuffleString(password.toString());
    }

    private static String shuffleString(String input) {
        SecureRandom random = new SecureRandom();
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int randomIndex = random.nextInt(chars.length);
            char temp = chars[i];
            chars[i] = chars[randomIndex];
            chars[randomIndex] = temp;
        }
        return new String(chars);
    }
}
