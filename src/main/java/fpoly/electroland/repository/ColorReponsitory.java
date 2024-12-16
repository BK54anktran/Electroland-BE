package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.Color;

@Repository
public interface ColorReponsitory extends JpaRepository<Color, Integer> {

}
