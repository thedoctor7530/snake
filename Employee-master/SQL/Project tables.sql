CREATE DATABASE Company;
USE Company;

CREATE TABLE EmployeeDetails(
employeeID INT PRIMARY KEY auto_increment,
employeeName VARCHAR(30) not null, 
employeeAddress VARCHAR(100),
NINumber VARCHAR(9) not null,
employeeBank VARCHAR(36) not null,
employeeSalary DECIMAL(9,2),
employeeDept VARCHAR(20));

CREATE TABLE SalesEmployee(
employeeID INT,
FOREIGN KEY(employeeID)
REFERENCES EmployeeDetails(employeeID),
commissionRate DECIMAL (4,2),
totalSales DECIMAL(9,2));

CREATE TABLE Project(
projectID INT PRIMARY KEY auto_increment,
projectName VARCHAR(30),
projectDuration VARCHAR(10));


CREATE TABLE ProjectEmployee(
projectID INT,
FOREIGN KEY(projectID)
REFERENCES Project(projectID),
employeeID INT,
FOREIGN KEY(employeeID)
REFERENCES EmployeeDetails(employeeID),
PRIMARY KEY (projectID, employeeID));

delimiter //

create procedure testData()

BEGIN
	INSERT into EmployeeDetails VALUES (1, "James Matchett", "Belfast", "PE284243G", "1234567890", 100000.00, 'Evolve');
	INSERT into EmployeeDetails VALUES (2, "Conor McCormick", "Larne", "PE234823G", "12513787890", 200000.00, 'Systems');
	INSERT into EmployeeDetails VALUES (3, "Dylan Robinson", "Strabane", "PE245J43G", "123258367890", 300000.00, 'Workday');
	INSERT into EmployeeDetails VALUES (4, "Maeve Donnelly", "Portadown", "PE2HJD84G", "1568367890", 400000.00, 'People');
	INSERT into EmployeeDetails VALUES (5, "Roisin Goodman", "Teconnaught", "PFFH243GH", "123S3842890", 500000.00, 'Digital Services');
  	INSERT into EmployeeDetails VALUES (6, "John Smith", "Dublin", "PFFHE83GH", "123S384450", 20000.00, 'Digital Services');
	INSERT into EmployeeDetails VALUES (7, "Joe Bloggs", "China", "P2YH243GH", "123S3842812", 10000.00, 'Evolve');
    INSERT into SalesEmployee VALUES (1, 0.14, 12000);
    INSERT into SalesEmployee VALUES (2, 0.25, 14000);
    INSERT into Project VALUES (1, "Fruit Shop", "60 Days");
    INSERT into Project VALUES (2, "Bank", "85 Days");
    INSERT into Project VALUES (3, "Hairdressers", "58 Days");
    INSERT into ProjectEmployee VALUES (1,1);
    INSERT into ProjectEmployee VALUES (1,2);
    INSERT into ProjectEmployee VALUES (1,3);
    INSERT into ProjectEmployee VALUES (2,4);
    INSERT into ProjectEmployee VALUES (2,5);
    INSERT into ProjectEmployee VALUES (3,6);
    INSERT into ProjectEmployee VALUES (3,7);
END //
delimiter ;


CALL testData();