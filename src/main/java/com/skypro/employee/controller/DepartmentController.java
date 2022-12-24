package com.skypro.employee.controller;

import com.skypro.employee.model.Employee;
import com.skypro.employee.service.DepartmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/{id}/employees")
    public Collection<Employee> getAllEmployeesInDepartment(@PathVariable("id") int departmentId) {
        return departmentService.getAllEmployeesInDepartment(departmentId);
    }

    @GetMapping("/{id}/salary/sum")
    public int getSalarySumInDepartment(@PathVariable("id") int departmentId) {
        return departmentService.getSalarySumInDepartment(departmentId);
    }

    @GetMapping("/{id}/salary/max")
    public int getSalaryMaxInDepartment(@PathVariable("id") int departmentId) {
        return departmentService.getSalaryMaxInDepartment(departmentId);
    }

    @GetMapping("/{id}/salary/min")
    public int getSalaryMinInDepartment(@PathVariable("id") int departmentId) {
        return departmentService.getSalaryMinInDepartment(departmentId);
    }

    @GetMapping("/employees")
    public Map<Integer, List<Employee>> getAllEmployeesGroupedByDepartments() {
        return departmentService.getAllEmployeesGroupedByDepartments();
    }

}
