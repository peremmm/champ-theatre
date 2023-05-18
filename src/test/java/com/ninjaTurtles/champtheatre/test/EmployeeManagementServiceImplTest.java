package com.ninjaTurtles.champtheatre.test;

import com.ninjaTurtles.champtheatre.bean.EmployeeBean;
import com.ninjaTurtles.champtheatre.models.Employee;
import com.ninjaTurtles.champtheatre.models.EmployeeAccount;
import com.ninjaTurtles.champtheatre.models.EmployeeRole;
import com.ninjaTurtles.champtheatre.models.Role;
import com.ninjaTurtles.champtheatre.repository.EmployeeAccountRepository;
import com.ninjaTurtles.champtheatre.repository.EmployeeRepository;
import com.ninjaTurtles.champtheatre.repository.EmployeeRoleRepository;
import com.ninjaTurtles.champtheatre.repository.RoleRepository;
import com.ninjaTurtles.champtheatre.service.impl.EmployeeManagementServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EmployeeManagementServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private EmployeeAccountRepository employeeAccountRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private EmployeeRoleRepository employeeRoleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    private EmployeeManagementServiceImpl employeeManagementService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        employeeManagementService = new EmployeeManagementServiceImpl(employeeRepository, employeeAccountRepository, roleRepository, employeeRoleRepository, passwordEncoder/* other dependencies */);
    }

    @Test
    public void testGetAllEmployee() {
        Employee employee1 = Employee.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .build();

        Employee employee2 = Employee.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .email("janesmith@example.com")
                .build();

        List<Employee> mockEmployees = Arrays.asList(employee1, employee2);

        when(employeeRepository.findAll(any(Sort.class))).thenReturn(mockEmployees);

        List<EmployeeBean> result = employeeManagementService.getAllEmployee("name", true);

        assertEquals(2, result.size());
        verify(employeeRepository, times(1)).findAll(any(Sort.class));
    }

    @Test
    public void testAddEmployeeAccount() {
        // Arrange
        EmployeeBean employeeBean = EmployeeBean.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .build();
        Employee employee = employeeManagementService.mapToEmployee(employeeBean);
        EmployeeAccount employeeAccount = new EmployeeAccount(/* account details */);

        when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.empty());
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        when(employeeAccountRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(roleRepository.findByRole(anyString())).thenReturn(Optional.of(new Role()));
        when(employeeRoleRepository.save(any(EmployeeRole.class))).thenReturn(new EmployeeRole());
        when(employeeAccountRepository.save(any(EmployeeAccount.class))).thenReturn(employeeAccount);

        // Act
        EmployeeAccount result = employeeManagementService.addEmployeeAccount(employeeAccount, employeeBean);

        // Assert
        assertEquals(employeeAccount, result);

        verify(employeeRepository, times(1)).findByEmail(employee.getEmail());
        verify(employeeRepository, times(1)).save(any(Employee.class));
        verify(employeeAccountRepository, times(1)).findByUsername(anyString());
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(roleRepository, times(1)).findByRole(anyString());
        verify(employeeRoleRepository, times(2)).save(any(EmployeeRole.class));
        verify(employeeAccountRepository, times(1)).save(any(EmployeeAccount.class));
    }

    @Test
    public void testFindEmployeeIdByEmail() {
        String employeeEmail = "johndoe@example.com";
        Employee employee = Employee.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email(employeeEmail)
                .build();

        when(employeeRepository.findByEmail(employeeEmail)).thenReturn(Optional.of(employee));

        EmployeeBean result = employeeManagementService.findEmployeeIdByEmail(employeeEmail);

        assertEquals(employee.getId(), result.getId());
        assertEquals(employee.getFirstName(), result.getFirstName());
        assertEquals(employee.getLastName(), result.getLastName());
        assertEquals(employee.getEmail(), result.getEmail());

        verify(employeeRepository, times(1)).findByEmail(employeeEmail);
    }

    @Test
    public void testUpdateEmployee() {
        // Set up the necessary data and expectations for the mocked objects
        EmployeeBean employeeBean = EmployeeBean.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .reservations1(new ArrayList<>())
                .reservations2(new ArrayList<>())
                .employeeAccount(new EmployeeAccount(1L, "john.doe", "password", false))
                .createdOn(LocalDateTime.now())
                .updateOn(LocalDateTime.now())
                .build();
        Employee employee = employeeManagementService.mapToEmployee(employeeBean);
        employeeBean.setEmployeeAccount(employee.getEmployeeAccount());

        employeeManagementService.updateEmployee(employeeBean);
    }

    @Test
    public void testDeleteEmployeeAccount() {
        long employeeId = 1L;
        EmployeeAccount employeeAccount = new EmployeeAccount();
        employeeAccount.setStatus(EmployeeAccount.Status.ACTIVE);
        when(employeeAccountRepository.findByEmployeeId(employeeId)).thenReturn(Optional.of(employeeAccount));

        employeeManagementService.deleteEmployeeAccount(employeeId);

        assertEquals(EmployeeAccount.Status.TERMINATED, employeeAccount.getStatus());

        verify(employeeAccountRepository).findByEmployeeId(employeeId);
        verify(employeeAccountRepository).save(employeeAccount);
    }

    @Test
    public void testDeleteEmployeeAccount_NoEmployeeAccountFound() {
        long employeeId = 1L;
        when(employeeAccountRepository.findByEmployeeId(employeeId)).thenReturn(Optional.empty());
        employeeManagementService.deleteEmployeeAccount(employeeId);
        verify(employeeAccountRepository).findByEmployeeId(employeeId);
        verify(employeeAccountRepository, never()).save(any(EmployeeAccount.class));
    }
}
