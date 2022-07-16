package web.uni.hr.meli.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.uni.hr.meli.model.Position;
import web.uni.hr.meli.model.PositionDetailsByCompany;

import java.util.List;

public interface PositionDetailsByCompanyRepository extends JpaRepository<PositionDetailsByCompany,Long> {

    //Solution part C
    @Query("SELECT pdc FROM PositionDetailsByCompany pdc where pdc.company.id=?1 and pdc.position.id=?2 and pdc.minSalary<?3")
    PositionDetailsByCompany getByCompanyAndPositionWithMinSalaryLessThan(long companyId, long positionId, int limit);

}
