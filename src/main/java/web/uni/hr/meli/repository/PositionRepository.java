package web.uni.hr.meli.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.uni.hr.meli.model.EntityType;
import web.uni.hr.meli.model.Position;

import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position,Long> {

    Optional<Position> findByName(String name);

}
