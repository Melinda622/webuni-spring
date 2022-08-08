package web.uni.hr.meli.dto;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Objects;

public class EmployeeDto {

    private long id;
    @NotEmpty
    private String name;

    @NotEmpty
    private String title;

    @Min(1)
    private int salary;
    @Past
    private LocalDateTime startDate;

    private CompanyDto companyDto;


    public EmployeeDto() {

    }

     public EmployeeDto(long id, String name, String title, int salary, LocalDateTime startDate) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.salary = salary;
        this.startDate = startDate;
    }

    public EmployeeDto(long id, String name, int salary, LocalDateTime startDate) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.startDate = startDate;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public CompanyDto getCompanyDto() {
        return companyDto;
    }

    public void setCompanyDto(CompanyDto companyDto) {
        this.companyDto = companyDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDto that = (EmployeeDto) o;
        return id == that.id && salary == that.salary && name.equals(that.name) && title.equals(that.title) && startDate.equals(that.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, title, salary, startDate);
    }
}
