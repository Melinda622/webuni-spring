package web.uni.hr.meli.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.uni.hr.meli.model.EntityType;
import web.uni.hr.meli.model.Position;

public interface PositionRepository extends JpaRepository<Position,Long> {

}
