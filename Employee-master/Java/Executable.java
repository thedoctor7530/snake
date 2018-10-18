package app;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Pattern;
import com.sun.org.apache.xerces.internal.impl.xs.identity.Selector.Matcher;

import app.MySQLHandler;

public class Executable {

	public Executable() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws SQLException, IOException, InterruptedException {
		// TODO Auto-generated method stub
		MySQLHandler ms = login();
		String InsertStatement = "INSERT into EmployeeDetails VALUES (1, \"James Matchett\", \"Belfast\", \"PE284243G\", \"1234567890\", 100000.00);";
		addEmployeeDetails(ms);

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
					System.out.println("Incorrect details");
				}
			} catch(SQLException e) {
				//pray that it works
				success = false;
			}
		}
		return ms;
	}
	
	public static void addEmployeeDetails(MySQLHandler ms) throws SQLException, IOException, InterruptedException {
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
	}
	
	//For NI number
	public static boolean filter(String PE) {
		Pattern pattern = Pattern.compile("Input(.*?)(?=Input|$)");
		Matcher matcher = pattern.matcher(PE);
		while (matcher.find()) {
		   return true;
		}
		
		return false;
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
