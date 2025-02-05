package fpoly.electroland.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:search% OR p.description LIKE %:search%")
    List<Product> findProduct(@Param("search") String search);

    //sort
    @Query("SELECT p FROM Product p ORDER BY p.price ASC")
    List<Product> sortByPriceAsc();

    @Query("SELECT p FROM Product p ORDER BY p.price DESC")
    List<Product> sortByPriceDesc();

    @Query("SELECT p FROM Product p ORDER BY p.name ASC")
    List<Product> sortByNameAsc();

    @Query("SELECT p FROM Product p ORDER BY p.name DESC")
    List<Product> sortByNameDesc();
}
