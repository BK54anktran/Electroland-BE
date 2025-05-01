package fpoly.electroland.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.Supplier;
import io.lettuce.core.dynamic.annotation.Param;


@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    Optional<Supplier> findById(Long id);

    @Query(value = """
        SELECT DISTINCT s.* 
        FROM Supplier s 
        JOIN Product p ON p.idSupplier = s.id 
        WHERE p.idCategory = :categoryId
        """, nativeQuery = true)
    List<Supplier> findSuppliersByCategoryId(@Param("categoryId") int categoryId);


}
