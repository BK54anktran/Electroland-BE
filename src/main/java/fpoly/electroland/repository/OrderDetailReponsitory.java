package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.OrderDetail;

@Repository
public interface OrderDetailReponsitory extends JpaRepository<OrderDetail, Integer> {

}
