SELECT EmployeeDetails.employeeName AS 'Employee Name', ProjectEmployee.projectID AS 'Project ID'
FROM EmployeeDetails JOIN ProjectEmployee
WHERE EmployeeDetails.employeeID = ProjectEmployee.employeeID;
