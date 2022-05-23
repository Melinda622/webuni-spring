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
public class SmartEmployeeService implements EmployeeService {

    /*@Value("${hr.raise.smart.highestLimit}")
    private int highestLimit;

    @Value("${hr.raise.smart.middleLimit}")
    private int middleLimit;

    @Value("${hr.raise.smart.lowestLimit}")
    private double lowestLimit;

    @Value("${hr.raise.smart.lowestPercent}")
    private int lowestPercent;

    @Value("${hr.raise.def.percent}")
    private int defaultPercent;

    @Value("${hr.raise.smart.moderatePercent}")
    private int moderatePercent;

    @Value("${hr.raise.smart.highestPercent}")
    private int highestPercent;

    @Override
    public int getPayRaisePercent(Employee employee) {
        Duration duration = Duration.between(LocalDateTime.now(), employee.getStartDate());
        double tenure = Math.abs(duration.toDays()) / 365.0;
        if (tenure >= highestLimit) {
            return highestPercent;
        } else if (tenure < highestLimit && tenure >= middleLimit) {
            return defaultPercent;
        } else if (tenure < middleLimit && tenure >= lowestLimit) {
            return moderatePercent;
        } else {
            return lowestPercent;
        }
    }*/

    @Autowired
    HrConfigProperties config;

    @Override
    public int getPayRaisePercent(Employee employee) {
        Duration duration = Duration.between(LocalDateTime.now(), employee.getStartDate());
        double tenure = Math.abs(duration.toDays()) / 365.0;

        //2nd solution:
        Map<Double,Integer> limits=config.getRaise().getSmart().getLimits();

        int maxPercent=0;

        for (Map.Entry<Double,Integer> entries: limits.entrySet()) {
            if(tenure>entries.getKey()){
                maxPercent=entries.getValue();
            }else {
                return maxPercent;
            }
        }
        return maxPercent;

        //1st solution:
        /*if (tenure >= config.getRaise().getSmart().getHighestLimit()) {
            return config.getRaise().getSmart().getHighestPercent();
            //tenure < config.getRaise().getSmart().getHighestLimit() &&
        } else if (tenure >= config.getRaise().getSmart().getMiddleLimit()) {
            return config.getRaise().getDef().getPercent();
            //tenure < config.getRaise().getSmart().getMiddleLimit() &&
        } else if (tenure >= config.getRaise().getSmart().getLowestLimit()) {
            return config.getRaise().getSmart().getModeratePercent();
        } else {
            return config.getRaise().getSmart().getLowestPercent();
        }*/
    }
}
