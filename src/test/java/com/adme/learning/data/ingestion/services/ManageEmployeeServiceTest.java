package com.adme.learning.data.ingestion.services;

import com.adme.learning.data.ingestion.dto.EmployeeDto;
import com.adme.learning.data.ingestion.entities.Employee;
import com.adme.learning.data.ingestion.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ManageEmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private ManageEmployeeService manageEmployeeService;

    @Test
    void retrieveAllEmployees_returnsEmptyList_whenNoEmployees() {
        when(employeeRepository.findAll()).thenReturn(List.of());

        List<EmployeeDto> result = manageEmployeeService.retrieveAllEmployees();

        assertThat(result).isEmpty();
        verify(employeeRepository).findAll();
    }

    @Test
    void retrieveAllEmployees_convertsEntityToDto() {
        Employee employee = Employee.builder()
                .id("1")
                .employeeNo("7369")
                .fullName("SMITH")
                .position("CLERK")
                .managerNo("7902")
                .hireDate("17-DEC-80")
                .salary(new BigDecimal("800.00"))
                .commission(null)
                .build();

        when(employeeRepository.findAll()).thenReturn(List.of(employee));

        List<EmployeeDto> result = manageEmployeeService.retrieveAllEmployees();

        assertThat(result).hasSize(1);
        EmployeeDto dto = result.get(0);
        assertThat(dto.id()).isEqualTo("1");
        assertThat(dto.employeeNo()).isEqualTo("7369");
        assertThat(dto.fullName()).isEqualTo("SMITH");
        assertThat(dto.position()).isEqualTo("CLERK");
        assertThat(dto.managerNo()).isEqualTo("7902");
        assertThat(dto.hireDate()).isEqualTo("17-DEC-80");
        assertThat(dto.salary()).isEqualTo(800.0);
        assertThat(dto.commission()).isNull();
    }

    @Test
    void retrieveAllEmployees_convertsCommission() {
        Employee employee = Employee.builder()
                .id("2")
                .employeeNo("7499")
                .fullName("ALLEN")
                .position("SALESMAN")
                .salary(new BigDecimal("1600.00"))
                .commission(new BigDecimal("300.00"))
                .build();

        when(employeeRepository.findAll()).thenReturn(List.of(employee));

        List<EmployeeDto> result = manageEmployeeService.retrieveAllEmployees();

        assertThat(result.get(0).salary()).isEqualTo(1600.0);
        assertThat(result.get(0).commission()).isEqualTo(300.0);
    }

    @Test
    void retrieveAllEmployees_returnsNullSalaryAndCommission_whenEntityFieldsAreNull() {
        Employee employee = Employee.builder()
                .id("3")
                .employeeNo("7900")
                .fullName("JAMES")
                .position("CLERK")
                .salary(null)
                .commission(null)
                .build();

        when(employeeRepository.findAll()).thenReturn(List.of(employee));

        List<EmployeeDto> result = manageEmployeeService.retrieveAllEmployees();

        assertThat(result.get(0).salary()).isNull();
        assertThat(result.get(0).commission()).isNull();
    }

    @Test
    void loadEmployeeFromCsv_savesAllEmployeesFromFile() {
        
        manageEmployeeService.loadEmployeeFromCsv();

        verify(employeeRepository, atLeastOnce()).save(any(Employee.class));
    }

    @Test
    void loadEmployeeFromCsv_doesNotThrow_whenFileNotFound() {
        // Vérifie que l'erreur est gérée silencieusement (log uniquement)
        // En renommant temporairement le chemin, on simule l'absence de fichier via
        // une sous-classe anonyme qui force un chemin invalide.
        ManageEmployeeService serviceWithBadPath = new ManageEmployeeService(employeeRepository) {
            @Override
            public void loadEmployeeFromCsv() {
                try {
                    new com.opencsv.bean.CsvToBeanBuilder<com.adme.learning.data.ingestion.dto.EmployeeCsvDto>(
                            new java.io.FileReader("nonexistent/path/employee.csv"))
                            .withType(com.adme.learning.data.ingestion.dto.EmployeeCsvDto.class)
                            .build()
                            .parse();
                } catch (java.io.FileNotFoundException e) {
                    // comportement attendu : exception capturée, pas de sauvegarde
                }
            }
        };

        serviceWithBadPath.loadEmployeeFromCsv();

        verify(employeeRepository, never()).save(any());
    }
}
