-- CREATE DUMMY DATA--

-- FOR EMPLOYEES table
INSERT INTO employees (id, first_name, last_name, email)
SELECT 201, 'John', 'Doe', 'johndoe@example.com' FROM dual UNION ALL
SELECT 202, 'Jane', 'Smith', 'janesmith@example.com' FROM dual UNION ALL
SELECT 203, 'Bob', 'Johnson', 'bobjohnson@example.com' FROM dual;
--DELETE FROM employees;

-- for EMPLOYEE_ACCOUNTS 
INSERT INTO employee_accounts (id, created_date, modified_date, password, status, username, employee_id)
SELECT 1001, SYSTIMESTAMP, SYSTIMESTAMP, 'password1', 'ACTIVE', 'johndoe', 201 FROM dual UNION ALL
SELECT 1002, SYSTIMESTAMP, SYSTIMESTAMP, 'password2', 'ACTIVE', 'janesmith', 202 FROM dual UNION ALL
SELECT 1003, SYSTIMESTAMP, SYSTIMESTAMP, 'password3', 'INACTIVE', 'bobsmith', 203 FROM dual;

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
SELECT 101, SYSTIMESTAMP, SYSTIMESTAMP, 100, 'Theater A', 'AVAILABLE' FROM dual UNION ALL
SELECT 102, SYSTIMESTAMP, SYSTIMESTAMP, 200, 'Theater B', 'OCCUPIED' FROM dual UNION ALL
SELECT 103, SYSTIMESTAMP, SYSTIMESTAMP, 150, 'Theater C', 'MAINTENANCE' FROM dual UNION ALL
SELECT 104, SYSTIMESTAMP, SYSTIMESTAMP, 150, 'Theater D', 'MAINTENANCE' FROM dual;

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

--for RESERVATIONS table
INSERT INTO 
    reservations (id, created_date, modified_date, event_date, event_description, 
    event_type, status, booker_id, reviewer_id, theatre_id, start_time, end_time)
VALUES 
    (1001, SYSTIMESTAMP, SYSTIMESTAMP, DATE '2023-05-15', 'Mothers day celebration', 1, 
    'PENDING', 201, 202, 101, TIMESTAMP '2023-05-15 09:00:00', TIMESTAMP '2023-05-15 12:00:00');

--to Query for time
SELECT TO_CHAR(start_time, 'HH24:MI:SS') AS START_TIME, TO_CHAR(end_time, 'HH24:MI:SS') AS END_TIME FROM reservations;
INSERT INTO participants (id,employee_id, reservation_id, company, first_name, last_name)
VALUES (1001,201, 1001,NULL , 'John', 'Doe');

-- describe participants;