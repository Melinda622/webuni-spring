package web.uni.hr.meli.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.uni.hr.meli.dto.EmployeeDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class HrController {

    private Map<Long, EmployeeDto> employees = new HashMap<>();

    {
        employees.put(1L, new EmployeeDto(1, "John Doe", "accountant", 100000, LocalDateTime.of(2021, 6, 3, 8, 0)));
        employees.put(2L, new EmployeeDto(2, "Jane Doe", "IT consultant", 200000, LocalDateTime.of(2022, 5, 16, 9, 0)));
    }

    @GetMapping
    public List<EmployeeDto> getAllEmployees() {
        return new ArrayList<>(employees.values());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getById(@PathVariable long id){
        EmployeeDto employeeDto=employees.get(id);
        if(employeeDto!=null){
            return ResponseEntity.ok(employeeDto);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Solution with a query parameter:
    @GetMapping(params="salaryLimit")
    public ResponseEntity<List<EmployeeDto>> filterEmployeesBySalaries(@RequestParam int salaryLimit){
        List<EmployeeDto> result=employees.values().stream().
                                filter(employee->employee.getSalary()>salaryLimit).
                                toList();
        if(result!=null&&!result.isEmpty()){
            return ResponseEntity.ok(result);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Solution with a path variable:
    /*@GetMapping("/salaries/{salary}")
    public ResponseEntity<List<EmployeeDto>> filterEmployeesBySalaries(@PathVariable int salary){
        List<EmployeeDto> result=new ArrayList<>();
        result=employees.values().stream().filter(employee->employee.getSalary()>salary).toList();
        if(result!=null&&!result.isEmpty()){
            return ResponseEntity.ok(result);
        }else{
            return ResponseEntity.notFound().build();
        }
    }*/

    @PostMapping
    public EmployeeDto createEmployee(@RequestBody EmployeeDto employeeDto){
        employees.put(employeeDto.getId(), employeeDto);
        System.out.println(employeeDto.getStartDate());
        return employeeDto;
    }

    @PutMapping("/{id}")
    public  ResponseEntity<EmployeeDto> modifyAirport(@PathVariable long id, @RequestBody EmployeeDto employeeDto){
        if(!employees.containsKey(id)){
            return ResponseEntity.notFound().build();
        }
        employeeDto.setId(id);
        employees.put(id,employeeDto);
        return ResponseEntity.ok(employeeDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id){
        employees.remove(id);
    }
}
