package web.uni.hr.meli.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.uni.hr.meli.model.Employee;

import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    List<Employee> findByPositionEquals(String position);

    @Query("SELECT e FROM Employee e WHERE UPPER(e.name) like UPPER(?1)")
    List<Employee> findByNameStartingWithIgnoreCase(String prefix);

    List<Employee> findByStartDateBetween(LocalDateTime startDate1, LocalDateTime startDate2);

    Long countByName(String name);

    Long countByNameAndIdNot(String name, long id);

}
