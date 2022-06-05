package web.uni.hr.meli.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.uni.hr.meli.dto.CompanyDto;
import web.uni.hr.meli.dto.EmployeeDto;
import web.uni.hr.meli.model.Employee;
import web.uni.hr.meli.service.EmployeeService;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private Map<Long, CompanyDto> companies = new HashMap<>();

    @Autowired
    private EmployeeService employeeService;

    {
        companies.put(1L, new CompanyDto(1, "12345", "IT consulting kft", "Budapest",
                new ArrayList<>(Arrays.asList(new EmployeeDto(1, "John Doe", "accountant", 100000,
                        LocalDateTime.of(2021, 6, 3, 8, 0))))
        ));
    }

    @GetMapping
    public List<CompanyDto> getAllCompanies(@RequestParam(required = false) Boolean full) {
        return adjustCompanyList(full);
    }

    public List<CompanyDto> adjustCompanyList(Boolean full) {
        if (full != null && full) {
            return new ArrayList<>(companies.values());
        } else {
            return companies.values().stream().
                    map(company -> new CompanyDto(company.getId(), company.getCompanyNumber(), company.getName(), company.getAddress(), null)).toList();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getCompaniesByCompanyNumber(@PathVariable long id, @RequestParam(required = false) Boolean full) {
        List<CompanyDto> companyList = adjustCompanyList(full);
        CompanyDto companyDto = companyList.stream().filter(c -> c.getId() == id).findFirst().get();
        if (companyDto != null) {
            return ResponseEntity.ok(companyDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public CompanyDto createCompany(@RequestBody CompanyDto companyDto) {
        companies.put(companyDto.getId(), companyDto);
        return companyDto;
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDto> modifyCompany(@PathVariable long id, @RequestBody CompanyDto companyDto) {
        if (!companies.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        companies.put(id, companyDto);
        return ResponseEntity.ok(companyDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        companies.remove(id);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<List<EmployeeDto>> getEmployeesInCompany(@PathVariable long id) {
        CompanyDto companyDto = companies.get(id);
        if (companyDto != null) {
            return ResponseEntity.ok(companyDto.getStaff());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/employees/{id}")
    public CompanyDto addEmployee(@PathVariable long id, @RequestBody EmployeeDto employeeDto) {
        CompanyDto companyDto = companies.get(id);
        companyDto.getStaff().add(employeeDto);
        return companyDto;
    }

    @PutMapping("/employees/{id}")
    public CompanyDto changeEmployeeList(@PathVariable long id, @RequestBody List<EmployeeDto> employees) {
        CompanyDto companyDto = companies.get(id);
        companyDto.setStaff(employees);
        return companyDto;
    }

    @DeleteMapping("/employees/delete/{id}")
    public void deleteEmployee(@PathVariable long id, @RequestBody EmployeeDto employeeDto) {
        CompanyDto companyDto = companies.get(id);
        List<EmployeeDto> newStaff = companyDto.getStaff();
        boolean employeeIsInList = newStaff.stream().anyMatch(e -> e.getId() == employeeDto.getId());
        if (employeeIsInList) {
            List<EmployeeDto> toBeRemoved = companyDto.getStaff().stream().filter(e -> e.getId() == employeeDto.getId()).toList();
            companyDto.getStaff().removeAll(toBeRemoved);
        }
    }

    //Employeeservice
    @GetMapping("/employees/raise/{id}")
    public int getEmployeesByCompany(@PathVariable long id, @RequestBody Employee employee) {
        return employeeService.getPayRaisePercent(employee);
    }
}
