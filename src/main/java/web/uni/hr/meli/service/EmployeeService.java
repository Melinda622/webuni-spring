package web.uni.hr.meli.service;

import web.uni.hr.meli.model.Employee;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    int getPayRaisePercent(Employee employee);

    Employee save(Employee employee);

    Employee update(Employee employee);

    List<Employee> findALl();

    Optional<Employee> findById(long id);

    List<Employee> findByPosition(String position);

    List<Employee> findByNameWithPrefix(String prefix);

    List<Employee> findByStartDate(LocalDateTime startDate1, LocalDateTime startDate2);

    void delete(long id);

    void raiseMinSalary(long positionId,int limit);

    void raiseMinSalary2(long companyId ,long positionId,int limit);
}
