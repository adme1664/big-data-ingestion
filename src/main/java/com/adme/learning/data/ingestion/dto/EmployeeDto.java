package com.adme.learning.data.ingestion.dto;

import com.adme.learning.data.ingestion.entities.Employee;

public record EmployeeDto(
    String id,
    String employeeNo,
    String fullName,
    String position,
    String managerNo,
    String hireDate,
    String departmentNo,
    Double salary,
    Double commission) {

  public EmployeeDto toEmployeeDto(Employee employee) {
    return new EmployeeDto(employee.getId(),
        employee.getEmployeeNo(),
        employee.getFullName(),
        employee.getPosition(),
        employee.getManagerNo(),
        employee.getHireDate(),
        null,
        employee.getSalary() != null ? employee.getSalary().doubleValue() : null,
        employee.getCommission() != null ? employee.getCommission().doubleValue() : null);
  }

}
