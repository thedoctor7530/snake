package app;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;



import app.MySQLHandler;

public class Executable {

	public Executable() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws SQLException, IOException, InterruptedException {
		// TODO Auto-generated method stub
		MySQLHandler ms = login();
		DisplayMenu();
		int Choice = GetMenuChoice();
		
		//Story 1: add an employee
		if(Choice == 1) {
			addEmployeeDetails(ms);
		}
		
		//Story 2: Generate a report containing employees per department
		if(Choice == 2) {
			EmployeesPerDepartment(ms);
		}
		
		//Story 3: Add a Sales Employee
		if(Choice == 3) {
			ResultSet NewEmployee = addEmployeeDetails(ms);
			if(NewEmployee != null) {
				String EmpId = "";
					while(NewEmployee.next()) {
						EmpId = NewEmployee.getString("employeeID");
					}
					
					//System.out.println(EmpId);
					addSalesEmployee(ms,EmpId);
					System.out.println("");
					System.out.println("SalesEmployee added");
					
			}
			
		}
		
		//Story 4: Report of employees net pay
		if(Choice == 4) {
			ResultSet repo = getNetPay(ms);
			while(repo.next()) {
				
				//TODO, divide by 12 and calc tax etc
				float totes = Float.valueOf((repo.getString("total")));
				totes = (float) ((totes/12) * 0.75);
				String Out = "$"+totes+" | Name: "+repo.getString("employeeName")
				+ " | " + repo.getString("employeeID");
				
				System.out.println(Out);
			}
		}
		
		//Story 5: Sort Sales Employees by highest sales total
		if(Choice == 5) {
			ResultSet SalesTot = getSalesTotal(ms);
			String lastName = "";
			String lastSalesTotal = "";
			while(SalesTot.next()) {
				String Out = SalesTot.getString("employeeName") +" | Sales total: "
						+ SalesTot.getString("totalSales");
				
				lastName = SalesTot.getString("employeeName");
				lastSalesTotal = SalesTot.getString("totalSales");
				
				System.out.println(Out);
			}
			
			System.out.println("");
			String lm = "Give the most high-fives to "+lastName+" with a sales total of $"+lastSalesTotal;
			System.out.println(lm);
		}
		
		//String InsertStatement = "INSERT into EmployeeDetails VALUES (1, \"James Matchett\", \"Belfast\", \"PE284243G\", \"1234567890\", 100000.00);";
		//addEmployeeDetails(ms);
		
		/*

		ResultSet rs = ms.Query("SELECT * FROM EmployeeDetails");		
		try {
			while (rs.next()) {
				
				
				String out = String.format("%d number, %s name, %s ni", rs.getInt("employeeID"),
						rs.getString("employeeName"), rs.getString("NINumber"));
				
				System.out.println(out);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		*/
	}
	
	public static void EmployeesPerDepartment(MySQLHandler ms) throws SQLException {
		String Query = "SELECT * FROM EmployeeDetails\n" + 
				"ORDER BY employeeDept;";
		ResultSet rs = ms.Query(Query);
		
		//used to work out if a break should be inserted
		//i.e. between two different departments
		
		String lastRs = "";
		
		while(rs.next()) {
			
			String Dept = rs.getString("employeeDept");
			if(Dept == "" || Dept == null) {
				Dept = "No Department";
			}
			
			if(!Dept.equals(lastRs)) {
				System.out.println("");
			}
			
			lastRs = Dept;
			
			String out = String.format("Emp ID: %d | %s | %s | %s", rs.getInt("employeeID"),
					rs.getString("employeeName"), rs.getString("NINumber"), Dept);
			
			System.out.println(out);
		}
	}
	
	public static void DisplayMenu() {
		String[] m = new String[10];
		m[0] = ("1. Enter a new Employee"); //story 1
		m[1] = ("2. Employee Report"); //story 2
		m[2] = ("3. Add a Sales Employee"); //story 3
		m[3] = ("4. Employee net pay report"); //story 4
		m[4] = ("5. Highest Sales Employee sales"); //story 5
		m[5] = ("6. Projects sub-menu"); //stories 6 -> 7 here
		m[6] = ("7. Log-out");
		
		System.out.println("");
		for(String s : m) {
			if(s!= null) {
			System.out.println(s);
			}
		}
		System.out.println("");
		System.out.println("Enter a number above");
		
	}
	
	public static int GetMenuChoice() {
		DisplayMenu();
				
		Scanner s  = new Scanner(System.in);
		String err;
		String TempString;
		int Choice;
		
		while(true) {
			try {
			TempString = s.nextLine();
			
			Choice = Integer.parseInt(TempString);
			if(Choice < 7 && Choice > 0) {
				return Choice;
			} else {
				if(Choice > 6) {
					err = (Choice + " is too large");
				} else {
					err = (Choice + " is too small");
				}
			}
			
			}
			catch(NumberFormatException e) {
				err = ("Is not a number");
			}
			
			System.out.println("");
			DisplayMenu();
			System.out.println(err + ", try again.");
		}
		
		
	}
	
	public static MySQLHandler login() throws SQLException, InterruptedException {
		//MySQLHandler ms = new MySQLHandler();
		Scanner s = new Scanner(System.in);
		MySQLHandler ms = new MySQLHandler();
		Boolean success = false;
		while(!success) {
			try {
				String user = "";
				String pwd = "";
				String Hostname = "jdbc:mysql://localhost/Company?useSSL=false";
				System.out.println("Enter username");
				user = s.nextLine();
				System.out.println("Enter password");
				pwd = s.nextLine();
				ms = new MySQLHandler(Hostname,user,pwd);
				success = ms.Connected;
				if(success) {
					return ms;
				} else {
					//System.out.println("Incorrect details");
				}
			} catch(SQLException e) {
				//pray that it works
				success = false;
			}
		}
		return ms;
	}
	
	//boolean is for if command was successful, useful for further steps
	//i.e. sales-employee add which depends on this succeeding
	public static ResultSet addEmployeeDetails(MySQLHandler ms) throws SQLException, IOException, InterruptedException {
		try {
		Scanner in = new Scanner(System.in);
		System.out.println("Input employee name");
		String newName = "\"" + in.nextLine() + "\"";
		WrongInput(newName, 30, 2, "employee name");
		System.out.println("Input employee address");
		String newAddress = "\"" + in.nextLine() + "\"";
		WrongInput(newAddress, 100, 2, "employee address");
		System.out.println("Input employee NIN");
		String newNIN = "\"" + in.nextLine() + "\"";
		WrongInput(newNIN, 12, 2, "employee NIN");
		System.out.println("Input employee bank number");
		String newBankNumber = "\"" + in.nextLine() + "\"";
		WrongInput(newBankNumber, 36, 2, "employee bank number");
		System.out.println("Input employee starting salary");
		float newSalary = in.nextFloat();
		WrongInput(newSalary, 999999999, "employee starting salary");
		//System.in.read();
		ms.Statement(String.format("INSERT into EmployeeDetails(employeeName, employeeAddress, NINumber, employeeBank, employeeSalary) VALUES (%s, %s, %s, %s, %f)", newName, newAddress, newNIN, newBankNumber, newSalary));
	    return ms.Query("Select * from EmployeeDetails where NINumber ="+newNIN);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	
	}
	
	public static void addSalesEmployee(MySQLHandler ms, String EmpID) throws SQLException {
		Scanner in = new Scanner(System.in);
		System.out.println("--------");
		System.out.println("Input Sales employee Comission rate");
		float newComission = in.nextFloat();
		System.out.println("Input employee total sales");
		float newSales = in.nextFloat();
		
		ms.Statement(String.format("INSERT into SalesEmployee(employeeID, commissionRate, totalSales) "
				+ "VALUES (%s, %f, %f)", 
				EmpID, newComission, newSales));
		
	}
	
	public static ResultSet getSalesTotal(MySQLHandler ms) {
		return ms.Query("SELECT * FROM EmployeeDetails JOIN SalesEmployee "
				+ "ORDER BY SalesEmployee.totalSales");
		
	}
	
	public static ResultSet getNetPay(MySQLHandler ms) {
		return ms.Query("select employeeID, employeeName, employeeSalary + ifnull((select(commissionRate * totalSales) from SalesEmployee \n" + 
				"where SalesEmployee.employeeID = EmployeeDetails.employeeID),0) as total\n" + 
				"from EmployeeDetails\n" + 
				"");

	}
	
	//For NI number
	public static boolean filter(String PE) {
		//Place holder regex function
		/*
		Pattern pattern = Pattern.compile("Input(.*?)(?=Input|$)");
		Matcher matcher = pattern.matcher(PE);
		while (matcher.find()) {
		   return true;
		}
		
		return false;
		*/
		return true;
	}
	
	public static boolean filter(String input, int maxLength, int type) {
		if (type == 1) {
			for (Character c : input.toCharArray()) {
				if (!c.isDigit(c)) {
					return false;
				}
			}
		}
		else {}
		if (input.length() > maxLength) {
			return false;
		}
		return true;
	}
	
	public static String WrongInput(String Input, int maxLength, int type, String message) {
		Scanner S = new Scanner(System.in);
		while(!filter(Input, maxLength, type)){
			System.out.println("Wrong input! please enter for "+message );
			Input = S.nextLine();
			
		}
		
		return Input;
	}
	
	public static int WrongInput(int Input, int maxValue, String message) {
		Scanner S = new Scanner(System.in);
		if (Input > maxValue) {
			System.out.println("Wrong input! please enter for "+message );
			Input = S.nextInt();
		}
		return Input;
	}
	
	public static float WrongInput(float Input, int maxValue, String message) {
		Scanner S = new Scanner(System.in);
		if (Input > maxValue) {
			System.out.println("Wrong input! please enter for "+message );
			Input = S.nextFloat();
		}
		return Input;
	}
}