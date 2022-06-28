package web.uni.hr.meli.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;

public class EmployeeDto {

    private long id;
    @NotNull
    private String name;
    @NotNull
    private String position;
    @Min(1)
    private int salary;
    @Past
    private LocalDateTime startDate;

    public EmployeeDto() {

    }

    public EmployeeDto(long id, String name, String position, int salary, LocalDateTime startDate) {
        this.id = id;
        this.name = name;
        this.position = position;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                ", startDate=" + startDate +
                '}';
    }
}
