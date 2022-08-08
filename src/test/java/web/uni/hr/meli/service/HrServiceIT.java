package web.uni.hr.meli.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import web.uni.hr.meli.model.Company;
import web.uni.hr.meli.model.Employee;
import web.uni.hr.meli.model.MinQualification;
import web.uni.hr.meli.model.Position;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase
public class HrServiceIT {
    @Autowired
    EmployeeService employeeService;

    @Autowired
    InitDbService initDbService;

    @BeforeEach
    void resetDB() {
        initDbService.clearDB();
    }

    @Test
    void testFindEmployeesByExample() {
        initDbService.insertTestData();
        Employee example=new Employee();
        example.setId(1);
        example.setName("john doe");
        example.setSalary(300000);
        example.setStartDate(LocalDateTime.of(2022, 1, 1, 9, 0));
        example.setPosition(new Position(1,"IT manager", MinQualification.UNIVERSITY));
        example.setCompany(new Company(2, "345672", "IT consulting kft", "Budapest XII", null));
        List<Employee> foundEmployees=employeeService.findEmployeesByExample(example);
        System.out.println(foundEmployees);
    }
}
