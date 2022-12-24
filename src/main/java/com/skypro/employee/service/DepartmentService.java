package com.skypro.employee.service;

import com.skypro.employee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DepartmentService {
    private final EmployeeService employeeService;

    public DepartmentService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public boolean checkDepartmentId(Integer departmentId) {
        for (Employee employee : employeeService.getAllEmployees()) {
            if (employee.getDepartment() == departmentId) {
                return true;
            }
        }
        return false;
    }

    public Collection<Employee> getAllEmployeesInDepartment(Integer departmentId) {
        if (departmentId != null && checkDepartmentId(departmentId)) {
            return employeeService.getAllEmployees().stream()
                    .filter(e -> e.getDepartment() == departmentId)
                    .collect(Collectors.toList());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public int getSalarySumInDepartment(Integer departmentId) {
        if (departmentId != null && checkDepartmentId(departmentId)) {
            return getAllEmployeesInDepartment(departmentId).stream()
                    .mapToInt(Employee::getSalary)
                    .sum();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public int getSalaryMaxInDepartment(Integer departmentId) {
        if (departmentId != null && checkDepartmentId(departmentId)) {
            return getAllEmployeesInDepartment(departmentId).stream()
                    .mapToInt(Employee::getSalary)
                    .max().getAsInt();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public int getSalaryMinInDepartment(Integer departmentId) {
        if (departmentId != null && checkDepartmentId(departmentId)) {
            return getAllEmployeesInDepartment(departmentId).stream()
                    .mapToInt(Employee::getSalary)
                    .min().getAsInt();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public Map<Integer, List<Employee>> getAllEmployeesGroupedByDepartments() {
        Map<Integer, List<Employee>> employees = new HashMap<>();

        List<Employee> employeeList = employeeService.getAllEmployees().stream()
                .sorted(Comparator.comparing(Employee::getDepartment))
                .toList();

        LinkedHashSet<Integer> departmentLinkedSet = employeeService.getAllEmployees().stream()
                .mapToInt(Employee::getDepartment)
                .sorted()
                .boxed()
                .collect(Collectors.toCollection(LinkedHashSet::new));

        for (Integer integer : departmentLinkedSet) {
            List<Employee> tmpList = new ArrayList<>();
            for (Employee employee : employeeList) {
                if (employee.getDepartment() == integer) {
                    tmpList.add(employee);
                }
            }
            employees.put(integer, tmpList);
        }
        return employees;
    }
}
