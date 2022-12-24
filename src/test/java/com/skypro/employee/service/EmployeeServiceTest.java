package com.skypro.employee.service;

import com.skypro.employee.model.Employee;
import com.skypro.employee.record.EmployeeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {
    private final EmployeeService out = new EmployeeService();
    private EmployeeRequest employee1;
    private EmployeeRequest employee2;
    private EmployeeRequest employee3;

    @BeforeEach
    public void setUp() {
        employee1 = new EmployeeRequest();
        employee1.setFirstName("Иван");
        employee1.setLastName("Иванов");
        employee1.setDepartment(1);
        employee1.setSalary(10000);

        employee2 = new EmployeeRequest();
        employee2.setFirstName("Дмитрий");
        employee2.setLastName("Дымов");
        employee2.setDepartment(3);
        employee2.setSalary(8000);

        employee3 = new EmployeeRequest();
        employee3.setFirstName("Алиса");
        employee3.setLastName("Лисова");
        employee3.setDepartment(4);
        employee3.setSalary(4000);

        out.addEmployee(employee1);
        out.addEmployee(employee2);
        out.addEmployee(employee3);
    }

    @Test
    void shouldReturnAllEmployees() {
        List<Employee> expected = new ArrayList<>(out.getAllEmployees());

        List<EmployeeRequest> actual = new ArrayList<>();
        actual.add(employee1);
        actual.add(employee2);
        actual.add(employee3);

        for (int i = 0; i < 3; i++) {
            assertEquals(expected.get(i).getFirstName(), actual.get(i).getFirstName());
            assertEquals(expected.get(i).getLastName(), actual.get(i).getLastName());
            assertEquals(expected.get(i).getDepartment(), actual.get(i).getDepartment());
            assertEquals(expected.get(i).getSalary(), actual.get(i).getSalary());
        }
    }

    @Test
    void getAllEmployeesNotNull() {
        Collection<Employee> expected = out.getAllEmployees();

        assertNotNull(expected);
    }

    @Test
    void shouldThrowResponseStatusExceptionWhenAddEmptyEmployeeName() {
        EmployeeRequest newEmployee = new EmployeeRequest();
        newEmployee.setFirstName(" ");
        newEmployee.setLastName(null);
        newEmployee.setDepartment(1);
        newEmployee.setSalary(10000);

        assertThrows(ResponseStatusException.class, () -> out.addEmployee(newEmployee));
    }

    @Test
    void doNotAddSameEmployee() {
        EmployeeRequest newEmployee = new EmployeeRequest();
        newEmployee.setFirstName("Иван");
        newEmployee.setLastName("Иванов");
        newEmployee.setDepartment(1);
        newEmployee.setSalary(10000);

        out.addEmployee(newEmployee);
        assertEquals(out.getAllEmployees().size(), 3);
    }

    @Test
    void shouldReturnSalarySum() {
        int result = out.getSalarySum();
        assertEquals(result, 22000);
    }

    @Test
    void shouldReturnEmployeeWithMinSalary() {
        Employee expected = out.getEmployeeWithMinSalary();
        assertEquals(expected.getFirstName(), employee3.getFirstName());
        assertEquals(expected.getLastName(), employee3.getLastName());
        assertEquals(expected.getDepartment(), employee3.getDepartment());
        assertEquals(expected.getSalary(), employee3.getSalary());
    }

    @Test
    void shouldReturnEmployeeWithMaxSalary() {
        Employee expected = out.getEmployeeWithMaxSalary();
        assertEquals(expected.getFirstName(), employee1.getFirstName());
        assertEquals(expected.getLastName(), employee1.getLastName());
        assertEquals(expected.getDepartment(), employee1.getDepartment());
        assertEquals(expected.getSalary(), employee1.getSalary());
    }

    @Test
    void shouldReturnEmployeesWithHighSalary() {
        List<Employee> expected = new ArrayList<>(out.getEmployeesWithHighSalary());

        List<EmployeeRequest> actual = new ArrayList<>();
        actual.add(employee1);
        actual.add(employee2);

        for (int i = 0; i < 2; i++) {
            assertEquals(expected.get(i).getFirstName(), actual.get(i).getFirstName());
            assertEquals(expected.get(i).getLastName(), actual.get(i).getLastName());
            assertEquals(expected.get(i).getDepartment(), actual.get(i).getDepartment());
            assertEquals(expected.get(i).getSalary(), actual.get(i).getSalary());
        }
    }
}