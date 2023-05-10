-- CREATE DUMMY DATA--

-- FOR EMPLOYEES table
INSERT INTO employees (id, first_name, last_name, email)
SELECT 201, 'John', 'Doe', 'johndoe@example.com' FROM dual UNION ALL
SELECT 202, 'Jane', 'Smith', 'janesmith@example.com' FROM dual UNION ALL
SELECT 203, 'Bob', 'Johnson', 'bobjohnson@example.com' FROM dual;
--DELETE FROM employees;

-- for EMPLOYEE_ACCOUNTS 
INSERT INTO employee_accounts (id, created_date, modified_date, password, status, username, employee_id)
SELECT 1001, '2022-01-01', '2022-01-01', 'password1', 'active', 'johndoe', 1 FROM dual UNION ALL
SELECT 1002, '2022-01-01', '2022-01-01', 'password2', 'inactive', 'janesmith', 2 FROM dual UNION ALL
SELECT 1003, '2022-01-01', '2022-01-01', 'password3', 'active', 'bobsmith', 3 FROM dual;

-- for MODULES table
INSERT INTO modules (id,module)
SELECT 101, 'EmployeeAccountManagement' FROM dual UNION ALL
SELECT 102, 'ReservationConstraintManagement' FROM dual UNION ALL
SELECT 103, 'ReservationManagement' FROM dual UNION ALL
SELECT 104, 'TheatreManagement' FROM dual UNION ALL
SELECT 105, 'ReservationManagement' FROM dual UNION ALL
SELECT 106, 'PasswordManagement' FROM dual UNION ALL
SELECT 107, 'RoleManagement' FROM dual;

-- for THEATERS table
INSERT INTO theaters (id, created_date, modified_date, capacity, name, status)
SELECT 101, '2022-01-01', '2022-01-01', 100, 'Theater A', 'AVAILABLE' FROM dual UNION ALL
SELECT 102, '2022-01-01', '2022-01-01', 200, 'Theater B', 'OCCUPIED' FROM dual UNION ALL
SELECT 103, '2022-01-01', '2022-01-01', 150, 'Theater C', 'MAINTENANCE' FROM dual UNION ALL
SELECT 104, '2022-01-01', '2022-01-01', 150, 'Theater D', 'MAINTENANCE' FROM dual;

-- for ROLES table
INSERT INTO roles (id, role)
SELECT 101, 'Administrator' FROM dual UNION ALL
SELECT 102, 'Reservation Coordinator' FROM dual UNION ALL
SELECT 103, 'User' FROM dual;

-- for ROLE_MODULE table
INSERT INTO role_module (module_id,role_id)
SELECT 101, 101 FROM dual UNION ALL
SELECT 102, 101 FROM dual UNION ALL
SELECT 103, 101 FROM dual UNION ALL
SELECT 104, 101 FROM dual UNION ALL
SELECT 105, 101 FROM dual UNION ALL
SELECT 106, 101 FROM dual UNION ALL
SELECT 107, 101 FROM dual UNION ALL
SELECT 103, 102 FROM dual UNION ALL
SELECT 105, 102 FROM dual UNION ALL
SELECT 106, 102 FROM dual UNION ALL
SELECT 105, 103 FROM dual UNION ALL
SELECT 106, 103 FROM dual;