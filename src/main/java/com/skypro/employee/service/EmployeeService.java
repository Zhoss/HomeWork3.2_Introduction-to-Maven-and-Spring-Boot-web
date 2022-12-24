package com.skypro.employee.service;

import com.skypro.employee.model.Employee;
import com.skypro.employee.record.EmployeeRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        boolean checkFirstName = StringUtils.isEmpty(employeeRequest.getFirstName()) ||
                StringUtils.isBlank(employeeRequest.getFirstName()) ||
                !StringUtils.isAlpha(employeeRequest.getFirstName());

        boolean checkLastName = StringUtils.isEmpty(employeeRequest.getLastName()) ||
                StringUtils.isBlank(employeeRequest.getLastName()) ||
                !StringUtils.isAlpha(employeeRequest.getLastName());

        if (checkFirstName || checkLastName) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else {
            Employee employee = new Employee(StringUtils.capitalize(employeeRequest.getFirstName()),
                    StringUtils.capitalize(employeeRequest.getLastName()),
                    employeeRequest.getDepartment(),
                    employeeRequest.getSalary());
            if (!this.employees.containsValue(employee)) {
                this.employees.put(employee.getId(), employee);
            }
            return employee;
        }
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
