package web.uni.hr.meli.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.uni.hr.meli.model.Employee;
import web.uni.hr.meli.model.EntityType;

public interface EntityTypeRepository extends JpaRepository<EntityType,Long> {

}
