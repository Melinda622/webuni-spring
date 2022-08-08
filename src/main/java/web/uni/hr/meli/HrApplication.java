package web.uni.hr.meli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import web.uni.hr.meli.model.Employee;
import web.uni.hr.meli.repository.CompanyRepository;
import web.uni.hr.meli.repository.EmployeeRepository;
import web.uni.hr.meli.repository.PositionDetailsByCompanyRepository;
import web.uni.hr.meli.service.*;

import java.time.LocalDateTime;

@SpringBootApplication
public class HrApplication implements CommandLineRunner {

    @Autowired
    SalaryService salaryService;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    InitDbService initDbService;

    @Autowired
    CompanyService companyService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PositionDetailsByCompanyRepository positionDetailsByCompanyRepository;

    public static void main(String[] args) {
        SpringApplication.run(HrApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(salaryService.getFinalSalary(new Employee(1L, "John Doe", 100000, LocalDateTime.of(2017, 3, 1, 9, 0))));
        System.out.println(salaryService.getFinalSalary(new Employee(2L, "Jane Doe", 100000, LocalDateTime.of(2007, 3, 1, 9, 0))));
        initDbService.clearDB();
        initDbService.insertTestData();
    }
}
