package web.uni.hr.meli.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class PositionDetailsByCompany {

    @Id
    private long id;
    private int minSalary;

    @ManyToOne
    private Company company;

    @ManyToOne
    private Position position;

    public PositionDetailsByCompany() {

    }


    public PositionDetailsByCompany(long id, int minSalary) {
        this.id = id;
        this.minSalary = minSalary;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(int minSalary) {
        this.minSalary = minSalary;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "PositionDetailsByCompany{" +
                "id=" + id +
                ", minSalary=" + minSalary +
                ", company=" + company +
                ", position=" + position +
                '}';
    }
}
