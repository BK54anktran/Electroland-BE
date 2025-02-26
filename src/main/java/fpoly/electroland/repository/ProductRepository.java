package fpoly.electroland.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.Product;
import fpoly.electroland.model.Supplier;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

        List<Product> findAll(Sort sort);

        List<Product> findBySupplier(Supplier supplier);

        List<Product> findByCategoryId(int id);

        List<Product> findByCategoryId(int id, Sort sort);

        List<Product> findByNameContaining(String key);

        List<Product> findByNameContaining(String key, Sort sort);

        List<Product> findByNameContainingAndCategoryId(String key, int id, Sort sort);

        List<Product> findByPriceBetween(int minPrice, int maxPrice);

        List<Product> findByPriceBetween(int minPrice, int maxPrice, Sort sort);

        List<Product> findByNameContainingAndPriceBetween(String key, int minPrice, int maxPrice, Sort sort);

        List<Product> findByNameContainingAndCategoryIdAndPriceBetween(String key, int category, int minPrice,
                        int maxPrice,
                        Sort sort);

        List<Product> findByCategoryIdAndPriceBetween(int category, int minPrice, int maxPrice, Sort sort);

        List<Product> findByNameContainingAndCategoryIdAndPriceBetweenAndSupplierIdIn(String key, int category,
                        int minPrice, int maxPrice, List<Integer> suppliers,
                        Sort sort);

        List<Product> findByNameContainingAndCategoryIdAndSupplierIdIn(String key, int category, List<Integer> supplier,
                        Sort sort);

        List<Product> findByNameContainingAndPriceBetweenAndSupplierIdIn(String key, int minPrice, int maxPrice,
                        List<Integer> supplier, Sort sort);

        List<Product> findByNameContainingAndSupplierIdIn(String key, List<Integer> supplier, Sort sort);

        List<Product> findByCategoryIdAndPriceBetweenAndSupplierIdIn(int category, int minPrice, int maxPrice,
                        List<Integer> supplier, Sort sort);

        List<Product> findByCategoryIdAndSupplierIdIn(int category, List<Integer> supplier, Sort sort);

        List<Product> findByPriceBetweenAndSupplierIn(int minPrice, int maxPrice, List<Supplier> suppliers, Sort sort);

        List<Product> findBySupplierIdIn(List<Integer> supplier, Sort sort);

        List<Product> findByCategoryIdAndPriceBetweenAndSupplierIn(int category, int minPrice, int maxPrice,
                        List<Supplier> suppliers, Sort sort);

        List<Product> findByPriceBetweenAndSupplierIdIn(Integer minPrice, Integer maxPrice, List<Integer> supplier,
                        Sort sort);

        // Thêm câu truy vấn thống kê sản phẩm
        @Query("SELECT p.name AS product_name, "
                        + "c.name AS category_name, "
                        + "s.name AS supplier_name, "
                        + "COUNT(p.id) AS total_products, "
                        + "SUM(CASE WHEN p.priceDiscount IS NOT NULL THEN 1 ELSE 0 END) AS discounted_products, "
                        + "AVG(p.priceDiscount / p.price) AS avg_discount_percentage, "
                        + "AVG(p.price) AS avg_price "
                        + "FROM Product p "
                        + "JOIN Category c ON p.category.id = c.id "
                        + "JOIN Supplier s ON p.supplier.id = s.id "
                        + "GROUP BY p.name, c.name, s.name")
        List<Object[]> getProductStatistics(Sort sort);

        @Query("SELECT p.name AS productName, " +
                        "SUM(rd.quantity * rd.price) AS revenue, " +
                        "SUM(rd.quantity) AS totalQuantity " +
                        "FROM ReceiptDetail rd JOIN rd.product p JOIN rd.receipt r " +
                        "WHERE (:startDate IS NULL OR r.receiptDate >= :startDate) " +
                        "AND (:endDate IS NULL OR r.receiptDate <= :endDate) " +
                        "GROUP BY p.name " +
                        "ORDER BY revenue DESC")
        List<Object[]> getTop10ProductRevenue(@Param("startDate") LocalDateTime startDate,
                        @Param("endDate") LocalDateTime endDate);

}
