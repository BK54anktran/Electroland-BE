package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.ConfigStore;
import java.util.Optional;

@Repository
public interface ConfigStoreRepository extends JpaRepository<ConfigStore, Integer> {
    Optional<ConfigStore> findByKeyword(String keyword);
}
