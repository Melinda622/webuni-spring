package web.uni.hr.meli.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import web.uni.hr.meli.model.AverageSalaryByPosition;
import web.uni.hr.meli.model.Employee;

import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    List<Employee> findByPositionNameEquals(String position);

    @Query("SELECT e FROM Employee e WHERE UPPER(e.name) like UPPER(?1)")
    List<Employee> findByNameStartingWithIgnoreCase(String prefix);

    List<Employee> findByStartDateBetween(LocalDateTime startDate1, LocalDateTime startDate2);

    List<Employee> findBySalaryGreaterThan(int salary);

    @Query("select e.position.name as position, AVG(e.salary) as averageSalary from Employee e join e.company c where c.id=?1 group by e.position.name order by 1 desc")
    List<AverageSalaryByPosition> findByCompanyWithAverageSalary(long companyId);

    //Optional homework A
    @Query("SELECT e FROM Employee e join e.position p where e.position.id=?1 and p.minSalary=?2")
    List<Employee> findByPositionMinSalaryLessThan(long positionId, int limit);
}
