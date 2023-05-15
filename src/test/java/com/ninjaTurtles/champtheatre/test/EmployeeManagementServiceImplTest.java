//package com.ninjaTurtles.champtheatre.service.impl;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Sort;
//
//import com.ninjaTurtles.champtheatre.bean.EmployeeBean;
//import com.ninjaTurtles.champtheatre.exception.ServiceException;
//import com.ninjaTurtles.champtheatre.models.Employee;
//import com.ninjaTurtles.champtheatre.repository.EmployeeAccountRepository;
//import com.ninjaTurtles.champtheatre.repository.EmployeeRepository;
//import com.ninjaTurtles.champtheatre.repository.RoleRepository;
//
//@SpringBootTest
//public class EmployeeManagementServiceImplTest {
//
//    @InjectMocks
//    private EmployeeManagementServiceImpl employeeService;
//
//    @Mock
//    private EmployeeRepository employeeRepository;
//
//    @Mock
//    private EmployeeAccountRepository employeeAccountRepository;
//
//    @Mock
//    private RoleRepository roleRepository;
//
//    @Test
//    void testGetAllEmployee() {
//        // given
//        List<Employee> employees = new ArrayList<>();
//        employees.add(new Employee(1L, "John", "Doe", "johndoe@example.com"));
//        employees.add(new Employee(2L, "Jane", "Smith", "janesmith@example.com"));
//        when(employeeRepository.findAll(Sort.by(Sort.Direction.ASC, "firstName")))
//                .thenReturn(employees);
//
//        // when
//        List<EmployeeBean> employeeBeans = employeeService.getAllEmployee("firstName", true);
//
//        // then
//        assertEquals(2, employeeBeans.size());
//        assertEquals("John", employeeBeans.get(0).getFirstName());
//        assertEquals("Doe", employeeBeans.get(0).getLastName());
//        assertEquals("johndoe@example.com", employeeBeans.get(0).getEmail());
//        assertEquals("Jane", employeeBeans.get(1).getFirstName());
//        assertEquals("Smith", employeeBeans.get(1).getLastName());
//        assertEquals("janesmith@example.com", employeeBeans.get(1).getEmail());
//    }
//
//    @Test
//    void testRegisterWithExistingEmail() {
//        // given
//        EmployeeBean employeeBean = new EmployeeBean();
//        employeeBean.setEmail("johndoe@example.com");
//        when(employeeRepository.findByEmail("johndoe@example.com")).thenReturn(Optional.of(new Employee()));
//
//        // when, then
//        assertThrows(ServiceException.class, () -> {
//            employeeService.register(employeeBean);
//        });
//    }
//
//    @Test
//    void testRegister() throws ServiceException {
//        // given
//        EmployeeBean employeeBean;
//        employeeBean = new EmployeeBean();
//        employeeBean.setEmail("johndoe@example.com");
//        when(employeeRepository.findByEmail("johndoe@example.com")).thenReturn(Optional.empty());
//        when(employeeRepository.save(new Employee("John", "Doe", "johndoe@example.com")))
//                .thenReturn(new Employee(1L, "John", "Doe", "johndoe@example.com"));
//
//        // when
//        Employee employee = employeeService.register(employeeBean);
//
//        // then
//        assertNotNull(employee.getId());
//        assertEquals("John", employee.getFirstName());
//        assertEquals("Doe", employee.getLastName());
//        assertEquals("johndoe@example.com", employee.getEmail());
//    }
//}
