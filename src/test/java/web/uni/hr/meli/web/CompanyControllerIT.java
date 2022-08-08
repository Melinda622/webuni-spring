package web.uni.hr.meli.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import web.uni.hr.meli.dto.EmployeeDto;
import web.uni.hr.meli.model.Company;
import web.uni.hr.meli.service.CompanyService;
import web.uni.hr.meli.service.EmployeeService;
import web.uni.hr.meli.service.InitDbService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "999999")
public class CompanyControllerIT {

    private String BASE_URI = "/api/companies/employees/{id}";

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    CompanyService companyService;

    @Autowired
    InitDbService initDbService;

    @Autowired
    EmployeeService employeeService;

    @BeforeEach
    void resetDB() {
        initDbService.clearDB();
    }

    @Test
    void testAddEmployee() {
        initDbService.insertTestData();
        Company company =
                new Company(2, "345672", "IT consulting kft", "Budapest XII", null);
        long id = company.getId();
        List<EmployeeDto> employeesBefore = getALLEmployeesOfCompany(id);
        EmployeeDto newEmployee = new EmployeeDto(9, "Jill Doe", "IT manager", 300000, LocalDateTime.of(2022, 1, 1, 9, 0));
        createEmployee(id, newEmployee);
        List<EmployeeDto> employeesAfter = getALLEmployeesOfCompany(id);
        assertThat(employeesAfter.size()).isEqualTo(employeesBefore.size() + 1);
        assertThat(employeesAfter.contains(newEmployee)).isTrue();
    }

    private void createEmployee(long companyId, EmployeeDto newEmployee) {
        webTestClient.post().uri(BASE_URI, companyId).bodyValue(newEmployee).exchange().expectStatus().isOk();
    }

    private List<EmployeeDto> getAllEmployees() {
        List<EmployeeDto> responseList = webTestClient.get().uri("api/employees").exchange().expectStatus().isOk().
                expectBodyList(EmployeeDto.class).returnResult().getResponseBody();
        Collections.sort(responseList, (o1, o2) -> (int) (o1.getId() - o2.getId()));
        return responseList;
    }

    @Test
    void testUpdateEmployee() {
        initDbService.insertTestData();
        Company company =
                new Company(2, "345672", "IT consulting kft", "Budapest XII", null);
        long id = company.getId();
        List<EmployeeDto> employeesBefore = getALLEmployeesOfCompany(id);
        EmployeeDto newEmployee1 = new EmployeeDto(9, "Jill Doe", "IT manager", 300000, LocalDateTime.of(2022, 1, 1, 9, 0));
        EmployeeDto newEmployee2 = new EmployeeDto(10, "Jake Doe", "IT consultant", 200000, LocalDateTime.of(2022, 1, 1, 9, 0));
        List<EmployeeDto> newEmployees = new ArrayList<>();
        newEmployees.add(newEmployee1);
        newEmployees.add(newEmployee2);
        updateEmployees(id, newEmployees);
        List<EmployeeDto> employeesAfter = getALLEmployeesOfCompany(id);
        assertThat(employeesAfter).isEqualTo(newEmployees);
    }

    private List<EmployeeDto> getALLEmployeesOfCompany(Long companyId) {
        List<EmployeeDto> responseList = webTestClient.get().uri(BASE_URI, companyId).exchange().expectStatus().isOk().
                expectBodyList(EmployeeDto.class).returnResult().getResponseBody();
        Collections.sort(responseList, (o1, o2) -> (int) (o1.getId() - o2.getId()));
        return responseList;
    }

    private void updateEmployees(Long companyId, List<EmployeeDto> updatedEmployee) {
        webTestClient.put().uri(BASE_URI, companyId).bodyValue(updatedEmployee).exchange().expectStatus().isOk();
    }

    private void deleteEmployeesOfCompany(Long companyId, Long employeeId) {
        webTestClient.delete().uri("/api/companies/{companyID}/employees/delete/{employeeId}", companyId, employeeId).exchange().expectStatus().isOk();
    }

    @Test
    void testDeleteEmployees() {
        initDbService.insertTestData();
        EmployeeDto employeeDtoToBeDeleted = new EmployeeDto(3, "Jane Doe", "IT Consultant", 200000, LocalDateTime.of(2018, 6, 1, 9, 0));
        Company company =
                new Company(2, "345672", "IT consulting kft", "Budapest XII", null);
        long id = company.getId();
        List<EmployeeDto> employeesBefore = getALLEmployeesOfCompany(id);
        long employeeId = employeeDtoToBeDeleted.getId();
        deleteEmployeesOfCompany(id, employeeId);
        List<EmployeeDto> employeesAfter = getALLEmployeesOfCompany(id);
        boolean employeeWasDeleted = employeesAfter.contains(employeeDtoToBeDeleted);
        assertThat(employeeWasDeleted).isFalse();
    }
}
