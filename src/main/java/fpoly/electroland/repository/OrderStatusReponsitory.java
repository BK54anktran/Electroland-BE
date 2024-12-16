package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.OrderStatus;

@Repository
public interface OrderStatusReponsitory extends JpaRepository<OrderStatus, Integer> {

}
