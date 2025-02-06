package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.Customer;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByEmail(String email);
     
    // Tìm kiếm khách hàng theo tên hoặc email
    @Query("SELECT c FROM Customer c WHERE c.fullName LIKE %:keyword% OR c.email LIKE %:keyword%")
    List<Customer> searchByKeyword(@Param("keyword") String keyword);

    // Lọc khách hàng theo trạng thái
    List<Customer> findByStatus(boolean status);

    // Kết hợp cả tìm kiếm và lọc
    @Query("SELECT c FROM Customer c WHERE (c.fullName LIKE %:keyword% OR c.email LIKE %:keyword%) AND c.status = :status")
    List<Customer> searchAndFilter(@Param("keyword") String keyword, @Param("status") boolean status);
    

}