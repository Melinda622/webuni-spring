package web.uni.hr.meli.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.uni.hr.meli.model.*;
import web.uni.hr.meli.repository.*;

import java.time.LocalDateTime;

@Service
public class InitDbService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private EntityTypeRepository entityTypeRepository;

    @Autowired
    PositionDetailsByCompanyRepository positionDetailsByCompanyRepository;

    public void clearDB() {
        employeeRepository.deleteAll();
        positionDetailsByCompanyRepository.deleteAll();
        companyRepository.deleteAll();
        positionRepository.deleteAll();
        entityTypeRepository.deleteAll();
    }

    public void insertTestData() {

        Employee employee1 =employeeRepository.save(new Employee(1, "John Doe"/*"IT manager"*/, 300000, LocalDateTime.of(2022, 1, 1, 9, 0)));
        Employee employee2 =employeeRepository.save( new Employee(2, "Jake Doe"/*"accountant"*/, 150000, LocalDateTime.of(2017, 3, 1, 9, 0)));
        Employee employee3 =employeeRepository.save(new Employee(3, "Jane Doe"/*"It consultant"*/, 200000, LocalDateTime.of(2018, 6, 1, 9, 0)));
        Employee employee4 =employeeRepository.save(new Employee(4, "Jim Doe"/*It developer"*/, 92000, LocalDateTime.of(2019, 10, 1, 9, 0)));

        Position itManager=positionRepository.save(new Position(1,"IT manager", MinQualification.UNIVERSITY /*200000*/));
        Position accountant=positionRepository.save(new Position(2,"accountant", MinQualification.COLLEGE /*100000*/));
        Position itConsultant=positionRepository.save(new Position(3,"IT Consultant", MinQualification.COLLEGE /*150000*/));
        Position itDeveloper=positionRepository.save(new Position(4,"IT Developer", MinQualification.HIGH_SCHOOL /*90000*/));

        employee1.setPosition(itManager);
        employee2.setPosition(accountant);
        employee3.setPosition(itConsultant);
        employee4.setPosition(itDeveloper);

        Company company1 = companyRepository.save(new Company(1, "234567", "Financial consulting zrt", "Budapest II", null));
        Company company2 = companyRepository.save(new Company(2, "345672", "IT consulting kft", "Budapest XII", null));

        company1.addEmployee(employee2);
        company2.addEmployee(employee1);;
        company2.addEmployee(employee3);;
        company2.addEmployee(employee4);

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);
        employeeRepository.save(employee4);

        EntityType kft=entityTypeRepository.save(new EntityType(1,"kft"));
        EntityType nyrt=entityTypeRepository.save(new EntityType(2,"nyrt"));
        EntityType zrt=entityTypeRepository.save(new EntityType(3,"zrt"));
        EntityType bt=entityTypeRepository.save(new EntityType(4,"bt"));

        company1.addEntityType(zrt);
        company2.addEntityType(kft);

        companyRepository.save(company1);
        companyRepository.save(company2);

        PositionDetailsByCompany pd1=new PositionDetailsByCompany(1,90000);
        pd1.setCompany(company2);
        pd1.setPosition(itDeveloper);

        PositionDetailsByCompany pd2=new PositionDetailsByCompany(2,100000);
        pd2.setCompany(company1);
        pd2.setPosition(accountant);

        positionDetailsByCompanyRepository.save(pd1);
        positionDetailsByCompanyRepository.save(pd2);
    }
}
