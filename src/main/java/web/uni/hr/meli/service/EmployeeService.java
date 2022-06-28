package web.uni.hr.meli.service;

import web.uni.hr.meli.model.Employee;

import java.util.ArrayList;
import java.util.List;

public interface EmployeeService {

    int getPayRaisePercent(Employee employee);

    public Employee save(Employee employee);

    public List<Employee> findALl();

    public Employee findById(long id);

    public void delete(long id);
}
