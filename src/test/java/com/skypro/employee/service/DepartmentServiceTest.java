package com.skypro.employee.service;

import com.skypro.employee.model.Employee;
import com.skypro.employee.record.EmployeeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static java.lang.Integer.MAX_VALUE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {
    @Mock
    private EmployeeService employeeServiceMock;
    private DepartmentService out;
    private EmployeeRequest employee1;
    private EmployeeRequest employee2;
    private EmployeeRequest employee3;

    @BeforeEach
    public void initOut() {
        employee1 = new EmployeeRequest();
        employee1.setFirstName("Иван");
        employee1.setLastName("Иванов");
        employee1.setDepartment(1);
        employee1.setSalary(10000);

        employee2 = new EmployeeRequest();
        employee2.setFirstName("Дмитрий");
        employee2.setLastName("Дымов");
        employee2.setDepartment(1);
        employee2.setSalary(8000);

        employee3 = new EmployeeRequest();
        employee3.setFirstName("Алиса");
        employee3.setLastName("Лисова");
        employee3.setDepartment(4);
        employee3.setSalary(4000);

        out = new DepartmentService(employeeServiceMock);
    }

    @Test
    void shouldReturnAllEmployeesInDepartment() {
        Set<Employee> employeeSet = new HashSet<>((Set.of(
                new Employee(employee1.getFirstName(),
                        employee1.getLastName(),
                        employee1.getDepartment(),
                        employee1.getSalary())
        )));
        when(employeeServiceMock.getAllEmployees()).thenReturn(employeeSet);

        assertIterableEquals(employeeSet, out.getAllEmployeesInDepartment(1));
    }

    @Test
    void shouldThrowResponseStatusExceptionWhenAsDepartmentNumberPassedNull() {
        assertThrows(ResponseStatusException.class,() -> out.getSalarySumInDepartment(null));
    }

    @Test
    void shouldThrowResponseStatusExceptionWhenAsDepartmentNumberPassedNonValidNumber() {
        Set<Employee> employeeSet = new HashSet<>((Set.of(
                new Employee(employee1.getFirstName(),
                        employee1.getLastName(),
                        employee1.getDepartment(),
                        employee1.getSalary())
        )));
        when(employeeServiceMock.getAllEmployees()).thenReturn(employeeSet);
        assertThrows(ResponseStatusException.class,() -> out.getSalarySumInDepartment(MAX_VALUE));
    }

    @Test
    void shouldReturnSalarySumInDepartment() {
        Set<Employee> employeeSet = new HashSet<>((Set.of(
                new Employee(employee1.getFirstName(),
                        employee1.getLastName(),
                        employee1.getDepartment(),
                        employee1.getSalary())
        )));
        when(employeeServiceMock.getAllEmployees()).thenReturn(employeeSet);

        assertEquals(10000, out.getSalarySumInDepartment(1));
    }

    @Test
    void shouldReturnSalaryMaxInDepartment() {
        Set<Employee> employeeSet = new HashSet<>((Set.of(
                new Employee(employee1.getFirstName(),
                        employee1.getLastName(),
                        employee1.getDepartment(),
                        employee1.getSalary()),
                new Employee(employee2.getFirstName(),
                        employee2.getLastName(),
                        employee2.getDepartment(),
                        employee2.getSalary())
        )));
        when(employeeServiceMock.getAllEmployees()).thenReturn(employeeSet);

        assertEquals(10000, out.getSalaryMaxInDepartment(1));
    }

    @Test
    void shouldReturnSalaryMinInDepartment() {
        Set<Employee> employeeSet = new HashSet<>((Set.of(
                new Employee(employee1.getFirstName(),
                        employee1.getLastName(),
                        employee1.getDepartment(),
                        employee1.getSalary()),
                new Employee(employee2.getFirstName(),
                        employee2.getLastName(),
                        employee2.getDepartment(),
                        employee2.getSalary())
        )));
        when(employeeServiceMock.getAllEmployees()).thenReturn(employeeSet);

        assertEquals(8000, out.getSalaryMinInDepartment(1));
    }

    @Test
    void shouldReturnAllEmployeesGroupedByDepartments() {
        Map<Integer, List<Employee>> employees = new LinkedHashMap<>();
        Employee employeeIvan = new Employee(employee1.getFirstName(), employee1.getLastName(), employee1.getDepartment(), employee1.getSalary());
        Employee employeeDmitriy = new Employee(employee2.getFirstName(), employee2.getLastName(), employee2.getDepartment(), employee2.getSalary());
        Employee employeeAlisa = new Employee(employee3.getFirstName(), employee3.getLastName(), employee3.getDepartment(), employee3.getSalary());

        employees.put(1, new ArrayList<>(List.of(employeeIvan, employeeDmitriy)));
        employees.put(4, new ArrayList<>(List.of(employeeAlisa)));

        Set<Employee> employeeSet = new LinkedHashSet<>();
        employeeSet.add(employeeIvan);
        employeeSet.add(employeeDmitriy);
        employeeSet.add(employeeAlisa);

        when(employeeServiceMock.getAllEmployees()).thenReturn(employeeSet);

        assertEquals(employees, out.getAllEmployeesGroupedByDepartments());
    }
}