package web.uni.hr.meli.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import web.uni.hr.meli.config.HrConfigProperties;
import web.uni.hr.meli.model.Employee;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class SmartEmployeeService extends HrService {

    @Autowired
    HrConfigProperties config;

    @Override
    public int getPayRaisePercent(Employee employee) {
        Duration duration = Duration.between(LocalDateTime.now(), employee.getStartDate());
        double tenure = Math.abs(duration.toDays()) / 365.0;

        //2nd solution:
        Map<Double, Integer> limits = config.getRaise().getSmart().getLimits();

        int maxPercent = 0;

        for (Map.Entry<Double, Integer> entries : limits.entrySet()) {
            if (tenure > entries.getKey()) {
                maxPercent = entries.getValue();
            } else {
                return maxPercent;
            }
        }
        return maxPercent;

    }
}
