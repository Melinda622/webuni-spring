package web.uni.hr.meli.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Employee {

    @Id
    private long id;
    private String name;
    //private String position;
    @ManyToOne
    private Position position;
    private int salary;
    private LocalDateTime startDate;

    @ManyToOne
    @JoinColumn(name="company_id")
    private Company company;

    public Employee() {

    }

    public Employee(long id, String name, int salary, LocalDateTime startDate) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.startDate = startDate;
    }

    /*public Employee(long id, String name, String position, int salary, LocalDateTime startDate) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.salary = salary;
        this.startDate = startDate;
    }*/

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

//    public String getPosition() {
//        return position;
//    }

//    public void setPosition(String position) {
//        this.position = position;
//    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                ", startDate=" + startDate +
                ", company=" + company +
                '}';
    }
}

