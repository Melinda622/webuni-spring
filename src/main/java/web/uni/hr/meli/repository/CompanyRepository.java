package web.uni.hr.meli.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.uni.hr.meli.model.Company;
import web.uni.hr.meli.model.Employee;
import web.uni.hr.meli.model.EntityType;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company,Long> {

    Long countByCompanyNumber(String companyNumber);

    Long countByCompanyNumberAndIdNot(String companyNumber, long id);

    List<Company> findDistinctByStaffSalaryGreaterThan(int salary);

    @Query("select c from Company c where size(c.staff)>?1")
    List<Company> findByStaffCountHigherThan(int limit);
}
