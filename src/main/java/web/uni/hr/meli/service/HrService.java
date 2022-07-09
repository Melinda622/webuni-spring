package web.uni.hr.meli.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.uni.hr.meli.model.Employee;
import web.uni.hr.meli.repository.EmployeeRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
public abstract class HrService implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee update(Employee employee) {
        if (employeeRepository.existsById(employee.getId())) {
            return employeeRepository.save(employee);
        } else {
            throw new NoSuchElementException();
        }
    }

    public List<Employee> findALl() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> findById(long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public List<Employee> findByPosition(String position) {
        return employeeRepository.findByPositionEquals(position);
    }


    @Override
    public List<Employee> findByNameWithPrefix(String prefix) {
        return employeeRepository.findByNameStartingWithIgnoreCase(prefix);
    }

    @Override
    public List<Employee> findByStartDate(LocalDateTime startDate1, LocalDateTime startDate2) {
        return employeeRepository.findByStartDateBetween(startDate1, startDate2);
    }

    public void delete(long id) {
        employeeRepository.deleteById(id);
    }

}
