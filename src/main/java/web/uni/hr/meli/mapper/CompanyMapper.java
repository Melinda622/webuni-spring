package web.uni.hr.meli.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import web.uni.hr.meli.dto.CompanyDto;
import web.uni.hr.meli.model.Company;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    List<CompanyDto> companiesToDtos(List<Company> companies);

    CompanyDto companyToDto(Company company);

    @Named("summary")
    @Mapping(target="staff", ignore=true)
    CompanyDto companyToDtoWithNoEmployees(Company company);

    @IterableMapping(qualifiedByName ="summary")
    List<CompanyDto> companiesToDtosWithNoEmployees(List<Company> companies);

    Company dtoToCompany(CompanyDto companyDto);
}
