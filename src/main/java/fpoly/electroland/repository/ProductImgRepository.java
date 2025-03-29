package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.Product;
import fpoly.electroland.model.ProductImg;
import java.util.List;


@Repository
public interface ProductImgRepository extends JpaRepository<ProductImg, Integer> {
    void deleteByProduct(Product product);
    List<ProductImg> findByProduct(Product product);
    List<ProductImg> findByProductId(int id);
}
