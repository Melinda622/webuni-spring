package web.uni.hr.meli.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import web.uni.hr.meli.dto.EmployeeDto;
import web.uni.hr.meli.mapper.HrMapper;
import web.uni.hr.meli.model.Employee;
import web.uni.hr.meli.model.Position;
import web.uni.hr.meli.repository.EmployeeRepository;
import web.uni.hr.meli.repository.PositionRepository;
import web.uni.hr.meli.service.EmployeeService;
import web.uni.hr.meli.service.HrService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/employees")
public class HrController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private HrMapper hrMapper;

    @GetMapping
    public List<EmployeeDto> getAllEmployees(Pageable pageable) {
        Page<Employee> result = employeeRepository.findAll(pageable);
        return hrMapper.employeesToDtos(result.getContent());
    }

    @GetMapping("/{id}")
    public EmployeeDto getById(@PathVariable long id) {
        Employee employee = employeeService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return hrMapper.employeeToDto(employee);
    }

    @GetMapping("/withname/{prefix}")
    public List<EmployeeDto> getByNameWithPrefix(@PathVariable String prefix) {
        String input = prefix + "%";
        List<Employee> employees = employeeService.findByNameWithPrefix(input);
        return hrMapper.employeesToDtos(employees);
    }

    @GetMapping("/withposition/{position}")
    public List<EmployeeDto> getByPosition(@PathVariable String position) {
        List<Employee> employees = employeeService.findByPosition(position);
        return hrMapper.employeesToDtos(employees);
    }

    @GetMapping("/startDate/{startDate}/endDate/{endDate}")
    public List<EmployeeDto> getEmployeesByStartDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        LocalDateTime start = LocalDateTime.of(startDate, LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(endDate, LocalTime.MAX);
        List<Employee> employees = employeeService.findByStartDate(start, end);
        return hrMapper.employeesToDtos(employees);
    }


    @PostMapping
    public EmployeeDto createEmployee(@RequestBody @Valid EmployeeDto employeeDto) {
        // Position position=positionRepository.findByName(employeeDto.getTitle()).get();
        Employee employee = hrMapper.dtoToEmployee(employeeDto);
        //employee.setPosition(position);
        employeeService.save(employee);
        return hrMapper.employeeToDto(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> modifyEmployee(@PathVariable long id, @RequestBody @Valid EmployeeDto employeeDto) {
        Employee employee = hrMapper.dtoToEmployee(employeeDto);
        employee.setId(id);
        EmployeeDto result = null;
        try {
            result = hrMapper.employeeToDto(employeeService.update(employee));
            return ResponseEntity.ok(result);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        employeeService.delete(id);
    }

    @GetMapping("/salary/{salary}")
    public List<EmployeeDto> getBySalariesGreaterThan(@PathVariable int salary) {
        List<Employee> result = employeeRepository.findBySalaryGreaterThan(salary);
        return hrMapper.employeesToDtos(result);
    }

    @PostMapping("/search")
    public List<EmployeeDto> searchEmployees(@RequestBody EmployeeDto employeeDto) {
        Employee example = hrMapper.dtoToEmployee(employeeDto);
        System.out.println("companyDto: " + employeeDto.getCompanyDto());
        List<Employee> result = employeeService.findEmployeesByExample(example);
        System.out.println(result);
        return hrMapper.employeesToDtos(result);
    }
}
