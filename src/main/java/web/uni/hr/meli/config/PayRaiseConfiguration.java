package web.uni.hr.meli.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import web.uni.hr.meli.service.DefaultEmployeeService;
import web.uni.hr.meli.service.EmployeeService;

@Configuration
@Profile("!smart")
public class PayRaiseConfiguration {

    @Bean
    public EmployeeService employeeService() {
        return new DefaultEmployeeService();
    }
}
