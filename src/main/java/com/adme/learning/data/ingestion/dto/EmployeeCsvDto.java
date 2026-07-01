package com.adme.learning.data.ingestion.dto;

import com.opencsv.bean.CsvBindByName;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeCsvDto {
    private String id;

    //EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO

    @CsvBindByName(column = "EMP_NO")
    private String employeeNo;

    @CsvBindByName(column = "ENAME")
    private String fullName;

    @CsvBindByName(column = "JOB")
    private String position;

    @CsvBindByName(column = "MGR")
    private String managerNo;

    @CsvBindByName(column = "HIREDATE")
    private String hireDate;

    @CsvBindByName(column = "SAL")
    private BigDecimal salary;

    @CsvBindByName(column = "COMM")
    private BigDecimal commission;

    @CsvBindByName(column = "DEPTNO")
    private String departmentNo;


}
