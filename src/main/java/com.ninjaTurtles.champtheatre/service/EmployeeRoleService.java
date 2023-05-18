package com.ninjaTurtles.champtheatre.service;

import com.ninjaTurtles.champtheatre.models.EmployeeRole;

public interface EmployeeRoleService {
    EmployeeRole findRoleByEmployeeId(Long employeeId);
}
