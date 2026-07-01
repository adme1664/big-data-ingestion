package com.adme.learning.data.ingestion.services;

import com.adme.learning.data.ingestion.dto.EmployeeCsvDto;
import com.adme.learning.data.ingestion.dto.EmployeeDto;
import com.adme.learning.data.ingestion.entities.Employee;
import com.adme.learning.data.ingestion.repository.EmployeeRepository;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ManageEmployeeService {

  private final EmployeeRepository employeeRepository;

  private final Logger logger = LoggerFactory.getLogger(ManageEmployeeService.class);


  public ManageEmployeeService(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  public void loadEmployeeFromCsv() {
    try {
      logger.info("Loading employee from csv");
      List<EmployeeCsvDto> employeeCsvDtos = new CsvToBeanBuilder<EmployeeCsvDto>(
          new FileReader("src/main/resources/employee.csv"))
          .withType(EmployeeCsvDto.class)
          .build()
          .parse();

      employeeCsvDtos.stream().map(this::convertEmployeeDtoToEmployee)
          .forEach(entity -> {
            employeeRepository.save(entity);
            logger.info("Employee {} has been loaded successfully", entity.getId());
          });

      logger.info("Found {} employee records", employeeCsvDtos.size());
    } catch (FileNotFoundException e) {
      logger.error(e.getMessage());
    }
  }

  public List<EmployeeDto> retrieveAllEmployees() {
    List<EmployeeDto> employeeDtos = new ArrayList<>();
    employeeRepository.findAll().forEach(entity -> {
      EmployeeDto employeeDto = new EmployeeDto(entity.getId(),
          entity.getEmployeeNo(),
          entity.getFullName(),
          entity.getPosition(),
          entity.getManagerNo(),
          entity.getHireDate(),
          null,
          entity.getSalary() != null ? entity.getSalary().doubleValue() : null,
          entity.getCommission() != null ? entity.getCommission().doubleValue() : null);
      employeeDtos.add(employeeDto);
    });
   return employeeDtos;
  }

  private Employee convertEmployeeDtoToEmployee(EmployeeCsvDto employeeCsvDto) {
    return Employee.builder()
        .employeeNo(employeeCsvDto.getEmployeeNo())
        .fullName(employeeCsvDto.getFullName())
        .position(employeeCsvDto.getPosition())
        .managerNo(employeeCsvDto.getManagerNo())
        .hireDate(employeeCsvDto.getHireDate())
        .salary(employeeCsvDto.getSalary())
        .commission(employeeCsvDto.getCommission())
        .build();
  }
}
