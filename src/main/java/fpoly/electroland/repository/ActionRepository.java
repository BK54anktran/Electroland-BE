package fpoly.electroland.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.Action;

@Repository
public interface ActionRepository extends JpaRepository<Action, Integer> {
    List<Action> findByTableNameContainingOrActionContainingOrEmployee_FullNameContaining(String tableName, String action, String employeeFullName);
}
