package web.uni.hr.meli.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import web.uni.hr.meli.dto.EmployeeDto;
import web.uni.hr.meli.model.Employee;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HrMapper {

    List<EmployeeDto> employeesToDtos(List<Employee> employees);

    @Mapping(target="title", source="position.name")
    EmployeeDto employeeToDto(Employee employee);

    @InheritInverseConfiguration
    Employee dtoToEmployee(EmployeeDto employeeDto);

    List<Employee> DtosToEmployees(List<EmployeeDto> employees);

}
