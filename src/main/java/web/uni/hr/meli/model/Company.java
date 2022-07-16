package web.uni.hr.meli.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Company {

    @Id
    private long id;
    private String companyNumber;
    private String name;
    private String address;

    @ManyToOne
    private EntityType entityType;

    //@OneToMany(mappedBy = "company")
    @OneToMany(mappedBy = "company",fetch= FetchType.EAGER)
    private List<Employee> staff = new ArrayList<>();

    public Company() {

    }

    public Company(long id, String companyNumber, String name, String address, List<Employee> staff) {
        this.id = id;
        this.companyNumber = companyNumber;
        this.name = name;
        this.address = address;
        this.staff = staff;
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

    public List<Employee> getStaff() {
        return staff;
    }

    public void setStaff(List<Employee> staff) {
        this.staff = staff;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public void addEmployee(Employee employee) {

        if (this.staff == null) {
            this.staff = new ArrayList<>();
        }
        this.staff.add(employee);
        employee.setCompany(this);
    }

    public void addEntityType(EntityType entityType) {
        this.setEntityType(entityType);
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", companyNumber='" + companyNumber + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                /*", staff=" + staff +*/
                '}';
    }
}
