package web.uni.hr.meli.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import web.uni.hr.meli.model.Employee;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class HrTLController {

    private List<Employee> allEmployees = new ArrayList<>();

    {
        allEmployees.add(new Employee(1L,"John Doe","accountant",100000, LocalDateTime.of(2022,3,1,9,0)));
    }

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

    @PostMapping("/employees")
    public String addEmployees(Employee employee){
        allEmployees.add(employee);
        return "redirect:employees";
    }
}
