package com.ninjaTurtles.champtheatre.test;

import com.ninjaTurtles.champtheatre.bean.EmployeeBean;
import com.ninjaTurtles.champtheatre.models.Employee;
import com.ninjaTurtles.champtheatre.repository.EmployeeAccountRepository;
import com.ninjaTurtles.champtheatre.repository.EmployeeRepository;
import com.ninjaTurtles.champtheatre.service.impl.EmployeeManagementServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EmployeeManagementServiceImplTest {

    @InjectMocks
    private EmployeeManagementServiceImpl employeeManagementService;

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private EmployeeAccountRepository employeeAccountRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        employeeManagementService = new EmployeeManagementServiceImpl(employeeRepository, employeeAccountRepository);
    }

    @Test
    public void testGetAllEmployee() {
        // Create a list of test employees
        List<Employee> employees = new ArrayList<>();
        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setFirstName("John");
        employee1.setLastName("Doe");
        employee1.setEmail("john@example.com");

        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setFirstName("Jane");
        employee2.setLastName("Smith");
        employee2.setEmail("jane@example.com");

        employees.add(employee1);
        employees.add(employee2);

        // Mock the repository response
        when(employeeRepository.findAll()).thenReturn(employees);

        // Call the service method
        List<EmployeeBean> employeeBeans = employeeManagementService.getAllEmployee();

        // Verify the repository method was called
        verify(employeeRepository, times(1)).findAll();

        // Assert the result
        assertEquals(employees.size(), employeeBeans.size());
        for (int i = 0; i < employees.size(); i++) {
            Employee employee = employees.get(i);
            EmployeeBean employeeBean = employeeBeans.get(i);
            assertEquals(employee.getId(), employeeBean.getId());
            assertEquals(employee.getFirstName(), employeeBean.getFirstName());
            assertEquals(employee.getLastName(), employeeBean.getLastName());
            assertEquals(employee.getEmail(), employeeBean.getEmail());
        }
    }

    @Test
    public void testGetAllEmployee_NoEmployees() {
        // Mock an empty list of employees
        when(employeeRepository.findAll()).thenReturn(new ArrayList<>());

        // Call the service method
        List<EmployeeBean> employeeBeans = employeeManagementService.getAllEmployee();

        // Verify the repository method was called
        verify(employeeRepository, times(1)).findAll();

        // Assert the result
        assertEquals(0, employeeBeans.size());
    }

    //test for updateEmployeeDetails

    //test for updateEmployeeAccountStatus

    //test for deleteEmployeeAccount

    //test for addEmployeeAccount
}
