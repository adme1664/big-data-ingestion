package com.adme.learning.data.ingestion;

import com.adme.learning.data.ingestion.services.ManageEmployeeService;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BigDataIngestionApplication {

	private final ManageEmployeeService manageEmployeeService;

  public BigDataIngestionApplication(ManageEmployeeService manageEmployeeService) {
    this.manageEmployeeService = manageEmployeeService;
  }

  public static void main(String[] args) {
		SpringApplication.run(BigDataIngestionApplication.class, args);
	}

	@PostConstruct
	public void loadData() {
		manageEmployeeService.loadEmployeeFromCsv();
	}




}
