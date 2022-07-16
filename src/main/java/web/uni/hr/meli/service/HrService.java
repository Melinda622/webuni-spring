package web.uni.hr.meli.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import web.uni.hr.meli.model.Employee;
import web.uni.hr.meli.model.Position;
import web.uni.hr.meli.model.PositionDetailsByCompany;
import web.uni.hr.meli.repository.EmployeeRepository;
import web.uni.hr.meli.repository.PositionDetailsByCompanyRepository;
import web.uni.hr.meli.repository.PositionRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
public abstract class HrService implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    PositionDetailsByCompanyRepository positionDetailsByCompanyRepository;

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


    @Override
    public List<Employee> findALl() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> findById(long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public List<Employee> findByPosition(String position) {
        return employeeRepository.findByPositionNameEquals(position);
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


    //Solution part A
    @Override
    public void raiseMinSalary(long positionId,int limit) {
        List<Employee> targetEmployees = employeeRepository.findByPositionMinSalaryLessThan(positionId,limit);
        Position position=positionRepository.findById(positionId).get();
        position.setMinSalary(limit);
        for (Employee e : targetEmployees) {
            e.getPosition().setMinSalary(limit);
            if(e.getSalary()<limit){
                e.setSalary(limit);
            }
            employeeRepository.save(e);
        }
        positionRepository.save(position);
    }

    //Solution part C
    @Override
    public void raiseMinSalary2(long companyId, long positionId, int limit) {
        PositionDetailsByCompany pd=positionDetailsByCompanyRepository.getByCompanyAndPositionWithMinSalaryLessThan(2,4,100000);
        pd.setMinSalary(limit);
        positionDetailsByCompanyRepository.save(pd);
        List<Employee> targetEmployees=pd.getCompany().getStaff().
                stream().filter(e->e.getSalary()<limit).toList();
        targetEmployees.stream().forEach(e->e.setSalary(limit));
        targetEmployees.stream().forEach(e->employeeRepository.save(e));
    }
}
