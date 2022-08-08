package web.uni.hr.meli.service;

import org.springframework.data.jpa.domain.Specification;
import web.uni.hr.meli.model.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class EmployeeSpecifications {

    public static Specification<Employee> hasId(long id) {
        return (root, cq, cb) -> cb.equal(root.get(Employee_.id), id);
    }

    public static Specification<Employee> hasName(String name) {
        return (root, cq, cb) -> cb.like(cb.lower(root.get(Employee_.name)), name.toLowerCase() + "%");
    }

    public static Specification<Employee> hasPosition(String title) {
        return (root, cq, cb) -> cb.equal(root.get(Employee_.position).get(Position_.name), title);
    }

    public static Specification<Employee> hasSalary(int salary) {
        int higherSalaryLimit = salary * 105 / 100;
        int lowerSalaryLimit = salary * 95 / 100;
        return (root, cq, cb) -> cb.between(root.get(Employee_.salary), lowerSalaryLimit, higherSalaryLimit);
    }

    public static Specification<Employee> hasStartDate(LocalDateTime startDate) {
        LocalDateTime startOfDay = LocalDateTime.of(startDate.toLocalDate(), LocalTime.MIN);
        return (root, cq, cb) -> cb.between(root.get(Employee_.startDate), startOfDay, startOfDay.plusDays(1));
    }

    public static Specification<Employee> hasCompany(String companyName) {
        return (root, cq, cb) -> cb.like(cb.lower(root.get(Employee_.company).get(Company_.name)), companyName.toLowerCase() + "%");
    }

}
