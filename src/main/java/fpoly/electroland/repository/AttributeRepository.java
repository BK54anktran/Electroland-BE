package fpoly.electroland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fpoly.electroland.model.Attribute;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Integer> {

}
