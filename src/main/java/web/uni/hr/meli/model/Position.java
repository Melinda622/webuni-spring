package web.uni.hr.meli.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Position {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private MinQualification minQualification;
    private int minSalary;
    @OneToMany(mappedBy = "position")
    private List<Employee> employees;


    public Position() {

    }

    public Position(String name, MinQualification minQualification) {
        this.id = id;
        this.name = name;
        this.minQualification = minQualification;
    }

    public Position(long id, String name, MinQualification minQualification) {
        this.id = id;
        this.name = name;
        this.minQualification = minQualification;
    }

    public Position(long id, String name, MinQualification minQualification, int minSalary) {
        this.id = id;
        this.name = name;
        this.minQualification = minQualification;
        this.minSalary = minSalary;
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

    public MinQualification getMinQualification() {
        return minQualification;
    }

    public void setMinQualification(MinQualification minQualification) {
        this.minQualification = minQualification;
    }

    public int getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(int minSalary) {
        this.minSalary = minSalary;
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", minQualification=" + minQualification +
                ", minSalary=" + minSalary +
                '}';
    }
}
