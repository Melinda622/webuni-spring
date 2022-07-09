package web.uni.hr.meli.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import web.uni.hr.meli.dto.EmployeeDto;
import web.uni.hr.meli.mapper.HrMapper;
import web.uni.hr.meli.model.Employee;
import web.uni.hr.meli.service.EmployeeService;

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
    EmployeeService employeeService;

    @Autowired
    HrMapper hrMapper;

    @GetMapping
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> result = employeeService.findALl();
        return hrMapper.employeesToDtos(result);
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
        Employee employee = hrMapper.dtoToEmployee(employeeDto);
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
}
