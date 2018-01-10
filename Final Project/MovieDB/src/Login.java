import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Login {
	
	static Scanner input = new Scanner(System.in);
	public static Connection connection = null;
	public static String username;
	public static String password;
	
	//sets Username
	public static void setUsername() {
		System.out.println("ENTER USERNAME: ");
		username = input.nextLine();	
	}
	
	//sets Password
	public static void setPassword() {
		System.out.println("ENTER PASSWORD: ");
		password = input.nextLine();	
	}

	//Admin Login
	public static boolean adminLogin() {
		//Validates User Input
		if (username.equals("admin") && password.equals("admin")) {
			System.out.println("Welcome Admin!");
			return true;
		}
		else {
			System.out.println("Incorrect username or password.");
			return false;
		}
	}
	
	//User Connection
	public static Connection connection() throws SQLException {
		
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb", "user", "user");
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} 
		return connection;
	}
	
	//Closes Connections ****NEEDS EXCEPTIONS*****
	public static void closeConnection() throws SQLException {
		connection.close();
	}
}
