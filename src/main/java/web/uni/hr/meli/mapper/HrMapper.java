package web.uni.hr.meli.mapper;

import org.mapstruct.Mapper;
import web.uni.hr.meli.dto.EmployeeDto;
import web.uni.hr.meli.model.Employee;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HrMapper {

    List<EmployeeDto> employeesToDtos(List<Employee> employees);

    List<Employee> DtosToEmployees(List<EmployeeDto> employees);

    EmployeeDto employeeToDto(Employee employee);

    Employee dtoToEmployee(EmployeeDto employeeDto);
}
