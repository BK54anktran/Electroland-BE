package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
