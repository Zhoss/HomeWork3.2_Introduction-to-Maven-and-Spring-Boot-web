package com.skypro.employee.record;

public class EmployeeRequest {
    private String firstName;
    private String lastName;
    private Integer department;
    private int salary;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getDepartment() {
        return department;
    }

    public int getSalary() {
        return salary;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
