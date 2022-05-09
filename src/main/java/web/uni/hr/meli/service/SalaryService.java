package web.uni.hr.meli.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.uni.hr.meli.model.Employee;

@Service
public class SalaryService {

    private EmployeeService employeeService;

    public SalaryService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public int getFinalSalary(Employee employee) {
        int newSalary=(int) (employee.getSalary()*(100+employeeService.getPayRaisePercent(employee))/100.0);
        employee.setSalary(newSalary);
        return employee.getSalary();
    }
}
