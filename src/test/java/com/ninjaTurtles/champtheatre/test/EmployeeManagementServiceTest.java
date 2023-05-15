package com.ninjaTurtles.champtheatre.test;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.ninjaTurtles.champtheatre.models.EmployeeAccount;
import com.ninjaTurtles.champtheatre.repository.EmployeeAccountRepository;
import com.ninjaTurtles.champtheatre.repository.EmployeeRepository;
import com.ninjaTurtles.champtheatre.repository.RoleRepository;
import com.ninjaTurtles.champtheatre.service.EmployeeManagementService;
import com.ninjaTurtles.champtheatre.service.impl.EmployeeManagementServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class EmployeeManagementServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeAccountRepository employeeAccountRepository;
    @Mock
    private RoleRepository roleRepository;

    private EmployeeManagementService employeeManagementService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        employeeManagementService = new EmployeeManagementServiceImpl(employeeRepository, employeeAccountRepository, roleRepository);
    }

    @Test
    public void testDelete() {
        Long employeeId = 1L;

        employeeManagementService.delete(employeeId);

        verify(employeeRepository).deleteById(employeeId);
    }

//    @Test
//    public void testDeleteEmployeeAccount() {
//        Long employeeId = 1L;
//
//        when(employeeAccountRepository.findByEmployeeId(anyLong()))
//                .thenReturn(Optional.of(new EmployeeAccount(employeeId, EmployeeAccount.Status.ACTIVE)));
//
//        employeeManagementService.deleteEmployeeAccount(employeeId);
//
//        verify(employeeAccountRepository).findByEmployeeId(employeeId);
//        verify(employeeAccountRepository).save(new EmployeeAccount(employeeId, EmployeeAccount.Status.TERMINATED));
//    }
}
