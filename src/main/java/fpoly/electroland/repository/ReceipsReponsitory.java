package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.Receips;

@Repository
public interface ReceipsReponsitory extends JpaRepository<Receips, Integer> {

}
