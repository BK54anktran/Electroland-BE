package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.ProductImg;

@Repository
public interface ProductImgRepository extends JpaRepository<ProductImg, Integer> {

}
