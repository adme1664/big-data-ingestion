package com.adme.learning.data.ingestion.controller;

import com.adme.learning.data.ingestion.dto.EmployeeDto;
import com.adme.learning.data.ingestion.services.ManageEmployeeService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

  private final ManageEmployeeService manageEmployeeService;

  public EmployeeController(ManageEmployeeService manageEmployeeService) {
    this.manageEmployeeService = manageEmployeeService;
  }

  @GetMapping
  public List<EmployeeDto> getEmployees() {
    return manageEmployeeService.retrieveAllEmployees();
  }
}
