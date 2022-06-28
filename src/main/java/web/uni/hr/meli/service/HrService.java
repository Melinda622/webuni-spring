package web.uni.hr.meli.service;

import org.springframework.stereotype.Service;
import web.uni.hr.meli.dto.EmployeeDto;
import web.uni.hr.meli.model.Employee;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public abstract class HrService implements EmployeeService{

    private Map<Long, Employee> employees = new HashMap<>();

    {
        employees.put(1L, new Employee(1, "John Doe", "accountant", 100000, LocalDateTime.of(2021, 6, 3, 8, 0)));
        employees.put(2L, new Employee(2, "Jane Doe", "IT consultant", 200000, LocalDateTime.of(2022, 5, 16, 9, 0)));
    }

    public Map<Long, Employee> getEmployees() {
        return employees;
    }

    public Employee save(Employee employee){
        employees.put(employee.getId(),employee);
        return employee;
    }

    public List<Employee> findALl() {
        return new ArrayList<>(employees.values());
    }

    public Employee findById(long id) {
        return employees.get(id);
    }

    public void delete(long id) {
        employees.remove(id);
    }
}
