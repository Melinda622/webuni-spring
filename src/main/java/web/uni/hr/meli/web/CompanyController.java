package web.uni.hr.meli.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.uni.hr.meli.dto.CompanyDto;
import web.uni.hr.meli.dto.EmployeeDto;
import web.uni.hr.meli.mapper.CompanyMapper;
import web.uni.hr.meli.mapper.HrMapper;
import web.uni.hr.meli.model.Company;
import web.uni.hr.meli.model.Employee;
import web.uni.hr.meli.service.CompanyService;
import web.uni.hr.meli.service.EmployeeService;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    //private Map<Long, CompanyDto> companies = new HashMap<>();

    @Autowired
    CompanyService companyService;

    @Autowired
    CompanyMapper companyMapper;

    @Autowired
    HrMapper hrMapper;

    @Autowired
    EmployeeService employeeService;

    @GetMapping
    public List<CompanyDto> getAllCompanies(@RequestParam(required = false) Boolean full) {
        return adjustCompanyList(full);
    }

    public List<CompanyDto> adjustCompanyList(Boolean full) {
        if (full != null && full) {
            List<Company> firms = new ArrayList<>(companyService.findALl());
            return companyMapper.companiesToDtos(firms);
        } else {
            return companyService.findALl().stream().
                    map(company -> new CompanyDto(company.getId(), company.getCompanyNumber(),
                            company.getName(), company.getAddress(), null)).toList();
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
        if (companyService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        companyService.save(company);
        return ResponseEntity.ok(companyMapper.companyToDto(company));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        companyService.delete(id);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<List<EmployeeDto>> getEmployeesInCompany(@PathVariable long id) {
        Company company = companyService.findById(id);

        if (company != null) {
            List<Employee> employees = company.getStaff();
            return ResponseEntity.ok(hrMapper.employeesToDtos(employees));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/employees/{id}")
    public CompanyDto addEmployee(@PathVariable long id, @RequestBody EmployeeDto employeeDto) {
        Company company = companyService.findById(id);
        Employee employee = hrMapper.dtoToEmployee(employeeDto);
        company.getStaff().add(employee);
        return companyMapper.companyToDto(company);
    }

    @PutMapping("/employees/{id}")
    public CompanyDto changeEmployeeList(@PathVariable long id, @RequestBody List<EmployeeDto> employeeDtos) {
        Company company = companyService.findById(id);
        List<Employee> employees = hrMapper.DtosToEmployees(employeeDtos);
        company.setStaff(employees);
        return companyMapper.companyToDto(company);
    }

    @DeleteMapping("/employees/delete/{id}")
    public void deleteEmployee(@PathVariable long id, @RequestBody EmployeeDto employeeDto) {
        Company company = companyService.findById(id);
        List<Employee> newStaff = company.getStaff();
        boolean employeeIsInList = newStaff.stream().anyMatch(e -> e.getId() == employeeDto.getId());
        if (employeeIsInList) {
            List<Employee> toBeRemoved = company.getStaff().stream().filter(e -> e.getId() == employeeDto.getId()).toList();
            company.getStaff().removeAll(toBeRemoved);
        }
    }

    //Employeeservice
    @GetMapping("/employees/raise/{id}")
    public int getEmployeesByCompany(@PathVariable long id, @RequestBody Employee employee) {
        return employeeService.getPayRaisePercent(employee);
    }
}
