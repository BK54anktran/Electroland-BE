package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
