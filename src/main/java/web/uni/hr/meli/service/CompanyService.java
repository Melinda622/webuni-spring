package web.uni.hr.meli.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.uni.hr.meli.dto.CompanyDto;
import web.uni.hr.meli.dto.EmployeeDto;
import web.uni.hr.meli.model.Company;
import web.uni.hr.meli.model.Employee;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CompanyService {

    private Map<Long, Company> companies = new HashMap<>();

    @Autowired
    private EmployeeService employeeService;

    {
        companies.put(1L, new Company(1, "12345", "IT consulting kft", "Budapest",
                new ArrayList<>(Arrays.asList(new Employee(1, "John Doe", "accountant", 100000,
                        LocalDateTime.of(2021, 6, 3, 8, 0))))
        ));
    }

    public Map<Long, Company> getCompanies() {
        return companies;
    }

    public Company save(Company company) {
        companies.put(company.getId(), company);
        return company;
    }

    public List<Company> findALl() {
        return new ArrayList<>(companies.values());
    }

    public Company findById(long id) {
        return companies.get(id);
    }

    public void delete(long id) {
        companies.remove(id);
    }
}
