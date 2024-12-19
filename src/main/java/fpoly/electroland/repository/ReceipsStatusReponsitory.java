package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.ReceipsStatus;

@Repository
public interface ReceipsStatusReponsitory extends JpaRepository<ReceipsStatus, Integer> {

}
