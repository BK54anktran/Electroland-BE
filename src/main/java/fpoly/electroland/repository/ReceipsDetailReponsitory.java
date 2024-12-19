package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.ReceipsDetail;

@Repository
public interface ReceipsDetailReponsitory extends JpaRepository<ReceipsDetail, Integer> {

}
