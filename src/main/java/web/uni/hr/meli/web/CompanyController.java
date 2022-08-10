package web.uni.hr.meli.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import web.uni.hr.meli.dto.CompanyDto;
import web.uni.hr.meli.dto.EmployeeDto;
import web.uni.hr.meli.mapper.CompanyMapper;
import web.uni.hr.meli.mapper.HrMapper;
import web.uni.hr.meli.model.AverageSalaryByPosition;
import web.uni.hr.meli.model.Company;
import web.uni.hr.meli.model.Employee;
import web.uni.hr.meli.model.EntityType;
import web.uni.hr.meli.repository.CompanyRepository;
import web.uni.hr.meli.repository.EmployeeRepository;
import web.uni.hr.meli.service.CompanyService;
import web.uni.hr.meli.service.EmployeeService;

import java.util.*;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CompanyMapper companyMapper;

    @Autowired
    HrMapper hrMapper;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    CompanyRepository companyRepository;

    @GetMapping
    public List<CompanyDto> getAllCompanies(@RequestParam(required = false) Boolean full) {
        List<Company> companies = companyService.findALl();

        if (isFull(full)){
            companies=companyRepository.findAllWithEmployee();
            return companyMapper.companiesToDtos(companies);
        } else {
            return companyMapper.companiesToDtosWithNoEmployees(companies);
        }
    }

    private boolean isFull(Boolean full) {
        return full != null && full;
    }


    @GetMapping("/{id}")
    public CompanyDto getById(@PathVariable long id, @RequestParam(required = false) Boolean full) {
        Company company = companyService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (isFull(full)) {
            company=companyRepository.findByIdWithEmployee(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            return companyMapper.companyToDto(company);
        } else {
            return companyMapper.companyToDtoWithNoEmployees(company);
        }
    }


    @PostMapping
    public CompanyDto createCompany(@RequestBody CompanyDto companyDto) {
        Company company = companyMapper.dtoToCompany(companyDto);
        companyService.save(company);
        return companyMapper.companyToDto(company);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDto> modifyCompany(@PathVariable long id, @RequestBody CompanyDto companyDto) {
        Company company = companyMapper.dtoToCompany(companyDto);
        company.setId(id);
        company=companyService.update(company);
        company=companyRepository.findByIdWithEmployee(company.getId()).get();

        try {
           CompanyDto result = companyMapper.companyToDto(company);
            return ResponseEntity.ok(result);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        companyService.delete(id);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<List<EmployeeDto>> getEmployeesInCompany(@PathVariable long id) {
        //Company company = companyService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Company company = companyRepository.findByIdWithEmployee(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        System.out.println(company);
        List<Employee> employees = company.getStaff();
        System.out.println(employees);
        return ResponseEntity.ok(hrMapper.employeesToDtos(employees));
    }


    @PostMapping("/employees/{id}")
    public CompanyDto addEmployee(@PathVariable long id, @RequestBody EmployeeDto employeeDto) {
        Employee employee = hrMapper.dtoToEmployee(employeeDto);
        Company company = companyService.addEmployee(id, employee);
        return companyMapper.companyToDto(company);
    }

    @PutMapping("/employees/{id}")
    public CompanyDto changeEmployeeList(@PathVariable long id, @RequestBody List<EmployeeDto> employeeDtos) {
        List<Employee> employees = hrMapper.DtosToEmployees(employeeDtos);
        Company company = companyService.replaceEmployee(id, employees);
        return companyMapper.companyToDto(company);
    }

    @DeleteMapping("/{id}/employees/delete/{employeeId}")
    public CompanyDto deleteEmployee(@PathVariable long id, @PathVariable long employeeId) {
        Company company = companyService.deleteEmployee(id, employeeId);
        return companyMapper.companyToDto(company);
    }

    @GetMapping("/averagesalary/{id}")
    public List<AverageSalaryByPosition> getAverageSalaryByPosition(@PathVariable long id) {
       return employeeRepository.findByCompanyWithAverageSalary(id);
    }


    @PostMapping("/entityTypes/{id}")
    public CompanyDto addEntityType(@PathVariable long id, @RequestBody EntityType entityType) {
        Company company = companyService.addEntity(id,entityType);
        return companyMapper.companyToDto(company);
    }


   //Employeeservice
    @GetMapping("/employees/raise/{companyId}/{employeeId}")
    public int getRaiseByEmployeesByCompany(@PathVariable long companyId, @PathVariable long employeeId) {
        Employee employee=employeeService.findById(employeeId).get();
        return employeeService.getPayRaisePercent(employee);
    }
}
