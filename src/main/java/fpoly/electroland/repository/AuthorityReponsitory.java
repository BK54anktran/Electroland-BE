package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.Authority;

@Repository
public interface AuthorityReponsitory extends JpaRepository<Authority, Integer> {

}
