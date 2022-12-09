package com.skypro.employee.service;

import com.skypro.employee.model.Employee;
import com.skypro.employee.record.EmployeeRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final Map<Integer, Employee> employees = new HashMap<>();

    public Collection<Employee> getAllEmployees() {
        return this.employees.values();
    }

    public Employee addEmployee(EmployeeRequest employeeRequest) {
        if (employeeRequest.getFirstName() == null || employeeRequest.getLastName() == null) {
            throw new IllegalArgumentException("Employee name should be set");
        }
        Employee employee = new Employee(employeeRequest.getFirstName(),
                employeeRequest.getLastName(),
                employeeRequest.getDepartment(),
                employeeRequest.getSalary());
        this.employees.put(employee.getId(), employee);
        return employee;
    }

    public int getSalarySum() {
        return employees.values().stream()
                .mapToInt(Employee::getSalary)
                .sum();
    }

    public Employee getEmployeeWithMinSalary() {
        return this.employees.values()
                .stream()
                .min(Comparator.comparing(Employee::getSalary)).get();
    }

    public Employee getEmployeeWithMaxSalary() {
        return this.employees.values()
                .stream()
                .max(Comparator.comparing(Employee::getSalary)).get();
    }

    public int averageSalary() {
        int i = getSalarySum();
        int j = getAllEmployees().size();
        return i / j;
    }

    public Collection<Employee> getEmployeesWithHighSalary() {
        return this.employees.values()
                .stream()
                .filter(s -> s.getSalary() > averageSalary())
                .collect(Collectors.toList());
    }
}
