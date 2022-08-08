package web.uni.hr.meli.dto;

import web.uni.hr.meli.model.EntityType;

import java.util.ArrayList;
import java.util.List;

public class CompanyDto {

    private long id;
    private String companyNumber;
    private String name;
    private String address;
    private EntityType entityType;
    private List<EmployeeDto> staff = new ArrayList<>();

    public CompanyDto() {
    }

    public CompanyDto(long id, String companyNumber, String name, String address, List<EmployeeDto> staff) {
        this.id = id;
        this.companyNumber = companyNumber;
        this.name = name;
        this.address = address;
        this.staff = staff;
    }

    public CompanyDto(long id, String companyNumber, String name, String address, EntityType entityType, List<EmployeeDto> staff) {
        this.id = id;
        this.companyNumber = companyNumber;
        this.name = name;
        this.address = address;
        this.entityType = entityType;
        this.staff = staff;
    }


    public void addNewEmployee(EmployeeDto employeeDto) {
        staff.add(employeeDto);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<EmployeeDto> getStaff() {
        return staff;
    }

    public void setStaff(List<EmployeeDto> staff) {
        this.staff = staff;
    }
}
