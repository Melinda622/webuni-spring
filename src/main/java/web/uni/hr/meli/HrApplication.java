package web.uni.hr.meli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import web.uni.hr.meli.model.Employee;
import web.uni.hr.meli.service.SalaryService;

import java.time.LocalDateTime;

@SpringBootApplication
public class HrApplication implements CommandLineRunner {

    @Autowired
    SalaryService salaryService;

    public static void main(String[] args) {
        SpringApplication.run(HrApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(salaryService.getFinalSalary(new Employee(1L, "John Doe", "accountant", 100000, LocalDateTime.of(2017, 3, 1, 9, 0))));
        System.out.println(salaryService.getFinalSalary(new Employee(2L, "Jane Doe", "product manager", 100000, LocalDateTime.of(2007, 3, 1, 9, 0))));
    }
}
