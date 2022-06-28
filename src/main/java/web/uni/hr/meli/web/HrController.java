package web.uni.hr.meli.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import web.uni.hr.meli.dto.EmployeeDto;
import web.uni.hr.meli.mapper.HrMapper;
import web.uni.hr.meli.model.Employee;
import web.uni.hr.meli.service.DefaultEmployeeService;
import web.uni.hr.meli.service.EmployeeService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class HrController {

    @Autowired
    DefaultEmployeeService defaultEmployeeService;

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
        Employee employee = employeeService.findById(id);
        if (employee != null) {
            return hrMapper.employeeToDto(employee);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public EmployeeDto createEmployee(@RequestBody @Valid EmployeeDto employeeDto) {
        Employee employee = hrMapper.dtoToEmployee(employeeDto);
        employeeService.save(employee);
        return hrMapper.employeeToDto(employee);
    }

    @PutMapping("/{id}")
    public EmployeeDto modifyEmployee(@PathVariable long id, @RequestBody @Valid EmployeeDto employeeDto) {
        Employee employee = hrMapper.dtoToEmployee(employeeDto);
        if (employeeService.findById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        employee.setId(id);
        employeeService.save(employee);
        return hrMapper.employeeToDto(employee);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        employeeService.delete(id);
    }
}
