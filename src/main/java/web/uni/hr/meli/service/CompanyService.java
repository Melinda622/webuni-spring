package web.uni.hr.meli.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.uni.hr.meli.model.Company;
import web.uni.hr.meli.model.Employee;
import web.uni.hr.meli.model.EntityType;
import web.uni.hr.meli.repository.CompanyRepository;
import web.uni.hr.meli.repository.EmployeeRepository;
import web.uni.hr.meli.repository.EntityTypeRepository;
import web.uni.hr.meli.repository.PositionRepository;

import java.util.*;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EntityTypeRepository entityTypeRepository;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PositionRepository positionRepository;

    @Transactional
    public Company save(Company company) {
        checkUniqueCompanyNumber(company.getCompanyNumber(), null);
        companyRepository.save(company);
        return company;
    }

    @Transactional
    public Company update(Company company) {
        checkUniqueCompanyNumber(company.getCompanyNumber(), company.getId());
        if (companyRepository.existsById(company.getId())) {
            company = companyRepository.findByIdWithEmployee(company.getId()).get();
            return companyRepository.save(company);
        } else {
            throw new NoSuchElementException();
        }
    }

    private void checkUniqueCompanyNumber(String companyNumber, Long id) {

        boolean forUpdate = id != null;

        Long count = forUpdate ?
                companyRepository.countByCompanyNumberAndIdNot(companyNumber, id)
                : companyRepository.countByCompanyNumber(companyNumber);

        if (count > 0)
            throw new NonUniqueCompanyNumberException(companyNumber);
    }

    public List<Company> findALl() {
        return companyRepository.findAll();
    }

    public Optional<Company> findById(long id) {
        return companyRepository.findById(id);
    }

    @Transactional
    public void delete(long id) {
        companyRepository.deleteById(id);
    }

    @Transactional
    public Company addEmployee(long id, Employee employee) {
        Company company = companyRepository.findByIdWithEmployee(id).get();
        company.addEmployee(employee);
        employeeService.save(employee);
        return company;
    }

    @Transactional
    public Company deleteEmployee(long id, long employeeId) {
        Company company = companyRepository.findById(id).get();
        Employee employee = employeeRepository.findById(employeeId).get();
        employee.setCompany(null);
        company.getStaff().remove(employee);
        return company;
    }

    @Transactional
    public Company replaceEmployee(long id, List<Employee> employees) {
        Company company = companyRepository.findById(id).get();
        for (Employee employee : company.getStaff()) {
            employee.setCompany(null);
        }
        company.getStaff().clear();
        for (Employee employee : employees) {
            company.addEmployee(employee);
            employeeService.save(employee);
        }
        return company;
    }

    @Transactional
    public Company addEntity(long id, EntityType entityType) {
        entityTypeRepository.save(entityType);
        Company company = companyRepository.findById(id).get();
        company.addEntityType(entityType);
        return company;
    }
}
