package com.ninjaTurtles.champtheatre.test;

import com.ninjaTurtles.champtheatre.bean.ModuleBean;
import com.ninjaTurtles.champtheatre.bean.RoleBean;
import com.ninjaTurtles.champtheatre.models.*;
import com.ninjaTurtles.champtheatre.repository.RoleRepository;
import com.ninjaTurtles.champtheatre.service.RoleManagementService;
import com.ninjaTurtles.champtheatre.service.impl.RoleManagementServiceImpl;
import javafx.beans.value.ObservableBooleanValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RoleManagementServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

//    @InjectMocks
//    private RoleManagementService roleManagementService = new RoleManagementServiceImpl(roleRepository);

    @InjectMocks
    private RoleManagementServiceImpl roleManagementService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllRoles() {
        // Mocking the repository response
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(1L, "Admin", new HashSet<>(), new HashSet<>()));
        roles.add(new Role(2L, "User", new HashSet<>(), new HashSet<>()));
        when(roleRepository.findAll()).thenReturn(roles);

        // Calling the service method
        List<RoleBean> roleBeans = roleManagementService.getAllRoles();

        // Asserting the result
        assertEquals(roles.size(), roleBeans.size());
        assertEquals(roles.get(0).getId(), roleBeans.get(0).getId());
        assertEquals(roles.get(0).getRole(), roleBeans.get(0).getRole());
        assertEquals(roles.get(1).getId(), roleBeans.get(1).getId());
        assertEquals(roles.get(1).getRole(), roleBeans.get(1).getRole());

        // Verifying the repository method was called
        verify(roleRepository, times(1)).findAll();
        verifyNoMoreInteractions(roleRepository);
    }

    // Test code
    @Test
    public void testAddRole() {
        // Creating test data
        // Creating test data
        RoleBean roleBean = new RoleBean();
        roleBean.setId(1L);
        roleBean.setRole("Admin");
        roleBean.setModule(new HashSet<>());
        roleBean.setEmployee(new HashSet<>());

        ModuleBean moduleBean = new ModuleBean();
        moduleBean.setModuleName(roleBean.toString());

        // Adding modules to the roleBean
        Set<ModuleBean> modules = new HashSet<>();

        modules.add(moduleBean);

        // Calling the service method
        roleManagementService.addRole(roleBean, modules);

        // Verifying the repository method was called
        verify(roleRepository, times(1)).save(any(Role.class));
        verifyNoMoreInteractions(roleRepository);
    }

    @Test
    public void testCreateRoleWithModules() {
        // Mock data
        String roleName = "Admin";
        String roleDescription = "Administrator role";
        List<String> moduleNames = Arrays.asList("Module1", "Module2");

        // Create a mock RoleManagementService instance
        RoleManagementServiceImpl roleManagementService = new RoleManagementServiceImpl(roleRepository);

        // Mock the repository behavior
        when(roleRepository.save(any(Role.class))).thenReturn(new Role());

        // Call the createRole method
        RoleBean roleBean = new RoleBean();
        roleBean.setRole(roleName);
        roleBean.setDescription(roleDescription);

        Set<ModuleBean> modules = moduleNames.stream()
                .map(moduleName -> {
                    ModuleBean module = new ModuleBean();
                    module.setModuleName(moduleName);
                    return module;
                })
                .collect(Collectors.toSet());

        roleManagementService.addRole(roleBean, modules);

        // Verify that the roleRepository.save method was called
        verify(roleRepository, times(1)).save(any(Role.class));
    }

    @Test
    public void testCreateRoleWithoutModules() {
        // Mock data
        String roleName = "Admin";
        String roleDescription = "Administrator role";

        // Create a mock RoleManagementService instance
        RoleManagementServiceImpl roleManagementService = new RoleManagementServiceImpl(roleRepository);

        // Mock the repository behavior
        when(roleRepository.save(any(Role.class))).thenReturn(new Role());

        // Call the createRole method
        RoleBean roleBean = new RoleBean();
        roleBean.setRole(roleName);
        roleBean.setDescription(roleDescription);

        roleManagementService.addRole(roleBean, Collections.emptySet());

        // Verify that the roleRepository.save method was called
        verify(roleRepository, times(1)).save(any(Role.class));
    }

// =============================================================

    @Test
    public void testUpdateRole() {
        // Creating test data
        Role role = new Role(1L, "Admin", new HashSet<>(), new HashSet<>());
        Set<RoleModule> modules = new HashSet<>();

        RoleModule roleModule = new RoleModule();
        roleModule.setRole(role);
        // Set other properties of the RoleModule if needed

        modules.add(roleModule);

        // Calling the service method
//        roleManagementService.updateRole(role, modules);

        // Verifying the repository method was called
        verify(roleRepository, times(1)).save(role);
        verifyNoMoreInteractions(roleRepository);
    }


    @Test
    public void testDeleteRole() {
        // Creating test data
        Role role = new Role(1L, "Admin", new HashSet<>(), new HashSet<>());

        // Calling the service method
        roleManagementService.deleteRole(role);

        // Verifying the repository method was called
        verify(roleRepository, times(1)).delete(role);
        verifyNoMoreInteractions(roleRepository);
    }

//    @Test
//    public void testUpdateEmployeeRole() {
//        // Creating test data
//        Employee employee = new Employee();
//        Role existingRole = new Role(1L, "Admin", new HashSet<>(), new HashSet<>());
//        RoleModule newModule = new RoleModule(null, existingRole, new Module());
//        EmployeeRole employeeRole = new EmployeeRole(new EmployeeRoleId(employee.getId(), existingRole.getId()), employee, existingRole);
//
//        // Mocking the repository response
//        when(roleRepository.findById(employee.getId())).thenReturn(Optional.of(existingRole));
//
//        // Calling the service method
//        roleManagementService.updateEmployeeRole(employeeRole);
//
//        // Verifying the repository method was called
//        verify(roleRepository, times(1)).findAllByEmployee(employee);
//        verify(roleRepository, times(1)).save(existingRole);
//        verifyNoMoreInteractions(roleRepository);
//
//        // Asserting the updated role
//        assertEquals(newModule, existingRole.getModule());
//    }

}

