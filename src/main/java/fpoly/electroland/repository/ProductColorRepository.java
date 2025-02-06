package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.ProductColor;

@Repository
public interface ProductColorRepository extends JpaRepository<ProductColor, Integer> {

}
