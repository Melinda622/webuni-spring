package web.uni.hr.meli.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.uni.hr.meli.model.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class HrTLController {

    private List<Employee> allEmployees = new ArrayList<>();

   /* {
        allEmployees.add(new Employee(1L,"John Doe","accountant",100000, LocalDateTime.of(2022,3,1,9,0)));
    }*/

    @GetMapping("/")
    public String home(){
        return "index";
    }

    @GetMapping("/employees")
    public String listEmployees(Map<String,Object> model){
        model.put("employees",allEmployees);
        model.put("newEmployee",new Employee());
        return "employees";
    }

    @GetMapping("/employees/modify")
    public String modifyEmployees(Map<String,Object> model){
        model.put("employees",allEmployees);
        model.put("newEmployee",new Employee());
        return "employeeForm";
    }

    @GetMapping("/employees/modify/{id}")
    public String modifyEmployees(@PathVariable("id") long id, Map<String,Object> model){
        Employee employee=allEmployees.stream().filter(e->e.getId()==id).findFirst().get();
        model.put("employees",allEmployees);
        model.put("modifiedEmployee",employee);
        return "employeeForm";
    }

    @PostMapping("/employees")
    public String addEmployees(Employee employee){
        allEmployees.add(employee);
        return "redirect:employees";
    }

    @PostMapping("/employees/updateEmployee")
    public String updateEmployees(Employee employee){
        for (int i = 0; i < allEmployees.size(); i++) {
            if(allEmployees.get(i).getId()== employee.getId()){
                allEmployees.set(i,employee);
            }
        }
        return "redirect:/employees";
    }

    @GetMapping("/employees/delete/{id}")
    public String deleteEmployees(@PathVariable("id") long id){
        List<Employee> result=
        allEmployees.stream().filter(employee->employee.getId()==id).toList();
        allEmployees.removeAll(result);
        return "redirect:/employees";
    }
}
