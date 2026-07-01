package com.adme.learning.data.ingestion.entities;


import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {

  @Id
  private String id;
  private String employeeNo;
  private String fullName;
  private String position;
  private String managerNo;
  private String hireDate;
  private BigDecimal commission;
  private BigDecimal salary;

}
