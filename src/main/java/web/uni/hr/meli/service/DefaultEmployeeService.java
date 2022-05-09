package web.uni.hr.meli.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.uni.hr.meli.config.HrConfigProperties;
import web.uni.hr.meli.model.Employee;

@Service
public class DefaultEmployeeService implements EmployeeService {

    @Autowired
    HrConfigProperties config;

    @Override
    public int getPayRaisePercent(Employee employee) {
        //5%
        return config.getRaise().getDef().getPercent();
    }
}
