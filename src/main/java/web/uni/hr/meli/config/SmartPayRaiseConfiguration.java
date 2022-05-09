package web.uni.hr.meli.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import web.uni.hr.meli.service.DefaultEmployeeService;
import web.uni.hr.meli.service.EmployeeService;
import web.uni.hr.meli.service.SmartEmployeeService;

@Configuration
@Profile("smart")
public class SmartPayRaiseConfiguration {

    @Bean
    public EmployeeService employeeService() {
        return new SmartEmployeeService();
    }
}
