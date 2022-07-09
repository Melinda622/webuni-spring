package web.uni.hr.meli.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.uni.hr.meli.model.Company;

public interface CompanyRepository extends JpaRepository<Company,Long> {

    Long countByCompanyNumber(String companyNumber);

    Long countByCompanyNumberAndIdNot(String companyNumber, long id);

}
