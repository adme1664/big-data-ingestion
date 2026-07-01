package com.adme.learning.data.ingestion.repository;

import com.adme.learning.data.ingestion.dto.EmployeeDto;
import com.adme.learning.data.ingestion.entities.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

  EmployeeDto findByEmployeeNo(String employeeNo);
}
