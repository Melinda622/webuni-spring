package web.uni.hr.meli.mapper;

import org.mapstruct.Mapper;
import web.uni.hr.meli.dto.CompanyDto;
import web.uni.hr.meli.model.Company;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    List<CompanyDto> companiesToDtos(List<Company> employees);

    CompanyDto companyToDto(Company company);

    Company dtoToCompany(CompanyDto companyDto);
}
