package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.Order;

@Repository
public interface OrderReponsitory extends JpaRepository<Order, Integer> {

}
