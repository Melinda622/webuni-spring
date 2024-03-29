package web.uni.hr.meli.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import web.uni.hr.meli.dto.EmployeeDto;
import web.uni.hr.meli.service.EmployeeService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout="999999")
class HrControllerIT {

    private static final String BASE_URI = "/api/employees";

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    EmployeeService employeeService;

    @Test
    void testThatCreatedEmployeeIsListed() {
        List<EmployeeDto> employeesBefore = getAllEmployees();
        //List<Employee> employeesBefore = employeeService.findALl();
        EmployeeDto newEmployee = new EmployeeDto(9, "Jill Doe", "IT manager", 300000, LocalDateTime.of(2022, 1, 1, 9, 0));
        createEmployee(newEmployee);
        List<EmployeeDto> employeesAfter = getAllEmployees();

        assertThat(getAllEmployees().subList(0, employeesBefore.size())).usingRecursiveFieldByFieldElementComparator().
                containsExactlyElementsOf(employeesBefore);

        assertThat(employeesAfter.get(employeesAfter.size() - 1)).usingRecursiveComparison().ignoringFields("id","title").
                isEqualTo(newEmployee);
    }

    private void createEmployee(EmployeeDto newEmployee) {
        webTestClient.post().uri(BASE_URI).bodyValue(newEmployee).exchange().expectStatus().isOk();
    }

    private void createEmployeeWithInvalidArgument(EmployeeDto newEmployee) {
        webTestClient.post().uri(BASE_URI).bodyValue(newEmployee).exchange().expectStatus().isBadRequest();
    }


    private List<EmployeeDto> getAllEmployees() {
        List<EmployeeDto> responseList = webTestClient.get().uri(BASE_URI).exchange().expectStatus().isOk().
                expectBodyList(EmployeeDto.class).returnResult().getResponseBody();
        Collections.sort(responseList, (o1, o2) -> (int) (o1.getId() - o2.getId()));
        return responseList;
    }

    @Test
    void testPutMethod() {
        EmployeeDto newEmployee = new EmployeeDto(10, "Josephine Doe", "IT manager", 300000, LocalDateTime.of(2022, 1, 1, 9, 0));
        createEmployee(newEmployee);
        List<EmployeeDto> employeesBeforeModification = getAllEmployees();
        EmployeeDto employeeFromDb = employeesBeforeModification.stream().filter(e -> e.getName().equals(newEmployee.getName())).toList().get(0);
        EmployeeDto updatedEmployee = new EmployeeDto(employeeFromDb.getId(), "Josephine Doe", "IT developer", 300000, LocalDateTime.of(2022, 1, 1, 9, 0));
        updateEmployee(updatedEmployee);
        assertThat(getEmployeeById(updatedEmployee).getTitle()).isEqualTo(updatedEmployee.getTitle());
    }

    private void updateEmployee(EmployeeDto updatedEmployee) {
        long id = updatedEmployee.getId();
        webTestClient.put().uri("/api/employees/{id}", id).bodyValue(updatedEmployee).exchange().expectStatus().isOk();
    }

    private void updateEmployeeWithInvalidArgument(EmployeeDto updatedEmployee) {
        long id = updatedEmployee.getId();
        webTestClient.put().uri("/api/employees/{id}", id).bodyValue(updatedEmployee).exchange().expectStatus().isBadRequest();
    }

    private EmployeeDto getEmployeeById(EmployeeDto updatedEmployee) {
        long id = updatedEmployee.getId();
        EmployeeDto employeeDto = webTestClient.get().uri("/api/employees/{id}", id).exchange().expectStatus().isOk().
                expectBodyList(EmployeeDto.class).returnResult().getResponseBody().get(0);
        return employeeDto;
    }

    @Test
    void testNameIsNull() {
        List<EmployeeDto> employeesBefore = getAllEmployees();
        EmployeeDto newEmployee = new EmployeeDto(4, null, "IT manager", 300000, LocalDateTime.of(2022, 1, 1, 9, 0));
        createEmployeeWithInvalidArgument(newEmployee);
        List<EmployeeDto> employeesAfter = getAllEmployees();
        assertThat(employeesAfter).hasSameSizeAs(employeesBefore);
    }

    @Test
    void testPositionIsNull() {
        List<EmployeeDto> employeesBefore = getAllEmployees();
        EmployeeDto newEmployee = new EmployeeDto(4, "Jonathan Doe", null, 300000, LocalDateTime.of(2022, 1, 1, 9, 0));
        createEmployeeWithInvalidArgument(newEmployee);
        List<EmployeeDto> employeesAfter = getAllEmployees();
        assertThat(employeesAfter).hasSameSizeAs(employeesBefore);
    }

    @Test
    void testSalaryIsLowerThan1() {
        List<EmployeeDto> employeesBefore = getAllEmployees();
        EmployeeDto newEmployee = new EmployeeDto(4, "Jonathan Doe", "It manager", 0, LocalDateTime.of(2022, 1, 1, 9, 0));
        createEmployeeWithInvalidArgument(newEmployee);
        List<EmployeeDto> employeesAfter = getAllEmployees();
        assertThat(employeesAfter).hasSameSizeAs(employeesBefore);
    }

    @Test
    void testStartDateIsNotInThePast() {
        List<EmployeeDto> employeesBefore = getAllEmployees();
        EmployeeDto newEmployee = new EmployeeDto(4, "Jonathan Doe", "It manager", 0, LocalDateTime.of(2022, 7, 1, 9, 0));
        createEmployeeWithInvalidArgument(newEmployee);
        List<EmployeeDto> employeesAfter = getAllEmployees();
        assertThat(employeesAfter).hasSameSizeAs(employeesBefore);
    }

    @Test
    void testPutMethodWHenNameIsNull() {
        EmployeeDto newEmployee = new EmployeeDto(4, "Jonathan Doe", "IT manager", 300000, LocalDateTime.of(2022, 1, 1, 9, 0));
        createEmployee(newEmployee);
        List<EmployeeDto> employeesBeforeModification = getAllEmployees();
        EmployeeDto employeeFromDb = employeesBeforeModification.stream().filter(e -> e.getName().equals(newEmployee.getName())).toList().get(0);
        EmployeeDto updatedEmployee = new EmployeeDto(employeeFromDb.getId(), null, "IT developer", 300000, LocalDateTime.of(2022, 1, 1, 9, 0));
        updateEmployeeWithInvalidArgument(updatedEmployee);
        assertThat(getEmployeeById(updatedEmployee).getName()).isNotEqualTo(updatedEmployee.getName());
    }

    @Test
    void testPutMethodWHenPositionIsNull() {
        EmployeeDto newEmployee = new EmployeeDto(4, "Jonathan Doe", "IT manager", 300000, LocalDateTime.of(2022, 1, 1, 9, 0));
        createEmployee(newEmployee);
        List<EmployeeDto> employeesBeforeModification = getAllEmployees();
        EmployeeDto employeeFromDb = employeesBeforeModification.stream().filter(e -> e.getName().equals(newEmployee.getName())).toList().get(0);
        EmployeeDto updatedEmployee = new EmployeeDto(employeeFromDb.getId(), "Jonathan Doe", null, 300000, LocalDateTime.of(2022, 1, 1, 9, 0));
        updateEmployeeWithInvalidArgument(updatedEmployee);
        assertThat(getEmployeeById(updatedEmployee).getTitle()).isNotEqualTo(updatedEmployee.getTitle());
    }

    @Test
    void testPutMethodWHenSalaryIsLowerThan1() {
        EmployeeDto newEmployee = new EmployeeDto(4, "Jonathan Doe", "IT manager", 300000, LocalDateTime.of(2022, 1, 1, 9, 0));
        createEmployee(newEmployee);
        List<EmployeeDto> employeesBeforeModification = getAllEmployees();
        EmployeeDto employeeFromDb = employeesBeforeModification.stream().filter(e -> e.getName().equals(newEmployee.getName())).toList().get(0);
        EmployeeDto updatedEmployee = new EmployeeDto(employeeFromDb.getId(), "Jonathan Doe", "IT developer", 0, LocalDateTime.of(2022, 1, 1, 9, 0));
        updateEmployeeWithInvalidArgument(updatedEmployee);
        assertThat(getEmployeeById(updatedEmployee).getSalary()).isNotEqualTo(updatedEmployee.getSalary());
    }

    @Test
    void testPutMethodWHenStartDateIsNotInThePast() {
        EmployeeDto newEmployee = new EmployeeDto(4, "Jonathan Doe", "It manager", 300000, LocalDateTime.of(2022, 1, 1, 9, 0));
        createEmployee(newEmployee);
        List<EmployeeDto> employeesBeforeModification = getAllEmployees();
        EmployeeDto employeeFromDb = employeesBeforeModification.stream().filter(e -> e.getName().equals(newEmployee.getName())).toList().get(0);
        EmployeeDto updatedEmployee = new EmployeeDto(employeeFromDb.getId(), "Jonathan Doe", "IT developer", 300000, LocalDateTime.of(2022, 12, 1, 9, 0));
        updateEmployeeWithInvalidArgument(updatedEmployee);
        assertThat(getEmployeeById(updatedEmployee).getStartDate()).isNotEqualTo(updatedEmployee.getStartDate());
    }
}