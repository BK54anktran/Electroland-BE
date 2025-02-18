package fpoly.electroland.repository;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.Product;
import fpoly.electroland.model.Supplier;

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

}
